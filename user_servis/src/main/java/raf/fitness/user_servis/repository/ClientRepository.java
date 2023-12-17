package raf.fitness.user_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.user_servis.domain.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUsername(String username);

    Optional<Client> findByUsernameAndActivatedAndForbidden(String username, boolean active, boolean forbidden);

    Optional<Client> findByIdAndLoggedin(Long id, boolean loggedin);
}
