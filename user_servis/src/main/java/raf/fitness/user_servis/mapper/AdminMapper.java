package raf.fitness.user_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.user_servis.domain.Admin;
import raf.fitness.user_servis.dto.admin.*;
import raf.fitness.user_servis.repository.RoleRepository;

@Component
public class AdminMapper {

    private RoleRepository roleRepository;

    public AdminMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Admin adminRequestDtoToAdmin(AdminRequestDto dto) {
        Admin admin = new Admin();
        admin.setEmail(dto.getEmail());
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setUsername(dto.getUsername());
        admin.setPassword(dto.getPassword());
        admin.setPhoneNumber(dto.getPhoneNumber());
        admin.setLoggedin(false);
        admin.setRole(roleRepository.findByName("ADMIN").orElse(null));
        return admin;
    }

    public AdminResponseDto adminToAdminResponseDto(Admin admin) {
        AdminResponseDto dto = new AdminResponseDto();
        dto.setId(admin.getId());
        dto.setEmail(admin.getEmail());
        dto.setFirstName(admin.getFirstName());
        dto.setLastName(admin.getLastName());
        dto.setUsername(admin.getUsername());
        return dto;
    }
}
