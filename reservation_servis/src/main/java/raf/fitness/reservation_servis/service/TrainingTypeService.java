package raf.fitness.reservation_servis.service;

import raf.fitness.reservation_servis.dto.TrainingTypeDto;

import java.util.List;

public interface TrainingTypeService {

    // can be a list instead of page because there are
    // only 2 training types -> predefined
    List<TrainingTypeDto> findAll();
}
