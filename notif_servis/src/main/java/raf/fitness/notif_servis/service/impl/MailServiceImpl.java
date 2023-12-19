package raf.fitness.notif_servis.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.fitness.notif_servis.domain.MailType;
import raf.fitness.notif_servis.dto.mail.MailResponseDto;
import raf.fitness.notif_servis.mapper.MailMapper;
import raf.fitness.notif_servis.repository.MailRepository;
import raf.fitness.notif_servis.repository.MailTypeRepository;
import raf.fitness.notif_servis.service.MailService;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class MailServiceImpl implements MailService {

    private MailRepository mailRepository;
    private MailTypeRepository mailTypeRepository;
    private MailMapper mailMapper;

    public MailServiceImpl(MailRepository mailRepository, MailTypeRepository mailTypeRepository, MailMapper mailMapper) {
        this.mailRepository = mailRepository;
        this.mailTypeRepository = mailTypeRepository;
        this.mailMapper = mailMapper;
    }

    @Override
    public Page<MailResponseDto> findAll(Pageable pageable) {
        return mailRepository.findAll(pageable).map(mailMapper::mailToMailResponseDto);
    }

    @Override
    public Page<MailResponseDto> findAllByMailType(String mailType, Pageable pageable) {
        MailType mt = mailTypeRepository.findByName(mailType).orElseThrow(() -> new RuntimeException("Mail type not found"));
        return mailRepository.findAllByMailType(mt, pageable).map(mailMapper::mailToMailResponseDto);
    }

    @Override
    public Page<MailResponseDto> findAllByTimestampBetween(LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable) {
        return mailRepository.findAllByTimestampBetween(timestampFrom, timestampTo, pageable).map(mailMapper::mailToMailResponseDto);
    }

    @Override
    public Page<MailResponseDto> findAllByMailTypeAndTimestampBetween(String mailType, LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable) {
        MailType mt = mailTypeRepository.findByName(mailType).orElseThrow(() -> new RuntimeException("Mail type not found"));
        return mailRepository.findAllByMailTypeAndTimestampBetween(mt, timestampFrom, timestampTo, pageable).map(mailMapper::mailToMailResponseDto);
    }

    @Override
    public Page<MailResponseDto> findAllBySentTo(String sentTo, Pageable pageable) {
        return mailRepository.findAllBySentTo(sentTo, pageable).map(mailMapper::mailToMailResponseDto);
    }

    @Override
    public Page<MailResponseDto> findAllBySentToAndMailType(String sentTo, String mailType, Pageable pageable) {
        MailType mt = mailTypeRepository.findByName(mailType).orElseThrow(() -> new RuntimeException("Mail type not found"));
        return mailRepository.findAllBySentToAndMailType(sentTo, mt, pageable).map(mailMapper::mailToMailResponseDto);
    }

    @Override
    public Page<MailResponseDto> findAllBySentToAndTimestampBetween(String sentTo, LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable) {
        return mailRepository.findAllBySentToAndTimestampBetween(sentTo, timestampFrom, timestampTo, pageable).map(mailMapper::mailToMailResponseDto);
    }

    @Override
    public Page<MailResponseDto> findAllBySentToAndMailTypeAndTimestampBetween(String sentTo, String mailType, LocalDate timestampFrom, LocalDate timestampTo, Pageable pageable) {
        MailType mt = mailTypeRepository.findByName(mailType).orElseThrow(() -> new RuntimeException("Mail type not found"));
        return mailRepository.findAllBySentToAndMailTypeAndTimestampBetween(sentTo, mt, timestampFrom, timestampTo, pageable).map(mailMapper::mailToMailResponseDto);
    }
}
