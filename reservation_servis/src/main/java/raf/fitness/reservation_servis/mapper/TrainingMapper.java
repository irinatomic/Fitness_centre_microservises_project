package raf.fitness.reservation_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.Training;
import raf.fitness.reservation_servis.domain.TrainingType;
import raf.fitness.reservation_servis.dto.training.*;
import raf.fitness.reservation_servis.repository.GymRepository;
import raf.fitness.reservation_servis.repository.TrainingTypeRepository;

@Component
public class TrainingMapper {

    private GymRepository gymRepository;
    private TrainingTypeRepository trainingTypeRepository;

    public TrainingMapper(GymRepository gymRepository, TrainingTypeRepository trainingTypeRepository) {
        this.gymRepository = gymRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    public Training requestDtoToTraining(TrainingRequestDto requestDto) {
        Training training = new Training();
        training.setName(requestDto.getName());
        training.setPrice(requestDto.getPrice());
        training.setDuration(requestDto.getDuration());
        training.setCapacity(requestDto.getCapacity());
        training.setGym(gymRepository.findById(requestDto.getGymId()).orElse(null));

        TrainingType trainingType = trainingTypeRepository.findById(requestDto.getTrainingTypeId()).orElse(null);
        training.setTrainingType(trainingType);

        if(trainingType.getName().equalsIgnoreCase("GROUP"))
            training.setMinPeopleNo(requestDto.getMinPeopleNo());
        else training.setMinPeopleNo(1);

        return training;
    }

    public TrainingResponseDto trainingToResponseDto(Training training) {
        TrainingResponseDto responseDto = new TrainingResponseDto();
        responseDto.setId(training.getId());
        responseDto.setName(training.getName());
        responseDto.setPrice(training.getPrice());
        responseDto.setDuration(training.getDuration());
        responseDto.setCapacity(training.getCapacity());
        responseDto.setMinPeopleNo(training.getMinPeopleNo());
        responseDto.setGymId(training.getGym().getId());
        responseDto.setTrainingType(training.getTrainingType().getName());
        return responseDto;
    }
}
