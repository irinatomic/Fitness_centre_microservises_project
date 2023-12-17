package raf.fitness.user_servis.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import raf.fitness.user_servis.domain.Client;
import raf.fitness.user_servis.mapper.ClientMapper;
import raf.fitness.user_servis.repository.ClientRepository;
import raf.fitness.user_servis.security.service.TokenService;
import raf.fitness.user_servis.service.ClientService;
import raf.fitness.user_servis.dto.client.*;
import raf.fitness.user_servis.dto.token.*;

import javax.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private TokenService tokenService;
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public ClientServiceImpl(TokenService tokenService, ClientRepository clientRepository, ClientMapper clientMapper) {
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientResponseDto add(ClientRequestDto clientRequestDto) {
        Client client = clientMapper.clientCreateRequestDtoToClient(clientRequestDto);
        clientRepository.save(client);
        return clientMapper.clientToClientResponseDto(client);
    }

    @Override
    @SneakyThrows // SneakyThrows is used to suppress the compile time exception
    public ClientResponseDto activate(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        client.setActivated(true);
        return clientMapper.clientToClientResponseDto(client);
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        client.setDeleted(true);
    }

    @Override
    @SneakyThrows
    public ClientResponseDto update(Long id, ClientRequestDto clientRequestDto) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        if(client.getActivated()) {
            client.setEmail(clientRequestDto.getEmail());
            client.setFirstName(clientRequestDto.getFirstName());
            client.setLastName(clientRequestDto.getLastName());
            client.setUsername(clientRequestDto.getUsername());
            client.setPassword(clientRequestDto.getPassword());
            client.setPhoneNumber(clientRequestDto.getPhoneNumber());
        }
        return clientMapper.clientToClientResponseDto(client);
    }

    @Override
    @SneakyThrows
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        // Try to find active user for specified credentials
        Client client = clientRepository.findByUsernameAndActivated(tokenRequestDto.getUsername(), true).
                orElseThrow(() -> new NotFoundException(String.format("Client with username: %s not found.", tokenRequestDto.getUsername())));

        // Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", client.getId());
        claims.put("role", client.getRole().getName());

        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
}
