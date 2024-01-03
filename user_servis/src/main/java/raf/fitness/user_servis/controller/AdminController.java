package raf.fitness.user_servis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import raf.fitness.user_servis.security.CheckSecurity;
import raf.fitness.user_servis.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.fitness.user_servis.dto.admin.*;
import raf.fitness.user_servis.dto.token.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "Update an admin by ID")
    @PutMapping("/{id}")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<AdminResponseDto> update(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id, @RequestBody @Valid AdminRequestDto adminRequestDto) {
        return new ResponseEntity<>(adminService.update(id, adminRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Get admin by id")
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(adminService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Login as an admin")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(adminService.login(tokenRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Logout as an admin")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam Long id) {
        adminService.logout(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Forbid access for a user with a specific role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "forbiddenId", value = "ID of the user to forbid", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "forbiddenRole", value = "Role to forbid", required = true, dataType = "String", paramType = "query")
    })
    @PutMapping("/forbid")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<?> forbid(@RequestHeader("Authorization") String authorization, @RequestParam Long forbiddenId, @RequestParam String forbiddenRole) {
        adminService.forbid(forbiddenId, forbiddenRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Remove forbidden access for a user with a specific role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "forbiddenId", value = "ID of the user to unforbid", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "forbiddenRole", value = "Role to unforbid", required = true, dataType = "String", paramType = "query")
    })
    @PutMapping("/unforbid")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<?> unforbid(@RequestHeader("Authorization") String authorization, @RequestParam Long forbiddenId, @RequestParam String forbiddenRole) {
        // Service returns void
        adminService.unforbid(forbiddenId, forbiddenRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

