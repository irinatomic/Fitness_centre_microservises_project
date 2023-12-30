package raf.fitness.reservation_servis.dto.gym;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
/** Only difference from the request DTO is that this has an ID
 *  field and a manager ID field -> we do not wish to allow the
 *  changing of the manager in the request DTO.
 */
public class GymResponseDto {

    private Long id;
    private String name;
    private String description;
    private Integer coachesCount;
    private Long managerId;
    private Integer freeSessionNo;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
