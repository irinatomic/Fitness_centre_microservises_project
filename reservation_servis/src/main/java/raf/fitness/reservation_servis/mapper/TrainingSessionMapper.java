package raf.fitness.reservation_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.SignedUp;
import raf.fitness.reservation_servis.domain.TrainingSession;
import raf.fitness.reservation_servis.dto.training_session.*;
import raf.fitness.reservation_servis.repository.GymRepository;
import raf.fitness.reservation_servis.repository.SignedUpRepository;
import raf.fitness.reservation_servis.repository.TrainingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingSessionMapper {

    private TrainingRepository trainingRepository;
    private SignedUpRepository signedUpRepository;
    private GymRepository gymRepository;

    public TrainingSessionMapper(TrainingRepository trainingRepository, SignedUpRepository signedUpRepository, GymRepository gymRepository) {
        this.trainingRepository = trainingRepository;
        this.signedUpRepository = signedUpRepository;
        this.gymRepository = gymRepository;
    }

    public TrainingSession requestDtoToTrainingSession(TrainingSessionRequestDto dto) {
        TrainingSession ts = new TrainingSession();
        ts.setCreatorId(dto.getCreatorId());
        ts.setSignedUpCount(1);
        ts.setDate(dto.getDate());
        ts.setStartTime(dto.getStartTime());
        ts.setTraining(trainingRepository.findById(dto.getTrainingId()).orElse(null));
        ts.setGym(gymRepository.findById(dto.getGymId()).orElse(null));
        // not setting: id, signedUpClients
        return ts;
    }

    public TrainingSessionResponseDto trainingSessionToResponseDto(TrainingSession ts) {
        TrainingSessionResponseDto dto = new TrainingSessionResponseDto();
        dto.setId(ts.getId());
        dto.setCreatorId(ts.getCreatorId());
        dto.setSignedUpCount(ts.getSignedUpCount());
        dto.setCapacity(ts.getTraining().getCapacity());
        dto.setTrainingName(ts.getTraining().getName());
        dto.setDate(ts.getDate());
        dto.setStartTime(ts.getStartTime());
        dto.setTrainingId(ts.getTraining().getId());
        dto.setGymId(ts.getGym().getId());
        dto.setGymManagerId(ts.getGym().getManagerId());

        // set signedUpUsersEmails
        List<SignedUp> signedUp = signedUpRepository.findAllByTrainingSessionId(ts.getId());
        List<String> emails = signedUp.stream()
                        .map(SignedUp::getEmail)
                        .collect(Collectors.toList());
        dto.setSignedUpUsersEmails(emails);
        return dto;
    }
}
