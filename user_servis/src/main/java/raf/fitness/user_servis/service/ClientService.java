package raf.fitness.user_servis.service;

import raf.fitness.user_servis.dto.client.*;
import raf.fitness.user_servis.dto.token.*;

public interface ClientService {

    ClientResponseDto add(ClientRequestDto clientRequestDto);

    ClientResponseDto activate(Long id);

    // Todo: ne moze ako nije loggedin
    ClientResponseDto update(Long id, ClientRequestDto clientRequestDto);

    // Todo: boolean loggedIn && ne moze ako je forbidden
    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    // Todo: ne moze ako nije loggedin
    void delete(Long id);
}
