package raf.fitness.notif_servis.email;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
