package raf.fitness.user_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Admin")
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(Role role, String email, String firstName, String lastName, String username, String password, String phoneNumber) {
        super(role, email, firstName, lastName, username, password, phoneNumber);
    }
}
