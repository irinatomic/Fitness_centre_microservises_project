package raf.fitness.reservation_servis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.reservation_servis.domain.TrainingSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    List<TrainingSession> findAllByGymId(Long gymId);

    List<TrainingSession> findAllByGymIdAndDate(Long gymId, LocalDate date);

    List<TrainingSession> findAllByGymIdAndTrainingTypeName(Long gymId, String trainingTypeName);

    List<TrainingSession> findAllByGymIdAndTrainingTypeNameAndDate(Long gymId, String trainingTypeName, LocalDate date);

    // for a reminder (cron job)
    List<TrainingSession> findAllByDateAndStartTime(LocalDate date, LocalTime startTime);
}
