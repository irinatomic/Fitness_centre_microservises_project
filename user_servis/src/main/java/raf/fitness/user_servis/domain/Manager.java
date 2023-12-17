package raf.fitness.user_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Manager")
public class Manager extends User {

    private String companyName;
    private LocalDate dateOfEmployment;
    private Boolean forbidden;

    public Manager() {
        super();
    }

    public Manager(Role role, String email, String firstName, String lastName, String username, String password, String phoneNumber, String companyName) {
        super(role, email, firstName, lastName, username, password, phoneNumber);
        this.companyName = companyName;
        this.dateOfEmployment = LocalDate.now();
        this.forbidden = false;
    }
}
