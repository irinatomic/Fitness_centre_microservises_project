package raf.fitness.user_servis.dto.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String membershipNumber;
    private String role;
}
