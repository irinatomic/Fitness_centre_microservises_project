package raf.fitness.notif_servis.dto.mail;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MailResponseDto {

    private Long id;
    private String mailType;
    private String timestamp;
    private String subject;
    private String sentTo;
    private String text;
}
