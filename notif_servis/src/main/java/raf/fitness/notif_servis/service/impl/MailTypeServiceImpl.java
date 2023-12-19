package raf.fitness.notif_servis.service.impl;

import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.fitness.notif_servis.domain.MailType;
import raf.fitness.notif_servis.dto.mail_type.*;
import raf.fitness.notif_servis.mapper.MailTypeMapper;
import raf.fitness.notif_servis.repository.MailTypeRepository;
import raf.fitness.notif_servis.service.MailTypeService;

import javax.transaction.Transactional;

@Service
@Transactional
public class MailTypeServiceImpl implements MailTypeService {

    private MailTypeRepository mailTypeRepository;
    private MailTypeMapper mailTypeMapper;

    public MailTypeServiceImpl(MailTypeRepository mailTypeRepository, MailTypeMapper m) {
        this.mailTypeRepository = mailTypeRepository;
        this.mailTypeMapper = m;
    }


    @Override
    public Page<MailTypeResponseDto> findAll(Pageable pageable) {
        return mailTypeRepository.findAll(pageable).map(mailTypeMapper::mailTypeToMTResponseDto);
    }

    @Override
    public MailTypeResponseDto add(MailTypeRequestDto mailTypeRequestDto) {
        MailType newMT = mailTypeMapper.mailTypeRequestDtoToMailType(mailTypeRequestDto);
        mailTypeRepository.save(newMT);
        return mailTypeMapper.mailTypeToMTResponseDto(newMT);
    }

    @Override
    @SneakyThrows
    public MailTypeResponseDto update(Long id, MailTypeRequestDto mailTypeRequestDto) {
        MailType toChange = mailTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Mail type with id: %d not found.", id)));

        toChange.setName(mailTypeRequestDto.getName());
        toChange.setSubject(mailTypeRequestDto.getSubject());
        toChange.setText(mailTypeRequestDto.getText());
        mailTypeRepository.save(toChange);
        return mailTypeMapper.mailTypeToMTResponseDto(toChange);
    }


    @Override
    public void delete(Long id) {
        mailTypeRepository.deleteById(id);
    }
}
