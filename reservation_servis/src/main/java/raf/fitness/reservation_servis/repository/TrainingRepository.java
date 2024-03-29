package raf.fitness.reservation_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import raf.fitness.reservation_servis.domain.Training;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByGymId(Long gymId);

    @Query("SELECT t FROM Training t WHERE t.gym.id = :gymId AND t.trainingType.id = :trainingTypeId")
    List<Training> findAllByGymIdAndTrainingTypeId(Long gymId, Long trainingTypeId);
}
