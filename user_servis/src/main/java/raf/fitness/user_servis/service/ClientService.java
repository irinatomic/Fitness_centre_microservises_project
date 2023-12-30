package raf.fitness.user_servis.service;

import raf.fitness.user_servis.dto.client.*;
import raf.fitness.user_servis.dto.token.*;

public interface ClientService {

    ClientResponseDto add(ClientRequestDto clientRequestDto);

    ClientResponseDto activate(Long id);

    ClientResponseDto update(Long id, ClientRequestDto clientRequestDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    void logout(Long id);

    void delete(Long id);

    Integer getClientsBookedNo(Long id);
}
