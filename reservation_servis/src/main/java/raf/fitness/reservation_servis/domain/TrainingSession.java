package raf.fitness.reservation_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "TrainingSession", indexes = {
        @Index(name = "idx_trainingTypeName", columnList = "trainingTypeName")
})
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long creatorId;
    private Integer signedUpCount;
    private String trainingTypeName;
    private LocalDate date;
    private LocalTime startTime;
    @ManyToOne
    private Training training;
    @ManyToOne
    private Gym gym;

    public TrainingSession() {}

    public TrainingSession(Long creatorId, Integer signedUpCount, String trainingTypeName, LocalDate date, LocalTime startTime, Training training, Gym gym) {
        this.creatorId = creatorId;
        this.signedUpCount = signedUpCount;
        this.trainingTypeName = trainingTypeName;
        this.date = date;
        this.startTime = startTime;
        this.training = training;
        this.gym = gym;
    }
}
