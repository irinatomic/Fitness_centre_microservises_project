package raf.fitness.reservation_servis.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.fitness.reservation_servis.domain.*;
import raf.fitness.reservation_servis.dto.SignedUpDto;
import raf.fitness.reservation_servis.dto.training_session.*;
import raf.fitness.reservation_servis.mapper.SignedUpMapper;
import raf.fitness.reservation_servis.mapper.TrainingSessionMapper;
import raf.fitness.reservation_servis.repository.*;
import raf.fitness.reservation_servis.service.TrainingSessionService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private TrainingSessionRepository trainingSessionRepository;
    private TrainingSessionMapper trainingSessionMapper;
    private TimeSlotRepository timeSlotRepository;
    private SignedUpRepository signedUpRepository;
    private SignedUpMapper signedUpMapper;

    public TrainingSessionServiceImpl(TrainingSessionRepository trainingSessionRepository, TrainingSessionMapper trainingSessionMapper, TimeSlotRepository timeSlotRepository, SignedUpRepository signedUpRepository, SignedUpMapper signedUpMapper) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingSessionMapper = trainingSessionMapper;
        this.timeSlotRepository = timeSlotRepository;
        this.signedUpRepository = signedUpRepository;
        this.signedUpMapper = signedUpMapper;
    }

    @Override
    public TrainingSessionResponseDto create(TrainingSessionRequestDto trainingSessionRequestDto, SignedUpDto creator) {
        TrainingSession ts = trainingSessionMapper.requestDtoToTrainingSession(trainingSessionRequestDto);
        SignedUp su = signedUpMapper.requestDtoToSignedUp(creator);
        trainingSessionRepository.save(ts);
        signedUpRepository.save(su);

        // Reserve time slots
        LocalDate date = ts.getDate();
        LocalTime startTime = ts.getStartTime();
        int timeSlotsNeeded = ts.getTraining().getDuration() / 15;

        for (int i = 0; i < timeSlotsNeeded; i++) {
            TimeSlot timeSlot = timeSlotRepository.findByGymIdAndDateAndStartTime(ts.getGym().getId(), date, startTime).orElseThrow(() -> new RuntimeException("Time slot not found"));
            if(timeSlot.isReserved())
                throw new RuntimeException("Time slot already reserved");

            timeSlot.setReserved(true);
            timeSlot.setTrainingSession(ts);
            startTime = startTime.plusMinutes(15);
        }

        //Todo: service 3 to notify the creator that the session is reserved
        return trainingSessionMapper.trainingSessionToResponseDto(ts);
    }

    @Override
    public void signUp(Long sessionId, SignedUpDto user) {
        TrainingSession ts = trainingSessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Training session not found"));

        if (ts.getSignedUpCount() == ts.getTraining().getCapacity()) {
            // Todo: service 3 to notify that there is no free spot
            return;
        }

        SignedUp su = signedUpMapper.requestDtoToSignedUp(user);
        signedUpRepository.save(su);
        ts.setSignedUpCount(ts.getSignedUpCount() + 1);
        // Todo: service 3 to notify the user that he is signed up
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

        //Todo: service 3 to notify the user that the session is cancelled
        signedUpRepository.delete(signedUp);
    }

    @Override
    public void cancelAsManager(Long sessionId){
        // time slots stay reserved
        TrainingSession ts = trainingSessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Training session not found"));
        List<SignedUp> signedUpUsers = signedUpRepository.findAllByTrainingSessionId(sessionId);

        trainingSessionRepository.deleteById(sessionId);
        signedUpRepository.deleteAllByTrainingSessionId(sessionId);

        // Todo: service 3 to notify all signed-up users that the session is cancelled
    }


    @Override
    public void delete(Long sessionId) {
        // this method is called by the cron job
        List<SignedUp> signedUpUsers = signedUpRepository.findAllByTrainingSessionId(sessionId);
        // Todo: service 3 to notify all signed-up users that the session is cancelled

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
    public Page<TrainingSessionResponseDto> getAllForGym(Long gymId, Pageable pageable) {
        // signed-up users are added in the mapper
        return trainingSessionRepository.findAllByGymId(gymId, pageable).map(trainingSessionMapper::trainingSessionToResponseDto);
    }

    @Override
    public List<TrainingSessionResponseDto> getAllForGymAndDate(Long gymId, LocalDate date) {
        List<TrainingSession> tss = trainingSessionRepository.findAllByGymIdAndDate(gymId, date);
        return tss.stream().map(trainingSessionMapper::trainingSessionToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<TrainingSessionResponseDto> getAllForGymAndTrainingType(Long gymId, String trainingType) {
        List<TrainingSession> tss = trainingSessionRepository.findAllByGymIdAndTrainingTypeName(gymId, trainingType);
        return tss.stream().map(trainingSessionMapper::trainingSessionToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<TrainingSessionResponseDto> getAllForGymAndDateAndTrainingType(Long gymId, LocalDate date, String trainingType) {
        List<TrainingSession> tss = trainingSessionRepository.findAllByGymIdAndTrainingTypeNameAndDate(gymId, trainingType, date);
        return tss.stream().map(trainingSessionMapper::trainingSessionToResponseDto).collect(Collectors.toList());
    }
}
