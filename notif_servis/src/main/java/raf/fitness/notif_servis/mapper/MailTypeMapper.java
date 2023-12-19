package raf.fitness.notif_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.notif_servis.domain.MailType;
import raf.fitness.notif_servis.dto.mail_type.*;

@Component
public class MailTypeMapper {

    public MailTypeMapper() { }

    public MailTypeResponseDto mailTypeToMTResponseDto(MailType mailType) {
        MailTypeResponseDto responseDto = new MailTypeResponseDto();
        responseDto.setId(mailType.getId());
        responseDto.setName(mailType.getName());
        responseDto.setSubject(mailType.getSubject());
        responseDto.setText(mailType.getText());
        return responseDto;
    }

    /* MailType structure
     * private String name;
     * private String subject;
     * private String text;
     */
    public MailType mailTypeRequestDtoToMailType(MailTypeRequestDto mailTypeRequestDto) {
        MailType mailType = new MailType();
        mailType.setName(mailTypeRequestDto.getName());
        mailType.setSubject(mailTypeRequestDto.getSubject());
        mailType.setText(mailTypeRequestDto.getText());
        return mailType;
    }
}
