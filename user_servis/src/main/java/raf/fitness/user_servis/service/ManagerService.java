package raf.fitness.user_servis.service;

import raf.fitness.user_servis.dto.manager.*;
import raf.fitness.user_servis.dto.token.*;

public interface ManagerService {

    ManagerResponseDto add(ManagerRequestDto managerRequestDto);

    ManagerResponseDto activate(Long id);

    ManagerResponseDto delete(Long id);

    ManagerResponseDto update(ManagerUpdateDto managerRequestDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
}
