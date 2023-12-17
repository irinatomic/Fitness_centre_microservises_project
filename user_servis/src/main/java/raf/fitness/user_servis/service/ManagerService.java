package raf.fitness.user_servis.service;

import raf.fitness.user_servis.dto.manager.*;
import raf.fitness.user_servis.dto.token.*;

public interface ManagerService {

    ManagerResponseDto add(ManagerRequestDto managerRequestDto);

    ManagerResponseDto activate(Long id);

    ManagerResponseDto update(Long id, ManagerRequestDto managerRequestDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    void logout(Long id);

    void delete(Long id);

    void giveFreeTraining(Long id); // client id

}
