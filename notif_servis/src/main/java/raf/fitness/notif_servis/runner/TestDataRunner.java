package raf.fitness.notif_servis.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.fitness.notif_servis.domain.MailType;
import raf.fitness.notif_servis.repository.MailRepository;
import raf.fitness.notif_servis.repository.MailTypeRepository;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private MailTypeRepository mailTypeRepository;
    private MailRepository mailRepository;

    public TestDataRunner(MailTypeRepository mailTypeRepository, MailRepository mailRepository){
        this.mailTypeRepository = mailTypeRepository;
        this.mailRepository = mailRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Activation mail type
        String activationSubject = "Account activation";
        String activationText = "Dear %name%,\n\n" +
                "Welcome to %fitnessCentre%!\n" +
                "Please click on the link below to activate your account:\n" +
                "%link%\n\n" +
                "Best regards,\n" +
                "%fitnessCentre% Team";
        MailType activation = new MailType("ACTIVATION", activationSubject, activationText);

        // Reset password mail type
        String resetPasswordSubject = "Password reset";
        String resetPasswordText = "Dear %name%,\n\n" +
                "You have requested a password reset.\n" +
                "Please click on the link below to reset your password:\n" +
                "%link%\n\n" +
                "Best regards,\n" +
                "%fitnessCentre% Team";
        MailType resetPassword = new MailType("RESET_PASSWORD", resetPasswordSubject, resetPasswordText);

        // Successful training reservation mail type
        String reservationSubject = "Successful training reservation";
        String reservationText = "Dear %name%,\n\n" +
                "You have successfully reserved a training.\n" +
                "Training details:\n" +
                "Training name: %trainingName%\n" +
                "Training date: %trainingDate%\n" +
                "Training time: %trainingTime%\n" +
                "Training duration: %trainingDuration%\n" +
                "Training price: %trainingPrice%\n\n" +
                "Best regards,\n" +
                "%fitnessCentre% Team";
        MailType reservation = new MailType("RESERVATION", reservationSubject, reservationText);

        // Training cancellation mail type
        String cancellationSubject = "Training cancellation";
        String cancellationText = "Dear %name%,\n\n" +
                "A training has been canceller.\n" +
                "Training details:\n" +
                "Training name: %trainingName%\n" +
                "Training date: %trainingDate%\n" +
                "Training time: %trainingTime%\n" +
                "Training duration: %trainingDuration%\n" +
                "Training price: %trainingPrice%\n\n" +
                "Best regards,\n" +
                "%fitnessCentre% Team";
        MailType cancellation = new MailType("CANCELLATION", cancellationSubject, cancellationText);

        // Free training mail type
        String freeTrainingSubject = "Free training";
        String freeTrainingText = "Dear %name%,\n\n" +
                "You have a free training.\n" +
                "Training details:\n" +
                "Training name: %trainingName%\n" +
                "Training date: %trainingDate%\n" +
                "Training time: %trainingTime%\n" +
                "Training duration: %trainingDuration%\n" +
                "Training price: 0 \n\n" +
                "Best regards,\n" +
                "%fitnessCentre% Team";
        MailType freeTraining = new MailType("FREE_TRAINING", freeTrainingSubject, freeTrainingText);

        // Training reminder mail type
        String reminderSubject = "Training reminder";
        String reminderText = "Dear %name%,\n\n" +
                "You have a training in 24 hours.\n" +
                "Training details:\n" +
                "Training name: %trainingName%\n" +
                "Training date: %trainingDate%\n" +
                "Training time: %trainingTime%\n" +
                "Training duration: %trainingDuration%\n" +
                "Training price: %trainingPrice%\n\n" +
                "Best regards,\n" +
                "%fitnessCentre% Team";
        MailType reminder = new MailType("REMINDER", reminderSubject, reminderText);

        mailTypeRepository.save(activation);
        mailTypeRepository.save(resetPassword);
        mailTypeRepository.save(reservation);
        mailTypeRepository.save(cancellation);
        mailTypeRepository.save(freeTraining);
        mailTypeRepository.save(reminder);
    }
}
