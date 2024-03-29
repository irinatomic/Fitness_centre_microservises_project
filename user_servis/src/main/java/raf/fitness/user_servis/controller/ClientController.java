package raf.fitness.user_servis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.fitness.user_servis.dto.client.*;
import raf.fitness.user_servis.dto.token.*;
import raf.fitness.user_servis.security.CheckSecurity;
import raf.fitness.user_servis.service.ClientService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Add a new client", notes = "Creates a new client.")
    @PostMapping("/register")
    public ResponseEntity<ClientResponseDto> add(@RequestBody @Valid ClientRequestDto clientRequestDto){
        return new ResponseEntity<>(clientService.add(clientRequestDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get client by id")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Activate client by ID", notes = "Activates a client with the provided ID.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of the client to activate", required = true, dataType = "long", paramType = "query")
    })
    @PutMapping("/activate")
    public ResponseEntity<ClientResponseDto> activate(@RequestParam Long id){
        return new ResponseEntity<>(clientService.activate(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Update client by ID", notes = "Updates client details based on provided ID.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of the client to update", required = true, dataType = "long", paramType = "path")
    })
    @PutMapping("/{id}")
    @CheckSecurity(roles = {"CLIENT"})
    public ResponseEntity<ClientResponseDto> update(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id, @RequestBody @Valid ClientRequestDto clientRequestDto){
        return new ResponseEntity<>(clientService.update(id, clientRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Client login", notes = "Authenticates and logs in as a client.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        return new ResponseEntity<>(clientService.login(tokenRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Logout as an client")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam Long id) {
        clientService.logout(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete client by ID", notes = "Deletes a client with the provided ID.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of the client to delete", required = true, dataType = "long", paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Get Clients booked number", notes = "Get Clients booked number with the provided ID.")
    @GetMapping("/booked-no")
    public ResponseEntity<Integer> getClientsBookedNo(@RequestParam Long id){
        return new ResponseEntity<>(clientService.getClientsBookedNo(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get forbidden clients", notes = "Get forbidden clients.")
    @GetMapping("/forbidden")
    public ResponseEntity<List<String>> getForbiddenClients() {
        return new ResponseEntity<>(clientService.getForbiddenClients(), HttpStatus.OK);
    }
}
