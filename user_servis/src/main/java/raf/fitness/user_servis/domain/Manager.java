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

    @ManyToOne
    private Role role;
    private String companyName;
    private LocalDate dateOfEmployment;
    private Boolean forbidden;

    public Manager() {
        super();
    }

    public Manager(String email, String firstName, String lastName, String username, String password, String phoneNumber, Role role, String companyName, LocalDate dateOfEmployment, Boolean forbidden) {
        super(email, firstName, lastName, username, password, phoneNumber);
        this.role = role;
        this.companyName = companyName;
        this.dateOfEmployment = dateOfEmployment;
        this.forbidden = forbidden;
    }
}
