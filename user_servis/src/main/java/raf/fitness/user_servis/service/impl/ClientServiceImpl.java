package raf.fitness.user_servis.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import raf.fitness.user_servis.async_comm.email.EmailSenderService;
import raf.fitness.user_servis.async_comm.email.EmailType;
import raf.fitness.user_servis.domain.Client;
import raf.fitness.user_servis.exception.NotFoundException;
import raf.fitness.user_servis.mapper.ClientMapper;
import raf.fitness.user_servis.repository.ClientRepository;
import raf.fitness.user_servis.security.service.TokenService;
import raf.fitness.user_servis.service.ClientService;
import raf.fitness.user_servis.dto.client.*;
import raf.fitness.user_servis.dto.token.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private TokenService tokenService;
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    // async communication
    private EmailSenderService emailSenderService;

    public ClientServiceImpl(TokenService tokenService, ClientRepository clientRepository, ClientMapper clientMapper, EmailSenderService emailSenderService) {
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return clientMapper.clientToClientResponseDto(clientRepository.findById(id).get());
    }

    @Override
    public ClientResponseDto add(ClientRequestDto clientRequestDto) {
        Client client = clientMapper.clientCreateRequestDtoToClient(clientRequestDto);
        clientRepository.save(client);

        // == Service 3 -> activation mail ==
        Map<String, String> params = new HashMap<>();
        params.put("firstName", client.getFirstName());
        params.put("lastName", client.getLastName());
        params.put("link", "http://localhost:8080/activate?role=client&id=" + client.getId());

        emailSenderService.sendMessageToQueue(EmailType.ACTIVATION, client.getEmail(), params);
        return clientMapper.clientToClientResponseDto(client);
    }

    @Override
    public ClientResponseDto activate(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        client.setActivated(true);
        return clientMapper.clientToClientResponseDto(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Integer getClientsBookedNo(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        return client.getTrainingsBookedNo();
    }

    @Override
    public List<String> getForbiddenClients() {
        List<Client> clients = clientRepository.findAllByForbidden(true);
        return clients.stream().map(Client::getUsername).collect(Collectors.toList());
    }

    @Override
    public ClientResponseDto update(Long id, ClientRequestDto clientRequestDto) {
        Client client = clientRepository.findByIdAndLoggedin(id, true).orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
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
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        // Try to find active user for specified credentials
        Client client = clientRepository.findByUsernameAndActivatedAndForbidden(tokenRequestDto.getUsername(), true, false).
                orElseThrow(() -> new NotFoundException(String.format("Client with username: %s not found.", tokenRequestDto.getUsername())));

        client.setLoggedin(true);
        // Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", client.getId());
        claims.put("role", client.getRole().getName());
        claims.put("email", client.getEmail());

        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public void logout(Long id) {
        Client client = clientRepository.findByIdAndLoggedin(id, true).
                orElseThrow(() -> new NotFoundException(String.format("Client with id: %s not found.", id)));
        client.setLoggedin(false);
    }
}
