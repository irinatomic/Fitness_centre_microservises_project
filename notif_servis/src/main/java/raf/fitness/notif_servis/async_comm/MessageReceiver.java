package raf.fitness.notif_servis.async_comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.fitness.notif_servis.domain.Mail;
import raf.fitness.notif_servis.dto.mail.MailRequestDto;
import raf.fitness.notif_servis.email.EmailService;
import raf.fitness.notif_servis.mapper.MailMapper;

@Component
public class MessageReceiver {

    @Value("${origin.sendEmail}")
    private String emailQueue;
    private EmailService emailService;
    private MailMapper mailMapper;
    private MessageHelper MessageHelper;

    public MessageReceiver(EmailService emailService, MailMapper mailMapper, MessageHelper messageHelper) {
        this.emailService = emailService;
        this.mailMapper = mailMapper;
        this.MessageHelper = messageHelper;
    }

    @JmsListener(destination = "${origin.sendEmail}")
    public void receiveMessage(String message) {
        MailRequestDto mailRequestDto = MessageHelper.convertJsonToMailRequestDto(message);
        Mail mail = mailMapper.mailRequestDtoToMail(mailRequestDto);

        // send to someones email
        emailService.sendSimpleMessage(mail.getSentTo(), mail.getMailType().getSubject(), mail.getFullText());
    }
}
