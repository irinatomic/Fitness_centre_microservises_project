package raf.fitness.reservation_servis.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "Gym")
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Length(max = 1000)
    private String description;
    private Integer coachesCount;
    @Column(unique = true)
    private Integer managerId;
    private Integer freeSessionNo;
    private LocalTime openingTime;
    private LocalTime closingTime;

    public Gym() {}

    public Gym(String name, String description, Integer coachesCount, Integer managerId, Integer freeSessionNo, LocalTime openingTime, LocalTime closingTime) {
    	this.name = name;
    	this.description = description;
    	this.coachesCount = coachesCount;
    	this.managerId = managerId;
    	this.freeSessionNo = freeSessionNo;
    	this.openingTime = openingTime;
    	this.closingTime = closingTime;
    }

}
