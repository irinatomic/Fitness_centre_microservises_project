package raf.fitness.notif_servis.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.fitness.notif_servis.domain.Mail;
import raf.fitness.notif_servis.domain.MailType;
import raf.fitness.notif_servis.repository.MailRepository;
import raf.fitness.notif_servis.repository.MailTypeRepository;

import java.time.LocalDate;

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

        // Mail types
        MailType activation = createActivationMT();
        MailType resetPassword = createResetPasswordMT();
        MailType reservation = createReservationMT();
        MailType cancellation = createCancellationMT();
        MailType reminder = createReminderMT();

        mailTypeRepository.save(activation);
        mailTypeRepository.save(resetPassword);
        mailTypeRepository.save(reservation);
        mailTypeRepository.save(cancellation);
        mailTypeRepository.save(reminder);

        // Mails
        Mail activationMail1 = createActivationMail1(activation);
        Mail activationMail2 = createActivationMail2(activation);
        Mail reservationMail = createReservationMail(reservation);
        Mail reminderMail1 = createReminderMailClient(reminder);
        Mail reminderMail2 = createReminderMailManager(reminder);

        mailRepository.save(activationMail1);
        mailRepository.save(activationMail2);
        mailRepository.save(reservationMail);
        mailRepository.save(reminderMail1);
        mailRepository.save(reminderMail2);
    }

    private MailType createActivationMT() {
        String activationSubject = "Account activation";
        String activationText = "Dear %firstName% %lastName%,\n\n" +
                "Welcome to our fitness centre!\n" +
                "Please click on the link below to activate your account:\n" +
                "%link%\n\n" +
                "Best regards!\n";
        return new MailType("ACTIVATION", activationSubject, activationText);
    }

    private MailType createResetPasswordMT() {
        String resetPasswordSubject = "Password reset";
        String resetPasswordText = "Dear %firstName% %lastName%,\n\n" +
                "You have requested a password reset.\n" +
                "Please click on the link below to reset your password:\n" +
                "%link%\n\n" +
                "Best regards,\n" +
                "%fitnessCentreName% Team";
        return new MailType("RESET_PASSWORD", resetPasswordSubject, resetPasswordText);
    }

    private MailType createReservationMT() {
        String reservationSubject = "Successful training reservation";
        String reservationText = "Dear %firstName% %lastName%,\n\n" +
                "You have successfully reserved a training.\n" +
                "Training details:\n" +
                "Training name: %trainingName%\n" +
                "Training date: %trainingDate%\n" +
                "Training time: %trainingStartTime%\n" +
                "Training duration: %trainingDuration%\n" +
                "Training price: %trainingPrice%\n\n" +
                "Best regards,\n" +
                "%fitnessCentreName% Team";
        return new MailType("RESERVATION", reservationSubject, reservationText);
    }

    private MailType createCancellationMT() {
        String cancellationSubject = "Training cancellation";
        String cancellationText = "Dear %firstName% %lastName%,\n\n" +
                "A training has been cancelled.\n" +
                "Training details:\n" +
                "Training name: %trainingName%\n" +
                "Training date: %trainingDate%\n" +
                "Training time: %trainingStartTime%\n" +
                "Training duration: %trainingDuration%\n" +
                "Training price: %trainingPrice%\n\n" +
                "Best regards,\n" +
                "%fitnessCentreName% Team";
        return new MailType("CANCELLATION", cancellationSubject, cancellationText);
    }

    private MailType createReminderMT() {
        String reminderSubject = "Training reminder";
        String reminderText = "Dear %firstName% %lastName%,\n\n" +
                "You have a training in 24 hours.\n" +
                "Training details:\n" +
                "Training name: %trainingName%\n" +
                "Training date: %trainingDate%\n" +
                "Training time: %trainingStartTime%\n" +
                "Training duration: %trainingDuration%\n" +
                "Training price: %trainingPrice%\n\n" +
                "Best regards,\n" +
                "%fitnessCentreName% Team";
        return new MailType("REMINDER", reminderSubject, reminderText);
    }

    private Mail createActivationMail1(MailType activationMT) {
        String fullText = "Dear CName_one CSurname_one,\n\n" +
                "Welcome to Fitness Centre!\n" +
                "Please click on the link below to activate your account:\n" +
                "%https://activation_link\n\n" +
                "Best regards,\n" +
                "Fitness Centre Team";
        return new Mail(activationMT, LocalDate.now(), "client_one@email.com", fullText);
    }

    private Mail createActivationMail2(MailType activationMT) {
        String fullText = "Dear CName_two CSurname_two,\n\n" +
                "Welcome to Fitness Centre!\n" +
                "Please click on the link below to activate your account:\n" +
                "%https://activation_link\n\n" +
                "Best regards,\n" +
                "Fitness Centre Team";
        return new Mail(activationMT, LocalDate.now(), "client_two@email.com", fullText);
    }

    private Mail createReservationMail(MailType reservationMT) {
        String fullText = "Dear CName_one CSurname_one,\n\n" +
                "You have successfully reserved a training.\n" +
                "Training details:\n" +
                "Training name: Pilates\n" +
                "Training date: 15.12.2023.\n" +
                "Training time: 16h\n" +
                "Training duration: 60min\n" +
                "Trainer: MName_one MSurname_one\n" +
                "Training price: 1000din\n\n" +
                "Best regards,\n" +
                "Fitness Centre Team";
        return new Mail(reservationMT, LocalDate.now(), "client_one@email.com", fullText);
    }

    private Mail createReminderMailClient(MailType reminderMT) {
        String fullText = "Dear CName_one CSurname_one,\n\n" +
                "You have a training in 24 hours.\n" +
                "Training details:\n" +
                "Training name: Pilates\n" +
                "Training date: 15.12.2023.\n" +
                "Training time: 16h\n" +
                "Training duration: 60min\n" +
                "Trainer: MName_one MSurname_one\n" +
                "Training price: 1000din\n\n" +
                "Best regards,\n" +
                "Fitness Centre Team";
        return new Mail(reminderMT, LocalDate.now(), "client_one@email.com", fullText);
    }

    private Mail createReminderMailManager(MailType reminderMT) {
        String fullText = "Dear MName_one MSurname_one,\n\n" +
                "You have a training in 24 hours.\n" +
                "Training details:\n" +
                "Training name: Pilates\n" +
                "Training date: 15.12.2023.\n" +
                "Training time: 16h\n" +
                "Training duration: 60min\n" +
                "Trainer: MName_one MSurname_one\n" +
                "Training price: 1000din\n\n" +
                "Best regards,\n" +
                "Fitness Centre Team";
        return new Mail(reminderMT, LocalDate.now(), "manager_one@email.com", fullText);
    }
}
