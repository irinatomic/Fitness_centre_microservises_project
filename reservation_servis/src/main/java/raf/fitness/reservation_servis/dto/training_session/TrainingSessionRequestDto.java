package raf.fitness.reservation_servis.dto.training_session;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
/** Request DTO when creating a training session.
 *  Cannot be updated.
 */
public class TrainingSessionRequestDto {

    @NotNull
    private String creatorId;
    @NotBlank
    private String trainingTypeName;
    @NotNull
    private String date;
    @NotNull
    private String startTime;
    @NotNull
    private String trainingId;
    @NotNull
    private String gymId;

    // Since we cannot send 2 DTOs in one body, we will include the SignedUpDto creator here
    @NotNull
    private Long clientId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;

}
