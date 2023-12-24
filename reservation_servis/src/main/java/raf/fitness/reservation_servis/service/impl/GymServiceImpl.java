package raf.fitness.reservation_servis.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.fitness.reservation_servis.domain.Gym;
import raf.fitness.reservation_servis.dto.gym.*;
import raf.fitness.reservation_servis.mapper.GymMapper;
import raf.fitness.reservation_servis.repository.GymRepository;
import raf.fitness.reservation_servis.service.GymService;

import javax.transaction.Transactional;

@Service
@Transactional
public class GymServiceImpl implements GymService {

    private GymRepository gymRepository;
    private GymMapper gymMapper;

    public GymServiceImpl(GymRepository gymRepository, GymMapper gymMapper) {
        this.gymRepository = gymRepository;
        this.gymMapper = gymMapper;
    }

    @Override
    public Page<GymResponseDto> findAll(Pageable pageable) {
        return gymRepository.findAll(pageable).map(gymMapper::gymToResponseDto);
    }

    @Override
    public GymResponseDto create(GymRequestDto gymRequestDto) {
        Gym newGym = gymMapper.requestDtoToGym(gymRequestDto);
        gymRepository.save(newGym);
        return gymMapper.gymToResponseDto(newGym);
    }

    @Override
    public GymResponseDto update(Long id, GymRequestDto gymRequestDto) {
        Gym toChange = gymRepository.findById(id).orElseThrow(() -> new RuntimeException("Gym not found"));
        toChange.setName(gymRequestDto.getName());
        toChange.setDescription(gymRequestDto.getDescription());
        toChange.setCoachesCount(gymRequestDto.getCoachesCount());
        toChange.setFreeSessionNo(gymRequestDto.getFreeSessionNo());
        toChange.setOpeningTime(gymRequestDto.getOpeningTime());
        toChange.setClosingTime(gymRequestDto.getClosingTime());
        return gymMapper.gymToResponseDto(toChange);
    }

    @Override
    public GymResponseDto changeFreeSessionNo(Long id, Integer freeSessionNo) {
        Gym toChange = gymRepository.findById(id).orElseThrow(() -> new RuntimeException("Gym not found"));
        toChange.setFreeSessionNo(freeSessionNo);
        return gymMapper.gymToResponseDto(toChange);
    }
}
