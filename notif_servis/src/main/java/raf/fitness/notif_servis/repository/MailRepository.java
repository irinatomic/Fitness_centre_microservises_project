package raf.fitness.notif_servis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.fitness.notif_servis.domain.Mail;
import raf.fitness.notif_servis.domain.MailType;

import java.time.LocalDate;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    // Admin calls

    Page<Mail> findAll(Pageable pageable);

    Page<Mail> findAllByMailType(MailType mailType, Pageable pageable);

    Page<Mail> findAllByTimestampBetween(LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable);

    Page<Mail> findAllByMailTypeAndTimestampBetween(MailType mailType, LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable);

    // Manager and client calls

    Page<Mail> findAllBySentTo(String sentTo, Pageable pageable);

    Page<Mail> findAllBySentToAndMailType(String sentTo, MailType mailType, Pageable pageable);

    Page<Mail> findAllBySentToAndTimestampBetween(String sentTo, LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable);

    Page<Mail> findAllBySentToAndMailTypeAndTimestampBetween(String sentTo, MailType mailType, LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable);
}
