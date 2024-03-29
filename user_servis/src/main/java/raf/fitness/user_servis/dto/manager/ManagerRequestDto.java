package raf.fitness.user_servis.dto.manager;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Length(min = 8)
    private String password;
    @Pattern(regexp="[0-9]+")
    private String phoneNumber;
    @NotBlank
    private String companyName;
}
