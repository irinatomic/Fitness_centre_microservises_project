package raf.fitness.user_servis.dto.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDto {
    private String token;

    public TokenResponseDto() {
    }

    public TokenResponseDto(String token) {
        this.token = token;
    }

}
