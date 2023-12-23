package raf.fitness.reservation_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.SignedUp;
import raf.fitness.reservation_servis.dto.SignedUpDto;
import raf.fitness.reservation_servis.repository.TrainingSessionRepository;

@Component
public class SignedUpMapper {

    private TrainingSessionRepository trainingSessionRepository;

    public SignedUpMapper(TrainingSessionRepository trainingSessionRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
    }

    public SignedUp requestDtoToSignedUp(SignedUpDto dto) {
        SignedUp signedUp = new SignedUp();
        signedUp.setClientId(dto.getClientId());
        signedUp.setFirstName(dto.getFirstName());
        signedUp.setLastName(dto.getLastName());
        signedUp.setEmail(dto.getEmail());
        signedUp.setTrainingSession(trainingSessionRepository.findById(dto.getTrainingSessionId()).orElse(null));
        return signedUp;
    }
}
