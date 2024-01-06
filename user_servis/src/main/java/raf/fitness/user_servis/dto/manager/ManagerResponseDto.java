package raf.fitness.user_servis.dto.manager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String role;
}
