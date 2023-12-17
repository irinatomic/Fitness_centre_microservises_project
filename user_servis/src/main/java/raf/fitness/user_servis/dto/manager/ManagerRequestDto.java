package raf.fitness.user_servis.dto.manager;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class ManagerRequestDto {

    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;
    @NotBlank
    private LocalDate dateOfEmployment;
    @Length(min = 8)
    private String password;
    @Pattern(regexp="[0-9]{10}")
    private String phoneNumber;
}
