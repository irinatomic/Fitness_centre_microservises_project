package raf.fitness.user_servis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.fitness.user_servis.dto.manager.ManagerRequestDto;
import raf.fitness.user_servis.dto.manager.ManagerResponseDto;
import raf.fitness.user_servis.dto.token.TokenRequestDto;
import raf.fitness.user_servis.dto.token.TokenResponseDto;
import raf.fitness.user_servis.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    ManagerResponseDto add(ManagerRequestDto managerRequestDto){

    }

    ManagerResponseDto activate(Long id){

    }

    ManagerResponseDto update(Long id, ManagerRequestDto managerRequestDto){

    }

    TokenResponseDto login(TokenRequestDto tokenRequestDto){

    }

    void delete(Long id){

    }

    void giveFreeTraining(Long id, ManagerRequestDto managerRequestDto) {

    }

}
