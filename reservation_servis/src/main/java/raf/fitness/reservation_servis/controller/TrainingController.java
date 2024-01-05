package raf.fitness.reservation_servis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.fitness.reservation_servis.aspects.security.CheckSecurity;
import raf.fitness.reservation_servis.dto.training.*;
import raf.fitness.reservation_servis.service.TrainingService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }
    
    @ApiOperation(value = "Get all trainings for a gym and a training type")
    @GetMapping("/gym")
    public ResponseEntity<List<TrainingResponseDto>> findAll(@RequestParam(required = false) Long gymdId, @RequestParam(required = false) Long trainingTypeId) {
        List<TrainingResponseDto> trainings = new ArrayList<>();
        if (gymdId != null && trainingTypeId != null) {
            trainings = trainingService.findAllForGymAndTrainingType(gymdId, trainingTypeId);
        } else if (gymdId != null) {
            trainings = trainingService.findAllForGym(gymdId);
        }

        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @ApiOperation(value = "Add a training to a gym")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<TrainingResponseDto> addTraining(@RequestHeader("Authorization") String authorization, @RequestBody TrainingRequestDto requestDto) {
        Long managerId = AuthorizationHelper.extractIdFromToken(authorization);
        TrainingResponseDto addedTraining = trainingService.add(managerId, requestDto);
        return new ResponseEntity<>(addedTraining, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a training for a gym")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/{id}")
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<TrainingResponseDto> updateTraining(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @RequestBody TrainingRequestDto requestDto) {
        Long managerId = AuthorizationHelper.extractIdFromToken(authorization);
        TrainingResponseDto updatedTraining = trainingService.update(id, managerId, requestDto);
        return new ResponseEntity<>(updatedTraining, HttpStatus.OK);
    }
}


