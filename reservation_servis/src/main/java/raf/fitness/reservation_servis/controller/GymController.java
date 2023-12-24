package raf.fitness.reservation_servis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.fitness.reservation_servis.aspects.security.CheckSecurity;
import raf.fitness.reservation_servis.dto.gym.*;
import raf.fitness.reservation_servis.service.GymService;

@RestController
@RequestMapping("/gyms")
public class GymController {

    private final GymService gymService;

    public GymController(GymService gymService) {
        this.gymService = gymService;
    }

    @ApiOperation(value = "Find all gyms")
    @GetMapping
    public ResponseEntity<Page<GymResponseDto>> findAll(Pageable pageable) {
        Page<GymResponseDto> gyms = gymService.findAll(pageable);
        return new ResponseEntity<>(gyms, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a gym")
    @PostMapping
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<GymResponseDto> create(@RequestHeader("Authorization") String authorization, @RequestBody GymRequestDto gymRequestDto) {
        GymResponseDto createdGym = gymService.create(gymRequestDto);
        return new ResponseEntity<>(createdGym, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a gym")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/{id}")
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<GymResponseDto> update(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @RequestBody GymRequestDto gymRequestDto) {
        // get id from authorization string
        Long managerId = AuthorizationHelper.extractIdFromToken(authorization);
        GymResponseDto updatedGym = gymService.update(id, managerId, gymRequestDto);
        return new ResponseEntity<>(updatedGym, HttpStatus.OK);
    }

    @ApiOperation(value = "Change free session number for a gym")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @PatchMapping("/{id}/change-free-session-no")
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<GymResponseDto> changeFreeSessionNo(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @RequestParam Integer freeSessionNo) {
        // get id from authorization string
        Long managerId = AuthorizationHelper.extractIdFromToken(authorization);
        GymResponseDto changedGym = gymService.changeFreeSessionNo(id, managerId, freeSessionNo);
        return new ResponseEntity<>(changedGym, HttpStatus.OK);
    }

}

