package raf.fitness.user_servis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.fitness.user_servis.dto.client.ClientRequestDto;
import raf.fitness.user_servis.dto.client.ClientResponseDto;
import raf.fitness.user_servis.dto.token.TokenRequestDto;
import raf.fitness.user_servis.dto.token.TokenResponseDto;
import raf.fitness.user_servis.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    ClientResponseDto add(ClientRequestDto clientRequestDto){

    }

    ClientResponseDto activate(Long id){

    }

    ClientResponseDto update(Long id, ClientRequestDto clientRequestDto){

    }

    TokenResponseDto login(TokenRequestDto tokenRequestDto){

    }

    void delete(Long id){

    }

}
