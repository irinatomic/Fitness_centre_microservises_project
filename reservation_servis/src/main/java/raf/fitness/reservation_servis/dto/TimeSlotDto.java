package raf.fitness.reservation_servis.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
/** Represents a time slot used only for filtering free time slots.
 *  This is a response DTO.
 */
public class TimeSlotDto {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private Integer duration = 15;
}
