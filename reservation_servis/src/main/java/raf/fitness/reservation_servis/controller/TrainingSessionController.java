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
import raf.fitness.reservation_servis.dto.SignedUpDto;
import raf.fitness.reservation_servis.dto.training_session.*;
import raf.fitness.reservation_servis.service.TrainingSessionService;

import java.time.LocalDate;
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
    @PostMapping("/{sessionId}/sign-up")
    @CheckSecurity(roles = {"CLIENT"})
    public ResponseEntity<Void> signUpForSession(@RequestHeader("Authorization") String authorization, @PathVariable Long sessionId, @RequestBody SignedUpDto user) {
        trainingSessionService.signUp(sessionId, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Cancel session as a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{sessionId}/cancel-as-user/{userId}")
    @CheckSecurity(roles = {"CLIENT"})
    public ResponseEntity<Void> cancelAsUser(@RequestHeader("Authorization") String authorization, @PathVariable Long sessionId, @PathVariable Long userId) {
        trainingSessionService.cancelAsUser(sessionId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Cancel session as a manager")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{sessionId}/cancel-as-manager")
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<Void> cancelAsManager(@RequestHeader("Authorization") String authorization, @PathVariable Long sessionId) {
        Long managerId = AuthorizationHelper.extractIdFromToken(authorization);
        trainingSessionService.cancelAsManager(managerId, sessionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Get all sessions for a gym")
    @GetMapping("/gym/{gymId}")
    public ResponseEntity<Page<TrainingSessionResponseDto>> getAllForGym(@PathVariable Long gymId, Pageable pageable) {
        Page<TrainingSessionResponseDto> sessions = trainingSessionService.getAllForGym(gymId, pageable);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all sessions for a gym and a date")
    @GetMapping("/gym/{gymId}/date/{date}")
    public ResponseEntity<List<TrainingSessionResponseDto>> getAllForGymAndDate(@PathVariable Long gymId, @PathVariable LocalDate date) {
        List<TrainingSessionResponseDto> sessions = trainingSessionService.getAllForGymAndDate(gymId, date);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all sessions for a gym and a training type")
    @GetMapping("/gym/{gymId}/type/{trainingType}")
    public ResponseEntity<List<TrainingSessionResponseDto>> getAllForGymAndTrainingType(@PathVariable Long gymId, @PathVariable String trainingType) {
        List<TrainingSessionResponseDto> sessions = trainingSessionService.getAllForGymAndTrainingType(gymId, trainingType);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all sessions for a gym, date, and training type")
    @GetMapping("/gym/{gymId}/date/{date}/type/{trainingType}")
    public ResponseEntity<List<TrainingSessionResponseDto>> getAllForGymAndDateAndTrainingType(@PathVariable Long gymId, @PathVariable LocalDate date, @PathVariable String trainingType) {
        List<TrainingSessionResponseDto> sessions = trainingSessionService.getAllForGymAndDateAndTrainingType(gymId, date, trainingType);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }
}


