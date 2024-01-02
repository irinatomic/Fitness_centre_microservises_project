package raf.fitness.notif_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.notif_servis.domain.Mail;
import raf.fitness.notif_servis.domain.MailType;
import raf.fitness.notif_servis.dto.mail.*;
import raf.fitness.notif_servis.repository.MailTypeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class MailMapper {

    private MailTypeRepository mailTypeRepository;

    public MailMapper(MailTypeRepository mailTypeRepository) {
        this.mailTypeRepository = mailTypeRepository;
    }

    public MailResponseDto mailToMailResponseDto(Mail mail) {
        MailResponseDto mailResponseDto = new MailResponseDto();
        mailResponseDto.setId(mail.getId());

        // format dd-MM-yyyy
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String formattedDate = mail.getTimestamp().format(formatter);
//        mailResponseDto.setTimestamp(formattedDate);

        mailResponseDto.setTimestamp(mail.getTimestamp().toString());

        mailResponseDto.setMailType(mail.getMailType().getName());
        mailResponseDto.setSentTo(mail.getSentTo());
        mailResponseDto.setSubject(mail.getMailType().getSubject());
        mailResponseDto.setText(mail.getFullText());
        return mailResponseDto;
    }

    /*
     * Mail structure:
     * private MailType mailType;
     * private LocalDate timestamp;
     * private String fullText;
     */
    public Mail mailRequestDtoToMail(MailRequestDto mailRequestDto) {
        Mail mail = new Mail();
        mail.setSentTo(mailRequestDto.getSentTo());
        mail.setTimestamp(LocalDate.now());

        MailType mailType = mailTypeRepository.findByName(mailRequestDto.getMailType()).orElse(null);
        mail.setMailType(mailType);

        String fullText = injectParamsIntoText(mailType.getText(), mailRequestDto.getParams());
        mail.setFullText(fullText);
        return mail;
    }

    private String injectParamsIntoText(String text, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String placeholder = "%" + entry.getKey() + "%";
            text = text.replace(placeholder, entry.getValue());
        }
        return text;
    }
}
