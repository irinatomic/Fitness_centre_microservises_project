package raf.fitness.user_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.fitness.user_servis.domain.Manager;
import raf.fitness.user_servis.domain.User;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<User> findManagerByUsername(String username);
}
