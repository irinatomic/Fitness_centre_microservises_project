package raf.fitness.user_servis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.fitness.user_servis.dto.manager.*;
import raf.fitness.user_servis.dto.token.*;
import raf.fitness.user_servis.security.CheckSecurity;
import raf.fitness.user_servis.service.ManagerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @ApiOperation(value = "Add a new manager", notes = "Creates a new manager.")
    @PostMapping("/register")
    public ResponseEntity<ManagerResponseDto> add(@RequestBody @Valid ManagerRequestDto managerRequestDto){
        return new ResponseEntity<>(managerService.add(managerRequestDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get manager by id")
    @GetMapping("/{id}")
    public ResponseEntity<ManagerResponseDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(managerService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Activate manager by ID", notes = "Activates a manager with the provided ID.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of the manager to activate", required = true, dataType = "Long", paramType = "query")
    })
    @PutMapping("/activate")
    public ResponseEntity<ManagerResponseDto> activate(@RequestParam Long id){
        return new ResponseEntity<>(managerService.activate(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Update manager by ID", notes = "Updates manager details based on provided ID.")
    @PutMapping("/{id}")
    @CheckSecurity(roles = {"MANAGER"})
    public ResponseEntity<ManagerResponseDto> update(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id, @RequestBody @Valid ManagerRequestDto managerRequestDto){
        return new ResponseEntity<>(managerService.update(id, managerRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Manager login", notes = "Authenticates and logs in as a manager.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        return new ResponseEntity<>(managerService.login(tokenRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Logout as an manager")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam Long id) {
        managerService.logout(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete manager by ID", notes = "Deletes a manager with the provided ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        managerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Get forbidden clients", notes = "Get forbidden clients.")
    @GetMapping("/forbidden")
    public ResponseEntity<List<String>> getForbiddenClients() {
        return new ResponseEntity<>(managerService.getForbiddenClients(), HttpStatus.OK);
    }
}

