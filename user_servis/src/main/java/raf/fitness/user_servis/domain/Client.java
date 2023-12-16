package raf.fitness.user_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Client")
public class Client extends User {

    @ManyToOne
    private Role role;
    @Column(unique = true)
    private String membershipNumber;
    private Integer trainingsBookedNo;

    public Client() {
        super();
    }

    public Client(String email, String firstName, String lastName, String username, String password, String phoneNumber, String membershipNumber, Integer trainingsBookedNo) {
        super(email, firstName, lastName, username, password, phoneNumber);
        this.membershipNumber = membershipNumber;
        this.trainingsBookedNo = trainingsBookedNo;
    }
}
