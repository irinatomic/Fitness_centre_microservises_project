package raf.fitness.reservation_servis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.fitness.reservation_servis.dto.SignedUpDto;
import raf.fitness.reservation_servis.dto.training_session.*;

import java.time.LocalDate;
import java.util.List;

public interface TrainingSessionService {

    // create a session
    TrainingSessionResponseDto create(TrainingSessionRequestDto trainingSessionRequestDto, SignedUpDto creator);

    // sign up for a session that is created
    void signUp(Long sessionId, SignedUpDto user);

    void cancelAsUser(Long sessionId, Long userId);

    void cancelAsManager(Long managerId, Long sessionId);

    // delete a session - used by the cron job
    void delete(Long sessionId);

    // get all sessions for a gym
    List<TrainingSessionResponseDto> getAllForGym(Long gymId);

    // filter: gym + date
    List<TrainingSessionResponseDto> getAllForGymAndDate(Long gymId, LocalDate date);

    // filter: gym + training type
    List<TrainingSessionResponseDto> getAllForGymAndTrainingType(Long gymId, Long trainingTypeId);

    // filter: gym + date + training type
    List<TrainingSessionResponseDto> getAllForGymAndDateAndTrainingType(Long gymId, LocalDate date, Long trainingTypeId);

}
