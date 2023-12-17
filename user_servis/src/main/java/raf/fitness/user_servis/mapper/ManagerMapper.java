package raf.fitness.user_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.user_servis.domain.Manager;
import raf.fitness.user_servis.dto.manager.ManagerRequestDto;
import raf.fitness.user_servis.dto.manager.ManagerResponseDto;
import raf.fitness.user_servis.repository.RoleRepository;

@Component
public class ManagerMapper {

    private RoleRepository roleRepository;

    public ManagerMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Manager managerRequestDtoToManager(ManagerRequestDto dto) {
        Manager manager = new Manager();
        manager.setEmail(dto.getEmail());
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setUsername(dto.getUsername());
        manager.setPassword(dto.getPassword());
        manager.setDateOfEmployment(dto.getDateOfEmployment());
        manager.setRole(roleRepository.findByName("MANAGER").orElse(null));
        return manager;
    }

    public ManagerResponseDto managerToManagerResponseDto(Manager manager) {
        ManagerResponseDto dto = new ManagerResponseDto();
        dto.setId(manager.getId());
        dto.setEmail(manager.getEmail());
        dto.setFirstName(manager.getFirstName());
        dto.setLastName(manager.getLastName());
        dto.setUsername(manager.getUsername());
        return dto;
    }
}
