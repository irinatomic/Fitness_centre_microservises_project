package raf.fitness.reservation_servis.dto.training_session;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class TrainingSessionResponseDto {

    private Long id;
    private Long creatorId;
    private Long gymManagerId;
    private Integer signedUpCount;
    private Integer capacity;
    private String trainingName;
    private LocalDate date;
    private LocalTime startTime;
    private Long trainingId;
    private Long gymId;
    private List<String> signedUpUsersEmails;
}
