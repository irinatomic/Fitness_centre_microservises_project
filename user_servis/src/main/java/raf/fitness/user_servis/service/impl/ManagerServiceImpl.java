package raf.fitness.user_servis.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import raf.fitness.user_servis.domain.Manager;
import raf.fitness.user_servis.dto.manager.*;
import raf.fitness.user_servis.dto.token.*;
import raf.fitness.user_servis.exception.NotFoundException;
import raf.fitness.user_servis.mapper.ManagerMapper;
import raf.fitness.user_servis.repository.ManagerRepository;
import raf.fitness.user_servis.security.service.TokenService;
import raf.fitness.user_servis.service.ManagerService;

import javax.transaction.Transactional;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private TokenService tokenService;
    private ManagerRepository managerRepository;
    private ManagerMapper managerMapper;

    public ManagerServiceImpl(TokenService tokenService, ManagerRepository managerRepository, ManagerMapper managerMapper) {
        this.tokenService = tokenService;
        this.managerRepository = managerRepository;
        this.managerMapper = managerMapper;
    }

    @Override
    public ManagerResponseDto add(ManagerRequestDto managerRequestDto) {
        Manager manager = managerMapper.managerCreateRequestDtoToManager(managerRequestDto);
        managerRepository.save(manager);
        return managerMapper.managerToManagerResponseDto(manager);
    }

    @Override
    public ManagerResponseDto activate(Long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Manager with id: %d not found.", id)));
        manager.setActivated(true);
        return managerMapper.managerToManagerResponseDto(manager);
    }

    @Override
    public ManagerResponseDto update(Long id, ManagerRequestDto managerRequestDto) {
        Manager manager = managerRepository.findByIdAndLoggedin(id, true).orElseThrow(() -> new NotFoundException(String.format("Manager with id: %d not found.", id)));
        if(manager.getActivated()) {
            manager.setEmail(managerRequestDto.getEmail());
            manager.setFirstName(managerRequestDto.getFirstName());
            manager.setLastName(managerRequestDto.getLastName());
            manager.setUsername(managerRequestDto.getUsername());
            manager.setPassword(managerRequestDto.getPassword());
            manager.setPhoneNumber(managerRequestDto.getPhoneNumber());
        }
        return managerMapper.managerToManagerResponseDto(manager);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        String username = tokenRequestDto.getUsername();
        Manager manager = managerRepository.findByUsernameAndActivatedAndForbidden(username, true, false).
                orElseThrow(() -> new NotFoundException(String.format("Manager with username: %s not found.", username)));

        // Create token payload
        manager.setLoggedin(true);
        Claims claims = Jwts.claims();
        claims.put("id", manager.getId());
        claims.put("role", manager.getRole().getName());

        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public void logout(Long id) {
        Manager manager = managerRepository.findByIdAndLoggedin(id, true).
                orElseThrow(() -> new NotFoundException(String.format("Client with id: %s not found.", id)));
        manager.setLoggedin(false);
    }

    @Override
    public void delete(Long id) {
        managerRepository.deleteById(id);
    }

    @Override
    public void giveFreeTraining(Long id) {
        // todo - komunikacija sa 3. servisom
    }
}
