package raf.fitness.reservation_servis.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.fitness.reservation_servis.dto.TrainingTypeDto;
import raf.fitness.reservation_servis.service.TrainingTypeService;

import java.util.List;

@RestController
@RequestMapping("/training-types")
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @ApiOperation(value = "Get all training types")
    @GetMapping
    public ResponseEntity<List<TrainingTypeDto>> findAllTrainingTypes() {
        List<TrainingTypeDto> trainingTypes = trainingTypeService.findAll();
        return new ResponseEntity<>(trainingTypes, HttpStatus.OK);
    }
}


