package raf.fitness.user_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.fitness.user_servis.domain.User;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<User, Long> {

    Optional<User> findAdminByUsername(String username);
}
