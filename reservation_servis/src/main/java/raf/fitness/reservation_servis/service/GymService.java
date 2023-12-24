package raf.fitness.reservation_servis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.fitness.reservation_servis.dto.gym.*;

public interface GymService {

    Page<GymResponseDto> findAll(Pageable pageable);

    GymResponseDto create(GymRequestDto gymRequestDto);

    GymResponseDto update(Long id, GymRequestDto gymRequestDto);

    GymResponseDto changeFreeSessionNo(Long id, Integer freeSessionNo);
}
