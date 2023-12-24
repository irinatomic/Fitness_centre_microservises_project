package raf.fitness.reservation_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.reservation_servis.domain.TrainingType;

import java.util.List;

@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {

    List<TrainingType> findAll();
}
