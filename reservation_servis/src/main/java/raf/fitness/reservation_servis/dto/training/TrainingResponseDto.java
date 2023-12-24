package raf.fitness.reservation_servis.dto.training;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingResponseDto {

    private Long id;
    private String name;
    private Integer price;
    private Integer duration;
    private Integer capacity;
    private Integer minPeopleNo;
    private Long gymId;
    private Long trainingTypeId;
}
