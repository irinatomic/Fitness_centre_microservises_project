package raf.fitness.reservation_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.reservation_servis.domain.SignedUp;

import java.util.List;
import java.util.Optional;

@Repository
public interface SignedUpRepository extends JpaRepository<SignedUp, Long> {

    // used when creating a training session response dto
    List<SignedUp> findAllByTrainingSessionId(Long trainingSessionId);

    // when finding a user that wants to pull out of a session
    Optional<SignedUp> findByTrainingSessionIdAndClientId(Long trainingSessionId, Long clientId);

    Optional<SignedUp> findByClientIdAndTrainingSessionId(Long clientId, Long trainingSessionId);

    void deleteAllByTrainingSessionId(Long trainingSessionId);
}
