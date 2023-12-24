package raf.fitness.reservation_servis.service;

import raf.fitness.reservation_servis.dto.training.*;

import java.util.List;

public interface TrainingService {

    // get all trainings for a gym (what does the gym offer)
    List<TrainingResponseDto> findAllForGym(Long gymId);

    // get all trainings for a gym and a training type
    List<TrainingResponseDto> findAllForGymAndTrainingType(Long gymId, Long trainingTypeId);

    // add a training to a gym
    TrainingResponseDto add(TrainingRequestDto requestDto);

    // update a training for a gym
    TrainingResponseDto update(Long id, TrainingRequestDto requestDto);
}
