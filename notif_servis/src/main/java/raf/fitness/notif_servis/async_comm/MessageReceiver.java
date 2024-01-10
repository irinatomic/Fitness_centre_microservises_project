package raf.fitness.notif_servis.async_comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.fitness.notif_servis.domain.Mail;
import raf.fitness.notif_servis.dto.mail.MailRequestDto;
import raf.fitness.notif_servis.email.EmailService;
import raf.fitness.notif_servis.mapper.MailMapper;
import raf.fitness.notif_servis.repository.MailRepository;

@Component
public class MessageReceiver {

    @Value("${origin.sendEmail}")
    private String emailQueue;
    private EmailService emailService;
    private MailMapper mailMapper;
    private MessageHelper MessageHelper;
    private MailRepository mailRepository;

    public MessageReceiver(EmailService emailService, MailMapper mailMapper, MessageHelper MessageHelper, MailRepository mailRepository) {
        this.emailService = emailService;
        this.mailMapper = mailMapper;
        this.MessageHelper = MessageHelper;
        this.mailRepository = mailRepository;
    }

    @JmsListener(destination = "${origin.sendEmail}")
    public void receiveMessage(String message) {
        MailRequestDto mailRequestDto = MessageHelper.convertJsonToMailRequestDto(message);
        Mail mail = mailMapper.mailRequestDtoToMail(mailRequestDto);
        mailRepository.save(mail);

        // send to someones email
        emailService.sendSimpleMessage(mail.getSentTo(), mail.getMailType().getSubject(), mail.getFullText());
    }
}
