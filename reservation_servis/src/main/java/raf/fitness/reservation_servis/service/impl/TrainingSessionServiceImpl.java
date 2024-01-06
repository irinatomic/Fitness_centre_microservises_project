package raf.fitness.reservation_servis.service.impl;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import raf.fitness.reservation_servis.async_comm.bookings.BookingsHandlerService;
import raf.fitness.reservation_servis.async_comm.email.*;
import raf.fitness.reservation_servis.domain.*;
import raf.fitness.reservation_servis.dto.SignedUpDto;
import raf.fitness.reservation_servis.dto.training_session.*;
import raf.fitness.reservation_servis.mapper.*;
import raf.fitness.reservation_servis.repository.*;
import raf.fitness.reservation_servis.service.TrainingSessionService;

import javax.transaction.Transactional;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private TrainingSessionRepository trainingSessionRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private TrainingSessionMapper trainingSessionMapper;
    private TimeSlotRepository timeSlotRepository;
    private GymRepository gymRepository;
    private TrainingRepository trainingRepository;
    private SignedUpRepository signedUpRepository;
    private SignedUpMapper signedUpMapper;
    private RestTemplate reservationRestTemplate;
    // async communication
    private EmailSenderService emailSenderService;
    private BookingsHandlerService bookingsHandlerService;

    public TrainingSessionServiceImpl(TrainingSessionRepository trainingSessionRepository, TrainingTypeRepository trainingTypeRepository, TrainingSessionMapper trainingSessionMapper, TimeSlotRepository timeSlotRepository, GymRepository gymRepository, TrainingRepository trainingRepository, SignedUpRepository signedUpRepository, SignedUpMapper signedUpMapper, RestTemplate reservationRestTemplate, EmailSenderService emailSenderService, BookingsHandlerService bookingsHandlerService) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingSessionMapper = trainingSessionMapper;
        this.timeSlotRepository = timeSlotRepository;
        this.gymRepository = gymRepository;
        this.trainingRepository = trainingRepository;
        this.signedUpRepository = signedUpRepository;
        this.signedUpMapper = signedUpMapper;
        this.reservationRestTemplate = reservationRestTemplate;
        this.emailSenderService = emailSenderService;
        this.bookingsHandlerService = bookingsHandlerService;
    }

    @Override
    public TrainingSessionResponseDto create(TrainingSessionRequestDto trainingSessionRequestDto) {
        TrainingSession ts = trainingSessionMapper.requestDtoToTrainingSession(trainingSessionRequestDto);
        SignedUp su = signedUpMapper.extractSignedUpFromTrainingSessionDto(trainingSessionRequestDto);

        trainingSessionRepository.save(ts);
        su.setTrainingSession(ts);                  // not sending this in the dto
        signedUpRepository.save(su);

        // Reserve time slots
        LocalDate date = ts.getDate();
        LocalTime startTime = ts.getStartTime();
        int timeSlotsNeeded = ts.getTraining().getDuration() / 15;

        List<TimeSlot> reserved = new ArrayList<>();

        for (int i = 0; i < timeSlotsNeeded; i++) {
            TimeSlot timeSlot = timeSlotRepository.findByGymIdAndDateAndStartTime(ts.getGym().getId(), date, startTime).orElseThrow(() -> new RuntimeException("Time slot not found"));

            // in case that a time slot is already reserved, we need to free up the reserved time slots
            if(timeSlot.isReserved()) {
                trainingSessionRepository.delete(ts);
                signedUpRepository.delete(su);
                for(TimeSlot tsReserved : reserved) {
                    tsReserved.setReserved(false);
                    tsReserved.setTrainingSession(null);
                }
                throw new RuntimeException("Time slot already reserved");
            }

            timeSlot.setReserved(true);
            reserved.add(timeSlot);                 // in case of an error, we need to free up the reserved time slots

            timeSlot.setTrainingSession(ts);
            startTime = startTime.plusMinutes(15);
        }

        Long trainingId = Long.parseLong(trainingSessionRequestDto.getTrainingId());
        if(!trainingRepository.findById(trainingId).isPresent()){
            System.out.println("There is no training by that id");
        }
        Training training = trainingRepository.findById(trainingId).get();
        Integer cena = training.getPrice();

        // == Service 1 to get clients trainingsBookedNo ==

        try {
            ResponseEntity<Integer> bookedNo = reservationRestTemplate.exchange("/client/booked-no/?id=" + su.getClientId(),
                    HttpMethod.GET, null, Integer.class);
            Long gymId = Long.parseLong(trainingSessionRequestDto.getGymId());
            if(!gymRepository.findById(gymId).isPresent()){
                System.out.println("There is no gym by that id");
            }
            Gym gym = gymRepository.findById(gymId).get();
            if(bookedNo.getBody() == null) {
                System.out.println("BookedNo body is empty");
                return null;
            }
            if ((bookedNo.getBody()+1) % gym.getFreeSessionNo() == 0) {
                cena = 0;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // == Service 1 increment session count ==
        bookingsHandlerService.sendMessageToQueue('+', List.of(su));

        // == Service 3 to notify the creator that the session is reserved ==
        Map<String, String> params = new HashMap<>();
        params.put("firstName", su.getFirstName());
        params.put("lastName", su.getLastName());
        params.put("trainingName", ts.getTraining().getName());
        params.put("trainingDate", ts.getDate().toString());
        params.put("trainingStartTime", ts.getStartTime().toString());
        params.put("trainingDuration", ts.getTraining().getDuration().toString());
        params.put("trainingPrice", String.valueOf(cena));
        params.put("fitnessCentreName", ts.getGym().getName());
        emailSenderService.sendMessageToQueue(EmailType.RESERVATION, su.getEmail(), params);

        return trainingSessionMapper.trainingSessionToResponseDto(ts);
    }

    @Override
    public void signUp(Long sessionId, SignedUpDto user) {
        TrainingSession ts = trainingSessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Training session not found"));

        if (ts.getSignedUpCount().equals(ts.getTraining().getCapacity())) {
            // won't happen as in the front the button is disabled
            return;
        }

        SignedUp su = signedUpMapper.requestDtoToSignedUp(user);
        signedUpRepository.save(su);
        ts.setSignedUpCount(ts.getSignedUpCount() + 1);
        trainingSessionRepository.save(ts);

        // Service 1: get clients trainingsBookedNo (sinh)
        if(!trainingRepository.findById(sessionId).isPresent()){
            System.out.println("There is no training session by that id");
        }
        Training training = trainingRepository.findById(sessionId).get();
        Integer cena = training.getPrice();

        // == Service 1 to check if the next session is free  ==
        try {
            ResponseEntity<Integer> bookedNo = reservationRestTemplate.exchange("/client/booked-no/?id=" + user.getClientId(),
                    HttpMethod.GET, null, Integer.class);
            Long gymId = training.getGym().getId();
            if(!gymRepository.findById(gymId).isPresent()){
                System.out.println("There is no gym by that id");
            }
            Gym gym = gymRepository.findById(gymId).get();
            if(bookedNo.getBody() == null) {
                System.out.println("BookedNo body is empty");
                return;
            }
            if ((bookedNo.getBody()+1) % gym.getFreeSessionNo() == 0) {
                cena = 0;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // == Service 1: increment session count ==
        bookingsHandlerService.sendMessageToQueue('+', List.of(su));

        // == Service 3 to notify the user that he is signed up ==
        Map<String, String> params = new HashMap<>();
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("trainingName", ts.getTraining().getName());
        params.put("trainingDate", ts.getDate().toString());
        params.put("trainingStartTime", ts.getStartTime().toString());
        params.put("trainingDuration", ts.getTraining().getDuration().toString());
        params.put("trainingPrice", String.valueOf(cena));
        params.put("fitnessCentreName", ts.getGym().getName());
        emailSenderService.sendMessageToQueue(EmailType.RESERVATION, user.getEmail(), params);
    }

    @Override
    public void cancelAsUser(Long sessionId, Long userId){
        TrainingSession session = trainingSessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Training session not found"));
        SignedUp signedUp = signedUpRepository.findByTrainingSessionIdAndClientId(sessionId, userId).orElseThrow(() -> new RuntimeException("User not found"));

        // option 1: individual session -> time slots become free
        if(session.getTraining().getCapacity() == 1) {
            List<TimeSlot> timeSlots = timeSlotRepository.findAllByTrainingSessionId(sessionId);
            for(TimeSlot ts : timeSlots) {
                ts.setReserved(false);
                ts.setTrainingSession(null);
            }
            trainingSessionRepository.delete(session);
        }

        // option 2: group session
        else {
            session.setSignedUpCount(session.getSignedUpCount() - 1);
        }

        // == Service 1 to decrement session count ==
        bookingsHandlerService.sendMessageToQueue('-', List.of(signedUp));

        // == Service 3 to notify the user that the session is cancelled ==
        Map<String, String> params = new HashMap<>();
        params.put("firstName", signedUp.getFirstName());
        params.put("lastName", signedUp.getLastName());
        params.put("trainingName", session.getTraining().getName());
        params.put("trainingDate", session.getDate().toString());
        params.put("trainingStartTime", session.getStartTime().toString());
        params.put("trainingDuration", session.getTraining().getDuration().toString());
        params.put("trainingPrice", String.valueOf(session.getTraining().getPrice()));
        params.put("fitnessCentreName", session.getGym().getName());
        emailSenderService.sendMessageToQueue(EmailType.CANCELLATION, signedUp.getEmail(), params);

        signedUpRepository.delete(signedUp);
    }

    @Override
    public void cancelAsManager(Long managerId, Long sessionId){
        // time slots stay reserved
        TrainingSession ts = trainingSessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Training session not found"));
        List<SignedUp> signedUpUsers = signedUpRepository.findAllByTrainingSessionId(sessionId);

        // check managerId == ts.getGym().getManagerId()
        if(!managerId.equals(ts.getGym().getManagerId())) {
            throw new RuntimeException("Manager not authorized");
        }

        signedUpRepository.deleteAllByTrainingSessionId(sessionId);
        trainingSessionRepository.deleteById(sessionId);

        // == Service 1 to decrement session count for ALL signed-up users ==
        bookingsHandlerService.sendMessageToQueue('-', signedUpUsers);

        // == Service 3 to notify all signed-up users that the session is cancelled ==
        Map<String, String> params = new HashMap<>();
        params.put("trainingName", ts.getTraining().getName());
        params.put("trainingDate", ts.getDate().toString());
        params.put("trainingStartTime", ts.getStartTime().toString());
        params.put("trainingDuration", ts.getTraining().getDuration().toString());
        params.put("trainingPrice", String.valueOf(ts.getTraining().getPrice()));
        params.put("fitnessCentreName", ts.getGym().getName());

        for(SignedUp su : signedUpUsers) {
            params.put("firstName", su.getFirstName());
            params.put("lastName", su.getLastName());
            emailSenderService.sendMessageToQueue(EmailType.CANCELLATION, su.getEmail(), params);
        }
    }

    @Override
    public void delete(Long sessionId) {
        // this method is called by the cron job
        if(!trainingSessionRepository.findById(sessionId).isPresent()){
            System.out.println("There is no training session by that id");
        }
        TrainingSession session = trainingSessionRepository.findById(sessionId).get();
        List<SignedUp> signedUpUsers = signedUpRepository.findAllByTrainingSessionId(sessionId);

        // == Service 1 to decrement session count for ALL signed-up users ==
        bookingsHandlerService.sendMessageToQueue('-', signedUpUsers);

        // == Service 3 to notify all signed-up users that the session is cancelled ==
        Map<String, String> params = new HashMap<>();
        params.put("trainingName", session.getTraining().getName());
        params.put("trainingDate", session.getDate().toString());
        params.put("trainingStartTime", session.getStartTime().toString());
        params.put("trainingDuration", session.getTraining().getDuration().toString());
        params.put("trainingPrice", String.valueOf(session.getTraining().getPrice()));
        params.put("fitnessCentreName", session.getGym().getName());

        for(SignedUp su : signedUpUsers) {
            params.put("firstName", su.getFirstName());
            params.put("lastName", su.getLastName());
            emailSenderService.sendMessageToQueue(EmailType.CANCELLATION, su.getEmail(), params);
        }

        // delete all signed-up users
        signedUpRepository.deleteAllByTrainingSessionId(sessionId);
        trainingSessionRepository.deleteById(sessionId);

        // free up time slots
        List<TimeSlot> timeSlots = timeSlotRepository.findAllByTrainingSessionId(sessionId);
        for(TimeSlot ts : timeSlots) {
            ts.setReserved(false);
            ts.setTrainingSession(null);
        }
    }

    @Override
    public List<TrainingSessionResponseDto> getAllForGym(Long gymId) {
        // signed-up users are added in the mapper
        return trainingSessionRepository.findAllByGymId(gymId).stream().map(trainingSessionMapper::trainingSessionToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<TrainingSessionResponseDto> getAllForGymAndDate(Long gymId, LocalDate date) {
        List<TrainingSession> tss = trainingSessionRepository.findAllByGymIdAndDate(gymId, date);
        return tss.stream().map(trainingSessionMapper::trainingSessionToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<TrainingSessionResponseDto> getAllForGymAndTrainingType(Long gymId, Long trainingTypeId) {
        TrainingType tt = trainingTypeRepository.findById(trainingTypeId).get();
        String trainingType = tt.getName();

        List<TrainingSession> tss = trainingSessionRepository.findAllByGymIdAndTrainingTypeName(gymId, trainingType);
        return tss.stream().map(trainingSessionMapper::trainingSessionToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<TrainingSessionResponseDto> getAllForGymAndDateAndTrainingType(Long gymId, LocalDate date, Long trainingTypeId) {
        TrainingType tt = trainingTypeRepository.findById(trainingTypeId).get();
        String trainingType = tt.getName();

        List<TrainingSession> tss = trainingSessionRepository.findAllByGymIdAndTrainingTypeNameAndDate(gymId, trainingType, date);
        return tss.stream().map(trainingSessionMapper::trainingSessionToResponseDto).collect(Collectors.toList());
    }
}
