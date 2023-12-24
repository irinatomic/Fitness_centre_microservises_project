package raf.fitness.reservation_servis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
/** Used for signing up for a training session.
 *  This is a request DTO.
 */
public class SignedUpDto {

    @NotNull
    private Long clientId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @NotNull
    private Long trainingSessionId;
}
