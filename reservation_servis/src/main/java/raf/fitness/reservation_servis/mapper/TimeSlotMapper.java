package raf.fitness.reservation_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.TimeSlot;
import raf.fitness.reservation_servis.dto.TimeSlotDto;

@Component
public class TimeSlotMapper {

    public TimeSlotDto timeSlotToResponseDto(TimeSlot timeSlot) {
        TimeSlotDto dto = new TimeSlotDto();
        dto.setId(timeSlot.getId());
        dto.setDate(timeSlot.getDate());
        dto.setStartTime(timeSlot.getStartTime());
        dto.setDuration(timeSlot.getDuration());
        return dto;
    }
}
