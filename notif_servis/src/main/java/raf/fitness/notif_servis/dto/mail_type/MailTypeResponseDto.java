package raf.fitness.notif_servis.dto.mail_type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailTypeResponseDto {

    private Long id;
    private String name;
    private String subject;
    private String text;
}
