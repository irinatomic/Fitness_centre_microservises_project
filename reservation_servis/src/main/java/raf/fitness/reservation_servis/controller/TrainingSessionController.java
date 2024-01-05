package raf.fitness.reservation_servis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.fitness.reservation_servis.aspects.security.CheckSecurity;
import raf.fitness.reservation_servis.dto.SignedUpDto;
import raf.fitness.reservation_servis.dto.training_session.*;
import raf.fitness.reservation_servis.service.TrainingSessionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/training-sessions")
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    public TrainingSessionController(TrainingSessionService trainingSessionService) {
        this.trainingSessionService = trainingSessionService;
    }

    @ApiOperation(value = "Create a session")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping
    @CheckSecurity(roles = {"CLIENT"})
    public ResponseEntity<TrainingSessionResponseDto> createSession(@RequestHeader("Authorization") String authorization, @RequestBody TrainingSessionRequestDto trainingSessionRequestDto, @RequestBody SignedUpDto creator) {
        TrainingSessionResponseDto createdSession = trainingSessionService.create(trainingSessionRequestDto, creator);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Sign up for a session")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/sign-up")
    @CheckSecurity(roles = {"CLIENT"})
    public ResponseEntity<Void> signUpForSession(@RequestHeader("Authorization") String authorization, @RequestParam String sessionId, @RequestBody SignedUpDto user) {
        Long sessionIdL = Long.parseLong(sessionId);

        trainingSessionService.signUp(sessionIdL, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Cancel session as a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/cancel-as-user")
    @CheckSecurity(roles = {"CLIENT"})
    public ResponseEntity<Void> cancelAsUser(@RequestHeader("Authorization") String authorization, @RequestParam String sessionId, @RequestParam String userId) {
        Long sessionIdL = Long.parseLong(sessionId);
        Long userIdL = Long.parseLong(userId);

        trainingSessionService.cancelAsUser(sessionIdL, userIdL);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Cancel session as a manager")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("cancel-as-manager")
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<Void> cancelAsManager(@RequestHeader("Authorization") String authorization, @RequestParam String sessionId, @RequestParam String managerId) {
        Long sessionIdL = Long.parseLong(sessionId);
        Long managerIdL = Long.parseLong(managerId);

        trainingSessionService.cancelAsManager(managerIdL, sessionIdL);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Find sessions with filtering")
    @GetMapping("/filter")
    public ResponseEntity<List<TrainingSessionResponseDto>> findAllFiltered(
            @RequestParam(required = true) String gymId,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Long trainingTypeId){

        Long gymdIdL = Long.parseLong(gymId);

        LocalDate dateLD = null;
        if(date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dateLD = LocalDate.parse(date, formatter);
        }

        List<TrainingSessionResponseDto> sessions = new ArrayList<>();
        if(date != null && trainingTypeId == null)
            sessions = trainingSessionService.getAllForGymAndDate(gymdIdL, dateLD);
        else if(date == null && trainingTypeId != null)
            sessions = trainingSessionService.getAllForGymAndTrainingType(gymdIdL, trainingTypeId);
        else if(date != null && trainingTypeId != null)
            sessions = trainingSessionService.getAllForGymAndDateAndTrainingType(gymdIdL, dateLD, trainingTypeId);
        else
            sessions = trainingSessionService.getAllForGym(gymdIdL);

        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }
}


