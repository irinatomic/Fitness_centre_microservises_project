package raf.fitness.notif_servis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.fitness.notif_servis.dto.mail_type.MailTypeRequestDto;
import raf.fitness.notif_servis.dto.mail_type.MailTypeResponseDto;
import raf.fitness.notif_servis.security.CheckSecurity;
import raf.fitness.notif_servis.service.MailTypeService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/mail-type")
public class MailTypeController {

    private final MailTypeService mailTypeService;

    public MailTypeController(MailTypeService mailTypeService) {
        this.mailTypeService = mailTypeService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ADMIN"})
    @ApiOperation(value = "Get all mail types", notes = "Get all mail types with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Page number"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sort criteria in the format: property(,asc|desc)")
    })
    public ResponseEntity<Page<MailTypeResponseDto>> findAll(@RequestHeader("Authorization") String authorization, @ApiIgnore Pageable pageable) {
        return ResponseEntity.ok(mailTypeService.findAll(pageable));
    }

    @PostMapping
    @CheckSecurity(roles = {"ADMIN"})
    @ApiOperation(value = "Add a new mail type", notes = "Add a new mail type")
    public ResponseEntity<MailTypeResponseDto> add(@RequestHeader("Authorization") String authorization, @RequestBody @Valid MailTypeRequestDto mailTypeRequestDto) {
        return ResponseEntity.ok(mailTypeService.add(mailTypeRequestDto));
    }

    @PutMapping("/{id}")
    @CheckSecurity(roles = {"ADMIN"})
    @ApiOperation(value = "Update a mail type", notes = "Update an existing mail type")
    public ResponseEntity<MailTypeResponseDto> update(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id, @RequestBody @Valid MailTypeRequestDto mailTypeRequestDto) {
        return ResponseEntity.ok(mailTypeService.update(id, mailTypeRequestDto));
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ADMIN"})
    @ApiOperation(value = "Delete a mail type", notes = "Delete an existing mail type by ID")
    @ApiImplicitParam(name = "id", dataType = "long", paramType = "path", value = "Mail Type ID")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        mailTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}

