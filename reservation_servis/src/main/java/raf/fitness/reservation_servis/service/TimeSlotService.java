package raf.fitness.reservation_servis.service;

import raf.fitness.reservation_servis.domain.TimeSlot;
import raf.fitness.reservation_servis.dto.TimeSlotDto;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService {

    // create time slots for the next 6 months
    void createTimeSlotsForGym(Long gymId);

    List<TimeSlotDto> findFreeTimeSlots(Long gymId, LocalDate date);
}
