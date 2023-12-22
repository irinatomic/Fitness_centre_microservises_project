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
    private Integer clientId;
    private String clientFirstName;
    private String clientLastName;
    private String clientEmail;
    @ManyToOne
    private TrainingSession trainingSession;

    public SignedUp() {}

    public SignedUp(Integer clientId, String clientFirstName, String clientLastName, String clientEmail) {
        this.clientId = clientId;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.clientEmail = clientEmail;
    }
}
