package raf.fitness.reservation_servis.dto.training;

import lombok.Getter;
import lombok.Setter;
import raf.fitness.reservation_servis.aspects.validator.DivisibleByFifteen;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
/** Used for creating and updating a training.
 *  Response DTO has an additional id field.
 */
public class TrainingRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    @Min(value = 1, message = "Price must be at least 1")
    private Integer price;
    @DivisibleByFifteen
    private Integer duration;
    @NotNull
    private Integer capacity;
    @NotNull
    private Integer minPeopleNo;
    @NotNull
    private Long gymId;
    @NotNull
    private Long trainingTypeId;
}
