package raf.fitness.reservation_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.reservation_servis.domain.SignedUp;

import java.util.List;

@Repository
public interface SignedUpRepository extends JpaRepository<SignedUp, Long> {

    List<SignedUp> findAllByTrainingSessionId(Long trainingSessionId);
}
