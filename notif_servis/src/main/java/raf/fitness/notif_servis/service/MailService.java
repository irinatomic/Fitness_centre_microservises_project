package raf.fitness.notif_servis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.fitness.notif_servis.dto.mail.MailResponseDto;

import java.time.LocalDate;

public interface MailService {

    // Admin calls

    Page<MailResponseDto> findAll(Pageable pageable);

    Page<MailResponseDto> findAllByMailType(String mailType, Pageable pageable);

    Page<MailResponseDto> findAllByTimestampBetween(LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable);

    Page<MailResponseDto> findAllByMailTypeAndTimestampBetween(String mailType, LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable);

    // Manager and client calls

    Page<MailResponseDto> findAllBySentTo(String sentTo, Pageable pageable);

    Page<MailResponseDto> findAllBySentToAndMailType(String sentTo, String mailType, Pageable pageable);

    Page<MailResponseDto> findAllBySentToAndTimestampBetween(String sentTo, LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable);

    Page<MailResponseDto> findAllBySentToAndMailTypeAndTimestampBetween(String sentTo, String mailType, LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable);
}
