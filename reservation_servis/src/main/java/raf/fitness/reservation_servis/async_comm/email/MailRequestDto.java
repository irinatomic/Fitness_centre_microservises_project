package raf.fitness.reservation_servis.async_comm.email;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Getter
@Setter
public class MailRequestDto {

    @Length(min = 5)
    private String mailType;
    @Email
    private String sentTo;
    @NotEmpty
    private Map<String, String> params;
}