package raf.fitness.user_servis.service;

import raf.fitness.user_servis.dto.manager.*;
import raf.fitness.user_servis.dto.token.*;

import java.util.List;

public interface ManagerService {

    ManagerResponseDto findById(Long id);

    ManagerResponseDto add(ManagerRequestDto managerRequestDto);

    ManagerResponseDto activate(Long id);

    ManagerResponseDto update(Long id, ManagerRequestDto managerRequestDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    void logout(Long id);

    void delete(Long id);

    List<String> getForbiddenClients();
}
