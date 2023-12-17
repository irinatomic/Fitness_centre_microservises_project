package raf.fitness.user_servis.service;

import raf.fitness.user_servis.dto.manager.*;
import raf.fitness.user_servis.dto.token.*;

public interface ManagerService {

    ManagerResponseDto add(ManagerRequestDto managerRequestDto);

    ManagerResponseDto activate(Long id);

    // Todo: ne moze ako nije loggedin
    ManagerResponseDto update(Long id, ManagerRequestDto managerRequestDto);

    // Todo: boolean loggedIn && ne moze ako je forbidden
    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    // Todo: ne moze ako nije loggedin
    void delete(Long id);

    // Todo: ne moze ako nije loggedin
    void giveFreeTraining(Long id); // client id

    //Todo: logOut metoda
}
