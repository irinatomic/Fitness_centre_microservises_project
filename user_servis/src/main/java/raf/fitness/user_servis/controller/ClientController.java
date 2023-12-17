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
import raf.fitness.user_servis.service.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Add a new client", notes = "Creates a new client.")
    @PostMapping
    public ResponseEntity<ClientResponseDto> add(@RequestBody @Valid ClientRequestDto clientRequestDto){
        return new ResponseEntity<>(clientService.add(clientRequestDto), HttpStatus.CREATED);
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
    public ResponseEntity<ClientResponseDto> update(@PathVariable("id") Long id, @RequestBody @Valid ClientRequestDto clientRequestDto){
        return new ResponseEntity<>(clientService.update(id, clientRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Client login", notes = "Authenticates and logs in as a client.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        return new ResponseEntity<>(clientService.login(tokenRequestDto), HttpStatus.OK);
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
}

