package raf.fitness.reservation_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "SignedUp")
public class SignedUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToOne
    private TrainingSession trainingSession;

    public SignedUp() {}

    public SignedUp(Long clientId, String firstName, String lastName, String email, TrainingSession trainingSession) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.trainingSession = trainingSession;
    }
}
