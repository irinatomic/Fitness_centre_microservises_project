package raf.fitness.reservation_servis.async_comm.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import raf.fitness.reservation_servis.async_comm.MessageHelper;

import java.util.Map;

@Service
public class EmailSenderService {

    private JmsTemplate emailJmsTemplate;
    @Value("${destination.sendEmail}")
    private String emailQueue;
    private MessageHelper MessageHelper;

    @Autowired
    public EmailSenderService(JmsTemplate emailJmsTemplate, MessageHelper messageHelper) {
        this.emailJmsTemplate = emailJmsTemplate;
        MessageHelper = messageHelper;
    }

    public void sendMessageToQueue(EmailType emailType, String sentTo, Map<String, String> params) {
        MailRequestDto mail = createMailRequestDto(emailType, sentTo, params);
        String json = MessageHelper.createTextMessage(mail);
        emailJmsTemplate.convertAndSend(emailQueue, json);
    }

    private MailRequestDto createMailRequestDto(EmailType emailType, String sentTo, Map<String, String> params) {
        MailRequestDto mailRequestDto = new MailRequestDto();
        mailRequestDto.setMailType(emailType.toString());
        mailRequestDto.setSentTo(sentTo);
        mailRequestDto.setParams(params);
        return mailRequestDto;
    }

}
