package raf.fitness.user_servis.service;

import raf.fitness.user_servis.dto.admin.*;
import raf.fitness.user_servis.dto.token.*;

public interface AdminService {

    AdminResponseDto findById(Long id);

    AdminResponseDto update(Long id, AdminRequestDto adminRequestDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    void logout(Long id);

    void forbid(String forbiddenUsername, String forbiddenRole);

    void unforbid(String forbiddenUsername, String forbiddenRole);
}
