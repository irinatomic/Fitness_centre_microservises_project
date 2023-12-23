package raf.fitness.reservation_servis.dto.training_session;

import lombok.Getter;
import lombok.Setter;

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
    private Long creatorId;
    private Integer signedUpCount = 1;
    @NotBlank
    private String trainingTypeName;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private Long trainingId;
    @NotNull
    private Long gymId;

}
