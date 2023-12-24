package raf.fitness.reservation_servis.service.impl;

import org.springframework.stereotype.Service;
import raf.fitness.reservation_servis.domain.Gym;
import raf.fitness.reservation_servis.domain.TimeSlot;
import raf.fitness.reservation_servis.dto.TimeSlotDto;
import raf.fitness.reservation_servis.mapper.TimeSlotMapper;
import raf.fitness.reservation_servis.repository.GymRepository;
import raf.fitness.reservation_servis.repository.TimeSlotRepository;
import raf.fitness.reservation_servis.service.TimeSlotService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimeSlotServiceImpl implements TimeSlotService {

    private GymRepository gymRepository;
    private TimeSlotRepository timeSlotRepository;
    private TimeSlotMapper timeSlotMapper;

    public TimeSlotServiceImpl(GymRepository gymRepository, TimeSlotRepository timeSlotRepository, TimeSlotMapper timeSlotMapper) {
        this.gymRepository = gymRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotMapper = timeSlotMapper;
    }

    @Override
    public void createTimeSlotsForGym(Long gymId) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(() -> new RuntimeException("Gym not found"));

        // Create time slots for the next 6 months, avoiding duplicates
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(6);

        // Retrieve existing time slots for the gym
        List<TimeSlot> existingTimeSlots = timeSlotRepository.findAllByGymIdAndDateAfter(gymId, startDate.minusDays(1));

        // Calculate the number of time slots in a day
        int timeSlotsPerDay = (gym.getClosingTime().getHour() - gym.getOpeningTime().getHour()) * 4;
        List<TimeSlot> timeSlots = new ArrayList<>();

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            for (int j = 0; j < timeSlotsPerDay; j++) {
                // Calculate the time for the current slot
                LocalTime startTime = gym.getOpeningTime().plusHours(j / 4).plusMinutes((j % 4) * 15);

                // Check if a time slot already exists for this date and time
                LocalDate dateTmp = date;
                boolean slotExists = existingTimeSlots.stream()
                        .anyMatch(slot -> slot.getDate().equals(dateTmp) && slot.getStartTime().equals(startTime));

                if (!slotExists) {
                    TimeSlot timeSlot = new TimeSlot();
                    timeSlot.setGym(gym);
                    timeSlot.setDate(date);
                    timeSlot.setStartTime(startTime);
                    timeSlot.setDuration(15);
                    timeSlot.setReserved(false);
                    timeSlots.add(timeSlot);
                }
            }
        }

        // Save the newly created time slots
        timeSlotRepository.saveAll(timeSlots);
    }

    @Override
    public List<TimeSlotDto> findFreeTimeSlots(Long gymId, LocalDate date) {
        List<TimeSlot> free = timeSlotRepository.findAllByGymIdAndDateAndIsReserved(gymId, date, false);
        return free.stream().map(timeSlotMapper::timeSlotToResponseDto).collect(Collectors.toList());
    }

}
