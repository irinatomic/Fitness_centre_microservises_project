package raf.fitness.notif_servis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.fitness.notif_servis.dto.mail_type.*;

public interface MailTypeService {

    Page<MailTypeResponseDto> findAll(Pageable pageable);

    MailTypeResponseDto add(MailTypeRequestDto mailTypeRequestDto);

    MailTypeResponseDto update(Long id, MailTypeRequestDto mailTypeRequestDto);

    void delete(Long id);
}
