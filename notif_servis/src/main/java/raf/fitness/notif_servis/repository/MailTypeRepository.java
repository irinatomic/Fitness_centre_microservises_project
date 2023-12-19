package raf.fitness.notif_servis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.notif_servis.domain.MailType;

import java.util.Optional;

@Repository
public interface MailTypeRepository extends JpaRepository<MailType, Long> {

    Page<MailType> findAll(Pageable pageable);

    Optional<MailType> findByName(String name);
}
