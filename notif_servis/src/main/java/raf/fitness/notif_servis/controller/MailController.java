package raf.fitness.notif_servis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.fitness.notif_servis.dto.mail.MailResponseDto;
import raf.fitness.notif_servis.security.CheckSecurity;
import raf.fitness.notif_servis.service.MailService;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @ApiOperation(value = "Retrieve emails (admin access)", notes = "Retrieves emails based on provided parameters for admin roles.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, paramType = "header", dataType = "string"),
            @ApiImplicitParam(name = "timestampFrom", value = "Start timestamp", dataType = "java.time.LocalDate", paramType = "query"),
            @ApiImplicitParam(name = "timestampTo", value = "End timestamp", dataType = "java.time.LocalDate", paramType = "query"),
            @ApiImplicitParam(name = "mailType", value = "Mail type", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "Page number", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Page size", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Sort field", dataType = "string", paramType = "query")
    })
    @GetMapping("/all")
    @CheckSecurity(roles = {"ADMIN"})
    public ResponseEntity<Page<MailResponseDto>> findEmailsForAdmin(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false) String timestampFrom,
            @RequestParam(required = false) String timestampTo,
            @RequestParam(required = false) String mailType,
            @ApiIgnore Pageable pageable) {

        LocalDate timestampFromLD = null;
        LocalDate timestampToLD = null;

        if (timestampFrom != null && !timestampFrom.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            timestampFromLD = LocalDate.parse(timestampFrom, formatter);
            timestampToLD = LocalDate.parse(timestampTo, formatter);
        }

        if (timestampFrom != null && timestampTo != null && mailType != null) {
            return ResponseEntity.ok(mailService.findAllByMailTypeAndTimestampBetween(mailType, timestampFromLD, timestampToLD, pageable));
        } else if (mailType == null && timestampFrom != null && timestampTo != null) {
            return ResponseEntity.ok(mailService.findAllByTimestampBetween(timestampFromLD, timestampToLD, pageable));
        } else if (mailType != null && timestampFrom == null && timestampTo == null) {
            return ResponseEntity.ok(mailService.findAllByMailType(mailType, pageable));
        } else {
            return ResponseEntity.ok(mailService.findAll(pageable));
        }
    }

    @ApiOperation(value = "Retrieve specific emails (client/manager access)", notes = "Retrieves specific emails based on provided parameters for client/manager roles.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, paramType = "header", dataType = "string"),
            @ApiImplicitParam(name = "timestampFrom", value = "Start timestamp", dataType = "java.time.LocalDate", paramType = "query"),
            @ApiImplicitParam(name = "timestampTo", value = "End timestamp", dataType = "java.time.LocalDate", paramType = "query"),
            @ApiImplicitParam(name = "mailType", value = "Mail type", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sentTo", value = "Recipient email", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "Page number", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Page size", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Sort field", dataType = "string", paramType = "query")
    })
    @GetMapping("/specific")
    @CheckSecurity(roles = {"CLIENT", "MANAGER"})
    public ResponseEntity<Page<MailResponseDto>> findSpecificEmailsForClientOrManager(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false) String timestampFrom,
            @RequestParam(required = false) String timestampTo,
            @RequestParam(required = false) String mailType,
            @RequestParam String sentTo,
            @ApiIgnore Pageable pageable) {

        LocalDate timestampFromLD = null;
        LocalDate timestampToLD = null;

        if (timestampFrom != null && !timestampFrom.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            timestampFromLD = LocalDate.parse(timestampFrom, formatter);
            timestampToLD = LocalDate.parse(timestampTo, formatter);
        }

        if (timestampFrom != null && timestampTo != null && mailType != null && sentTo != null) {
            return ResponseEntity.ok(mailService.findAllBySentToAndMailTypeAndTimestampBetween(sentTo, mailType, timestampFromLD, timestampToLD, pageable));
        } else if (timestampFrom != null && timestampTo != null && sentTo != null) {
            return ResponseEntity.ok(mailService.findAllBySentToAndTimestampBetween(sentTo, timestampFromLD, timestampToLD, pageable));
        } else if (mailType != null && sentTo != null) {
            return ResponseEntity.ok(mailService.findAllBySentToAndMailType(sentTo, mailType, pageable));
        } else {
            return ResponseEntity.ok(mailService.findAllBySentTo(sentTo, pageable));
        }
    }
}