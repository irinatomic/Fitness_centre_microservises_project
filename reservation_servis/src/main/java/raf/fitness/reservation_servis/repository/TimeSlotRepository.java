package raf.fitness.reservation_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.fitness.reservation_servis.domain.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    // when creating time slots for a gym
    @Query("SELECT t FROM TimeSlot t WHERE t.gym.id = :gymId AND t.date > :date")
    List<TimeSlot> findAllByGymIdAndDateAfter(@Param("gymId") Long gymId, @Param("date") LocalDate date);

    // for filtering free time slots
    List<TimeSlot> findAllByGymIdAndDateAndIsReserved(Long gymId, LocalDate date, Boolean isReserved);

    // when reserving a session -> make the time slot reserved
    Optional<TimeSlot> findByGymIdAndDateAndStartTime(Long gymId, LocalDate date, LocalTime startTime);

    // when wanting to free up time slots after a session is canceller
    List<TimeSlot> findAllByTrainingSessionId(Long trainingSessionId);

}
