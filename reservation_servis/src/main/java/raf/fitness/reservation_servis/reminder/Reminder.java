package raf.fitness.reservation_servis.reminder;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.SignedUp;
import raf.fitness.reservation_servis.domain.TrainingSession;
import raf.fitness.reservation_servis.repository.SignedUpRepository;
import raf.fitness.reservation_servis.repository.TrainingSessionRepository;
import raf.fitness.reservation_servis.service.TrainingSessionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class Reminder {

    private TrainingSessionRepository trainingSessionRepository;
    private TrainingSessionService trainingSessionService;
    private SignedUpRepository signedUpRepository;

    public Reminder(TrainingSessionRepository trainingSessionRepository, TrainingSessionService trainingSessionService, SignedUpRepository signedUpRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingSessionService = trainingSessionService;
        this.signedUpRepository = signedUpRepository;
    }

    // Cron job every 15 minutes
    @Scheduled(cron = "0 0/15 * * * *")
    public void checkSessions() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalTime now = LocalTime.now();

        List<TrainingSession> sessions = trainingSessionRepository.findAllByDateAndStartTime(tomorrow, now);

        for(TrainingSession s : sessions) {
            if(s.getTrainingTypeName().equalsIgnoreCase("INDIVIDUAL")){
                // Todo: service 3 to remind the creator that the session is tomorrow
            } else checkGroupSession(s);
        }

    }

    private void checkGroupSession(TrainingSession s) {
        int minPeopleNo = s.getTraining().getMinPeopleNo();
        int signedUpCount = s.getSignedUpCount();

        if(signedUpCount < minPeopleNo) {
            trainingSessionService.delete(s.getId());
            // the service notifies signed-up users that the session is canceled
        } else {
            List<SignedUp> signedUpUsers = signedUpRepository.findAllByTrainingSessionId(s.getId());
            // Todo: service 3 to remind all signed-up users that the session is tomorrow
        }
    }
}
