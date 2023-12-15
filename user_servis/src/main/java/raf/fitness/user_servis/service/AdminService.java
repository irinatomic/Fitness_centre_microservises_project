package raf.fitness.user_servis.service;

import raf.fitness.user_servis.dto.admin.*;
import raf.fitness.user_servis.dto.token.*;

public interface AdminService {

    AdminResponseDto update(AdminRequestDto adminResponseDto);

    TokenResponseDto login(TokenRequestDto adminResponseDto);

    AdminResponseDto forbid(TokenRequestDto tokenRequestDto, Long forbiddenId);

    AdminResponseDto unforbid(TokenRequestDto tokenRequestDto, Long forbiddenId);
}
