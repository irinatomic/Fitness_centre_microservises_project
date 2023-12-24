package raf.fitness.reservation_servis.service.impl;

import org.springframework.stereotype.Service;
import raf.fitness.reservation_servis.domain.Training;
import raf.fitness.reservation_servis.dto.training.*;
import raf.fitness.reservation_servis.mapper.TrainingMapper;
import raf.fitness.reservation_servis.repository.GymRepository;
import raf.fitness.reservation_servis.repository.TrainingRepository;
import raf.fitness.reservation_servis.repository.TrainingTypeRepository;
import raf.fitness.reservation_servis.service.TrainingService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

    private TrainingTypeRepository trainingTypeRepository;
    private TrainingRepository trainingRepository;
    private GymRepository gymRepository;
    private TrainingMapper trainingMapper;

    public TrainingServiceImpl(TrainingTypeRepository trainingTypeRepository, TrainingRepository trainingRepository, GymRepository gymRepository, TrainingMapper trainingMapper) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingRepository = trainingRepository;
        this.gymRepository = gymRepository;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public List<TrainingResponseDto> findAllForGym(Long gymId) {
        List<Training> offer = trainingRepository.findAllByGymId(gymId);
        return offer.stream().map(trainingMapper::trainingToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<TrainingResponseDto> findAllForGymAndTrainingType(Long gymId, Long trainingTypeId) {
        List<Training> offer = trainingRepository.findAllByGymIdAndTrainingTypeId(gymId, trainingTypeId);
        return offer.stream().map(trainingMapper::trainingToResponseDto).collect(Collectors.toList());
    }

    @Override
    public TrainingResponseDto add(Long managerId, TrainingRequestDto requestDto) {
        Training t = trainingMapper.requestDtoToTraining(requestDto);

        // check if managerId = t.getGym().getManagerId()
        if (!managerId.equals(t.getGym().getManagerId())) {
            throw new RuntimeException("You are not authorized to change this training");
        }

        trainingRepository.save(t);
        return trainingMapper.trainingToResponseDto(t);
    }

    @Override
    public TrainingResponseDto update(Long id, Long managerId, TrainingRequestDto requestDto) {
        Training t = trainingRepository.findById(id).orElseThrow(() ->  new RuntimeException("Training with id " + id + " does not exist"));

        // check if managerId = t.getGym().getManagerId()
        if (!managerId.equals(t.getGym().getManagerId())) {
            throw new RuntimeException("You are not authorized to change this training");
        }

        t.setName(requestDto.getName());
        t.setPrice(requestDto.getPrice());
        t.setDuration(requestDto.getDuration());
        t.setCapacity(requestDto.getCapacity());
        t.setMinPeopleNo(requestDto.getMinPeopleNo());
        t.setGym(gymRepository.findById(requestDto.getGymId()).orElse(null));
        t.setTrainingType(trainingTypeRepository.findById(requestDto.getTrainingTypeId()).orElse(null));
        return trainingMapper.trainingToResponseDto(t);
    }
}
