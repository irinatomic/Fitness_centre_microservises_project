package raf.fitness.user_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Admin")
public class Admin extends User {

    @ManyToOne
    private Role role;

    public Admin() {
        super();
    }

    public Admin(String email, String firstName, String lastName, String username, String password, String phoneNumber, Role role) {
        super(email, firstName, lastName, username, password, phoneNumber);
        this.role = role;
    }
}
