package raf.fitness.user_servis.service;

import raf.fitness.user_servis.dto.admin.*;
import raf.fitness.user_servis.dto.token.*;

public interface AdminService {

    AdminResponseDto update(Long id, AdminRequestDto adminRequestDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    void forbid(TokenRequestDto tokenRequestDto, Long forbiddenId, String forbiddenRole);

    void unforbid(TokenRequestDto tokenRequestDto, Long forbiddenId, String forbiddenRole);
}
