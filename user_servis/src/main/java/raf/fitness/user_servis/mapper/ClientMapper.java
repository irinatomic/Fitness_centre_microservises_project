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

    public Client clientRequestDtoToClient(ClientRequestDto dto) {
        Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setUsername(dto.getUsername());
        client.setPassword(dto.getPassword());
        // do not know the membershipNumber and trainingsBookedNo
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
