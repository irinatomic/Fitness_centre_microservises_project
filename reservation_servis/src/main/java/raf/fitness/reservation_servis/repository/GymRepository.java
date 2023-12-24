package raf.fitness.reservation_servis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.reservation_servis.domain.Gym;

import java.util.Optional;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

    Page<Gym> findAll(Pageable pageable);
}
