package raf.fitness.user_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.user_servis.domain.Manager;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByUsernameAndActivatedAndForbidden(String username, boolean activated, boolean forbidden);

    Optional<Manager> findByIdAndLoggedin(Long id, boolean loggedin);

    Optional<Manager> findByUsername(String username);

    List<Manager> findAllByForbidden(boolean forbidden);
}
