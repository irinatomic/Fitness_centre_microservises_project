package raf.fitness.user_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.user_servis.domain.Client;
import raf.fitness.user_servis.dto.client.*;
import raf.fitness.user_servis.repository.RoleRepository;

@Component
public class ClientMapper {

    private RoleRepository roleRepository;

    public ClientMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // WHEN CREATING A CLIENT
    public Client clientCreateRequestDtoToClient(ClientRequestDto dto) {
        Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setUsername(dto.getUsername());
        client.setPassword(dto.getPassword());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setMembershipNumber("2023-" + System.currentTimeMillis());
        client.setTrainingsBookedNo(0);
        client.setActivated(false);
        client.setForbidden(false);
        client.setLoggedin(false);
        client.setRole(roleRepository.findByName("CLIENT").orElse(null));
        return client;
    }

    public Client clientRequestDtoToClient(ClientRequestDto dto) {
        Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setUsername(dto.getUsername());
        client.setPassword(dto.getPassword());
        client.setPhoneNumber(dto.getPhoneNumber());

        // CANNOT CHANGE MEMBERSHIP NUMBER AND TRAININGS BOOKED NO
        client.setRole(roleRepository.findByName("CLIENT").orElse(null));
        return client;
    }

    public ClientResponseDto clientToClientResponseDto(Client client) {
        ClientResponseDto dto = new ClientResponseDto();
        dto.setId(client.getId());
        dto.setEmail(client.getEmail());
        dto.setFirstName(client.getFirstName());
        dto.setLastName(client.getLastName());
        dto.setUsername(client.getUsername());
        dto.setMembershipNumber(client.getMembershipNumber());
        return dto;
    }
}
