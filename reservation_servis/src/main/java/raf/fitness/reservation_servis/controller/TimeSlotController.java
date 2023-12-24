package raf.fitness.reservation_servis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.fitness.reservation_servis.aspects.security.CheckSecurity;
import raf.fitness.reservation_servis.dto.TimeSlotDto;
import raf.fitness.reservation_servis.service.TimeSlotService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @ApiOperation(value = "Create time slots for a gym")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/create/{gymId}")
    @CheckSecurity(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<Void> createTimeSlotsForGym(@RequestHeader("Authorization") String authorization, @PathVariable Long gymId) {
        timeSlotService.createTimeSlotsForGym(gymId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Find free time slots for a gym")
    @GetMapping("/free/{gymId}")
    public ResponseEntity<List<TimeSlotDto>> findFreeTimeSlots(@PathVariable Long gymId, @RequestParam LocalDate date) {
        List<TimeSlotDto> freeTimeSlots = timeSlotService.findFreeTimeSlots(gymId, date);
        return new ResponseEntity<>(freeTimeSlots, HttpStatus.OK);
    }
}

