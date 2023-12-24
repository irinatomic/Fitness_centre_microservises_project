package raf.fitness.reservation_servis.service.impl;

import org.springframework.stereotype.Service;
import raf.fitness.reservation_servis.domain.TrainingType;
import raf.fitness.reservation_servis.dto.TrainingTypeDto;
import raf.fitness.reservation_servis.mapper.TrainingTypeMapper;
import raf.fitness.reservation_servis.repository.TrainingTypeRepository;
import raf.fitness.reservation_servis.service.TrainingTypeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingTypeServiceImpl implements TrainingTypeService {

   private TrainingTypeRepository trainingTypeRepository;
   private TrainingTypeMapper trainingTypeMapper;

    public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper) {
         this.trainingTypeRepository = trainingTypeRepository;
         this.trainingTypeMapper = trainingTypeMapper;
    }

    @Override
    public List<TrainingTypeDto> findAll() {
        List<TrainingType> trainingTypes = trainingTypeRepository.findAll();

        // Convert List<TrainingType> to List<TrainingTypeDto> using streams and map
        return trainingTypes.stream().map(trainingTypeMapper::trainingTypeToResponseDto).collect(Collectors.toList());
    }
}
