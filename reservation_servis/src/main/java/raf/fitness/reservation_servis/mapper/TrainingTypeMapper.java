package raf.fitness.reservation_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.TrainingType;
import raf.fitness.reservation_servis.dto.TrainingTypeDto;

@Component
public class TrainingTypeMapper {

    public TrainingTypeDto trainingTypeToResponseDto(TrainingType trainingType) {
        TrainingTypeDto dto = new TrainingTypeDto();
        dto.setId(trainingType.getId());
        dto.setName(trainingType.getName());
        return dto;
    }
}
