package raf.fitness.reservation_servis.reminder;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.async_comm.email.EmailSenderService;
import raf.fitness.reservation_servis.async_comm.email.EmailType;
import raf.fitness.reservation_servis.domain.SignedUp;
import raf.fitness.reservation_servis.domain.TrainingSession;
import raf.fitness.reservation_servis.repository.SignedUpRepository;
import raf.fitness.reservation_servis.repository.TrainingSessionRepository;
import raf.fitness.reservation_servis.service.TrainingSessionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Reminder {

    private TrainingSessionRepository trainingSessionRepository;
    private TrainingSessionService trainingSessionService;
    private SignedUpRepository signedUpRepository;

    // async communication
    private EmailSenderService emailSenderService;

    public Reminder(TrainingSessionRepository trainingSessionRepository, TrainingSessionService trainingSessionService, SignedUpRepository signedUpRepository, EmailSenderService emailSenderService) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingSessionService = trainingSessionService;
        this.signedUpRepository = signedUpRepository;
        this.emailSenderService = emailSenderService;
    }

    // Cron job every 15 minutes
    @Scheduled(cron = "0 0/15 * * * *")
    public void checkSessions() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalTime now = LocalTime.now();

        List<TrainingSession> sessions = trainingSessionRepository.findAllByDateAndStartTime(tomorrow, now);

        for(TrainingSession s : sessions) {
            if(s.getTrainingTypeName().equalsIgnoreCase("INDIVIDUAL")){
                SignedUp creator = signedUpRepository.findByClientIdAndTrainingSessionId(s.getCreatorId(), s.getId()).get();

                // == Service 3 to remind the creator that the session is tomorrow ==
                Map<String, String> params = new HashMap<>();
                params.put("firstName", creator.getFirstName());
                params.put("lastName", creator.getLastName());
                params.put("trainingName", s.getTraining().getName());
                params.put("trainingDate", s.getDate().toString());
                params.put("trainingStartTime", s.getStartTime().toString());
                params.put("trainingPrice", s.getTraining().getPrice().toString());
                params.put("fitnessCentreName", s.getTraining().getGym().getName());
                emailSenderService.sendMessageToQueue(EmailType.REMINDER, creator.getEmail(), params);
            } else checkGroupSession(s);
        }

    }

    private void checkGroupSession(TrainingSession s) {
        int minPeopleNo = s.getTraining().getMinPeopleNo();
        int signedUpCount = s.getSignedUpCount();

        Map<String, String> params = new HashMap<>();
        params.put("trainingName", s.getTraining().getName());
        params.put("trainingDate", s.getDate().toString());
        params.put("trainingStartTime", s.getStartTime().toString());
        params.put("trainingPrice", s.getTraining().getPrice().toString());
        params.put("fitnessCentreName", s.getTraining().getGym().getName());

        if(signedUpCount < minPeopleNo) {
            trainingSessionService.delete(s.getId());

            // == Service 3 to notify all signed-up users that the session is canceled ==
            List<SignedUp> signedUpUsers = signedUpRepository.findAllByTrainingSessionId(s.getId());
            for(SignedUp su : signedUpUsers) {
                params.put("firstName", su.getFirstName());
                params.put("lastName", su.getLastName());
                emailSenderService.sendMessageToQueue(EmailType.CANCELLATION, su.getEmail(), params);
            }

        } else {
            // == Service 3 to remind all signed-up users that the session is tomorrow ==
            List<SignedUp> signedUpUsers = signedUpRepository.findAllByTrainingSessionId(s.getId());
            for(SignedUp su : signedUpUsers) {
                params.put("firstName", su.getFirstName());
                params.put("lastName", su.getLastName());
                emailSenderService.sendMessageToQueue(EmailType.REMINDER, su.getEmail(), params);
            }
        }
    }
}
