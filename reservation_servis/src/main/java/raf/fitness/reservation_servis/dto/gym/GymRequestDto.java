package raf.fitness.reservation_servis.dto.gym;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
public class GymRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @Min(value = 1, message = "Coaches count must be at least 1")
    private Integer coachesCount;
    @NotNull
    private Integer freeSessionNo;
    @NotNull
    private LocalTime openingTime;
    @NotNull
    private LocalTime closingTime;
}
