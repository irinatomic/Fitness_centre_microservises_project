package raf.fitness.reservation_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "TimeSlot", indexes = {
        @Index(name = "idx_date", columnList = "date")
})
public class TimeSlot {

    // each time slot is a 15-minute interval

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private Integer duration = 15;
    private boolean isReserved;
    @ManyToOne
    private TrainingSession trainingSession;

    public TimeSlot() {}

    public TimeSlot(LocalDate date, LocalTime startTime, boolean isReserved) {
        this.date = date;
        this.startTime = startTime;
        this.isReserved = isReserved;
    }

}
