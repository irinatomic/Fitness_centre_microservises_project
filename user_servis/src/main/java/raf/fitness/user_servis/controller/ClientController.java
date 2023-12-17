package raf.fitness.user_servis.controller;

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

    @PostMapping
    public ResponseEntity<ClientResponseDto> add(@RequestBody @Valid ClientRequestDto clientRequestDto){
        return new ResponseEntity<>(clientService.add(clientRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/activate")
    public ResponseEntity<ClientResponseDto> activate(@RequestParam Long id){
        return new ResponseEntity<>(clientService.activate(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> update(@PathVariable("id") Long id, @RequestBody @Valid ClientRequestDto clientRequestDto){
        return new ResponseEntity<>(clientService.update(id, clientRequestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        return new ResponseEntity<>(clientService.login(tokenRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
