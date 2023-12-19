package raf.fitness.notif_servis.dto.mail_type;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MailTypeRequestDto {

    @Length(min = 5)
    private String name;
    @Length(min = 5)
    private String subject;
    @NotBlank
    private String text;
}
