package raf.fitness.user_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Client")
public class Client extends User {

    @Column(unique = true)
    private String membershipNumber;
    private Integer trainingsBookedNo;
    private Boolean forbidden;

    public Client() {
        super();
    }

    public Client(Role role, String email, String firstName, String lastName, String username, String password, String phoneNumber, String membershipNumber) {
        super(role, email, firstName, lastName, username, password, phoneNumber);
        this.membershipNumber = membershipNumber;
        this.trainingsBookedNo = 0;
        this.forbidden = false;
    }
}
