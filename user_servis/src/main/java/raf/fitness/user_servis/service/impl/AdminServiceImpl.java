package raf.fitness.user_servis.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import raf.fitness.user_servis.domain.*;
import raf.fitness.user_servis.dto.admin.*;
import raf.fitness.user_servis.dto.token.*;
import raf.fitness.user_servis.repository.*;
import raf.fitness.user_servis.mapper.AdminMapper;
import raf.fitness.user_servis.security.service.TokenService;
import raf.fitness.user_servis.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private TokenService tokenService;
    private AdminRepository adminRepository;
    private ClientRepository clientRepository;
    private ManagerRepository managerRepository;
    private AdminMapper adminMapper;

    public AdminServiceImpl(TokenService tokenService, AdminRepository adminRepository, ClientRepository clientRepository, ManagerRepository managerRepository, AdminMapper adminMapper) {
        this.tokenService = tokenService;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    @SneakyThrows
    public AdminResponseDto update(Long id, AdminRequestDto adminRequestDto) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Product with id: %d not found.", id)));
        if(admin.getActivated()) {
            admin.setEmail(adminRequestDto.getEmail());
            admin.setFirstName(adminRequestDto.getFirstName());
            admin.setLastName(adminRequestDto.getLastName());
            admin.setUsername(adminRequestDto.getUsername());
            admin.setPassword(adminRequestDto.getPassword());
            admin.setPhoneNumber(adminRequestDto.getPhoneNumber());
            adminRepository.save(admin);
        }
        return adminMapper.adminToAdminResponseDto(admin);
    }

    @Override
    @SneakyThrows
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        // Try to find admin for specified credentials (all are active)
        Admin admin = adminRepository.findByUsername(tokenRequestDto.getUsername()).orElseThrow(() -> new NotFoundException("Admin not found."));

        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", admin.getId());
        claims.put("role", admin.getRole().getName());

        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    @SneakyThrows
    public void forbid(TokenRequestDto tokenRequestDto, Long forbiddenId, String forbiddenRole) {
        // Try to find admin for specified credentials (all are active)
        Admin admin = adminRepository.findByUsername(tokenRequestDto.getUsername()).orElseThrow(() -> new NotFoundException("Admin not found."));

        if(forbiddenRole.equalsIgnoreCase("client")) {
            Client toForbid = clientRepository.findById(forbiddenId).orElseThrow(() -> new NotFoundException("User not found."));
            toForbid.setForbidden(true);
        }

        else if(forbiddenRole.equalsIgnoreCase("manager")) {
            Manager toForbid = managerRepository.findById(forbiddenId).orElseThrow(() -> new NotFoundException("User not found."));
            toForbid.setForbidden(true);
        }

        else throw new NotFoundException("User not found.");
    }

    @Override
    @SneakyThrows
    public void unforbid(TokenRequestDto tokenRequestDto, Long forbiddenId, String forbiddenRole) {
        // Try to find admin for specified credentials (all are active)
        Admin admin = adminRepository.findByUsername(tokenRequestDto.getUsername()).orElseThrow(() -> new NotFoundException("Admin not found."));

        if(forbiddenRole.equalsIgnoreCase("client")) {
            Client toForbid = clientRepository.findById(forbiddenId).orElseThrow(() -> new NotFoundException("User not found."));
            toForbid.setForbidden(false);
        }

        else if(forbiddenRole.equalsIgnoreCase("manager")) {
            Manager toForbid = managerRepository.findById(forbiddenId).orElseThrow(() -> new NotFoundException("User not found."));
            toForbid.setForbidden(false);
        }

        else throw new NotFoundException("User not found.");
    }
}
