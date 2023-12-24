package raf.fitness.reservation_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private Integer duration; // minutes
    private Integer capacity;
    private Integer minPeopleNo;
    @ManyToOne
    @JoinColumn(name = "gymId")
    private Gym gym;
    @ManyToOne
    @JoinColumn(name = "trainingTypeId")
    private TrainingType trainingType;

    public Training() {}

    public Training(String name, Integer price, Integer duration, Integer capacity, Integer minPeopleNo, Gym gym, TrainingType trainingType) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.capacity = capacity;
        this.minPeopleNo = minPeopleNo;
        this.gym = gym;
        this.trainingType = trainingType;
    }

}
