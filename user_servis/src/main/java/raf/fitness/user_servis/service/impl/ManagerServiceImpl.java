package raf.fitness.user_servis.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import raf.fitness.user_servis.async_comm.email.EmailSenderService;
import raf.fitness.user_servis.async_comm.email.EmailType;
import raf.fitness.user_servis.domain.Manager;
import raf.fitness.user_servis.dto.manager.*;
import raf.fitness.user_servis.dto.token.*;
import raf.fitness.user_servis.exception.NotFoundException;
import raf.fitness.user_servis.mapper.ManagerMapper;
import raf.fitness.user_servis.repository.ManagerRepository;
import raf.fitness.user_servis.security.service.TokenService;
import raf.fitness.user_servis.service.ManagerService;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private TokenService tokenService;
    private ManagerRepository managerRepository;
    private ManagerMapper managerMapper;

    // async communication
    private EmailSenderService emailSenderService;

    public ManagerServiceImpl(TokenService tokenService, ManagerRepository managerRepository, ManagerMapper managerMapper, EmailSenderService emailSenderService) {
        this.tokenService = tokenService;
        this.managerRepository = managerRepository;
        this.managerMapper = managerMapper;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public ManagerResponseDto findById(Long id) {
        return managerMapper.managerToManagerResponseDto(managerRepository.findById(id).get());
    }

    @Override
    public ManagerResponseDto add(ManagerRequestDto managerRequestDto) {
        Manager manager = managerMapper.managerCreateRequestDtoToManager(managerRequestDto);
        managerRepository.save(manager);

        // == Service 3 -> activation mail ==
        Map<String, String> params = new HashMap<>();
        params.put("firstName", manager.getFirstName());
        params.put("lastName", manager.getLastName());
        params.put("link", "http://localhost:8080/manager/activate?role=manager&id=" + manager.getId());

        emailSenderService.sendMessageToQueue(EmailType.ACTIVATION, manager.getEmail(), params);

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
        claims.put("email", manager.getEmail());

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
    public List<String> getForbiddenClients() {
        List<Manager> managers = managerRepository.findAllByForbidden(true);
        return managers.stream().map(Manager::getUsername).collect(java.util.stream.Collectors.toList());
    }

}
