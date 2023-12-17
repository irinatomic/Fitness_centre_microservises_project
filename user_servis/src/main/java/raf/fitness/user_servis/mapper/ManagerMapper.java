package raf.fitness.user_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.user_servis.domain.Manager;
import raf.fitness.user_servis.dto.manager.ManagerRequestDto;
import raf.fitness.user_servis.dto.manager.ManagerResponseDto;
import raf.fitness.user_servis.repository.RoleRepository;

import java.time.LocalDate;

@Component
public class ManagerMapper {

    private RoleRepository roleRepository;

    public ManagerMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // WHEN CREATING A MANAGER
    public Manager managerCreateRequestDtoToManager(ManagerRequestDto dto) {
        Manager manager = new Manager();
        manager.setEmail(dto.getEmail());
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setUsername(dto.getUsername());
        manager.setPassword(dto.getPassword());
        manager.setPhoneNumber(dto.getPhoneNumber());
        manager.setDateOfEmployment(LocalDate.now());
        manager.setActivated(false);
        manager.setDeleted(false);
        manager.setForbidden(false);
        manager.setLoggedin(false);
        manager.setRole(roleRepository.findByName("MANAGER").orElse(null));
        return manager;
    }

    public Manager managerRequestDtoToManager(ManagerRequestDto dto) {
        Manager manager = new Manager();
        manager.setEmail(dto.getEmail());
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setUsername(dto.getUsername());
        manager.setPassword(dto.getPassword());
        manager.setPhoneNumber(dto.getPhoneNumber());
        // CANNOT CHANGE DATE OF EMPLOYMENT
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
