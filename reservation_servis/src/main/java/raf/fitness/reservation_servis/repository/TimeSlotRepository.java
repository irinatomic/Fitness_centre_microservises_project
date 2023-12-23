package raf.fitness.reservation_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.reservation_servis.domain.TimeSlot;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    // filter free time slots by date
    List<TimeSlot> findAllByIsReservedAndDate(Boolean isReserved, String date);
}
