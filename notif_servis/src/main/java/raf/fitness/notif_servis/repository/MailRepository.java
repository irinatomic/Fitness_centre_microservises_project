package raf.fitness.notif_servis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.notif_servis.domain.Mail;

import java.time.LocalDate;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    Page<Mail> findAll(Pageable pageable);

    Page<Mail> findAllByMailType(String mailType, Pageable pageable);

    Page<Mail> findAllBySentTo(String sentTo, Pageable pageable);

    Page<Mail> findAllByTimestampBetween(LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable);
}
