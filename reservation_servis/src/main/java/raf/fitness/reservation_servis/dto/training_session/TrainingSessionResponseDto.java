package raf.fitness.reservation_servis.dto.training_session;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainingSessionResponseDto {

    private Long id;
    private Integer creatorId;
    private Integer signedUpCount;
    private String trainingTypeName;
    private String date;
    private String startTime;
    private Integer trainingId;
    private Integer gymId;
    private List<String> signedUpUsersEmails;
}
