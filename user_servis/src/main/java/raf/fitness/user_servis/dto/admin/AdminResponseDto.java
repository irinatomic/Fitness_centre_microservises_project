package raf.fitness.user_servis.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponseDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;

}
