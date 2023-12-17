package raf.fitness.user_servis.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import raf.fitness.user_servis.domain.Manager;
import raf.fitness.user_servis.dto.manager.ManagerRequestDto;
import raf.fitness.user_servis.dto.manager.ManagerResponseDto;
import raf.fitness.user_servis.dto.token.TokenRequestDto;
import raf.fitness.user_servis.dto.token.TokenResponseDto;
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
        Manager manager = managerMapper.managerRequestDtoToManager(managerRequestDto);
        managerRepository.save(manager);
        return managerMapper.managerToManagerResponseDto(manager);
    }

    @Override
    @SneakyThrows
    public ManagerResponseDto activate(Long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Manager with id: %d not found.", id)));
        manager.setActivated(true);
        return managerMapper.managerToManagerResponseDto(manager);
    }

    @Override
    @SneakyThrows
    public ManagerResponseDto update(Long id, ManagerRequestDto managerRequestDto) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Manager with id: %d not found.", id)));
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
    @SneakyThrows
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        Manager manager = managerRepository.findByUsername(tokenRequestDto.getUsername()).
                orElseThrow(() -> new NotFoundException(String.format("Manager with username: %s not found.", tokenRequestDto.getUsername())));

        // Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", manager.getId());
        claims.put("role", manager.getRole().getName());

        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Manager with id: %d not found.", id)));
        manager.setDeleted(true);
    }

    @Override
    public void giveFreeTraining(Long id) {
        // todo - komunikacija sa 3. servisom
    }
}
