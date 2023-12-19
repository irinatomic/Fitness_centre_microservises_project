package raf.fitness.notif_servis.dto.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailResponseDto {

    private Long id;
    private String mailType;
    private String subject;
    private String sentTo;
    private String text;
}
