package raf.fitness.user_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Role role;
    private Boolean activated;
    private Boolean deleted;
    private String email;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    private String password;
    private String phoneNumber;

    public User() {}

    public User(Role role, String email, String firstName, String lastName, String username, String password, String phoneNumber) {
        this.activated = false;
        this.deleted = false;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
