package com.project.ccts.api;

import com.project.ccts.dto.CustomResponseObjectDTO;
import com.project.ccts.dto.HealthStatusMobileAddDTO;
import com.project.ccts.dto.PersonExpositionStatusDTO;
import com.project.ccts.service.CredentialService;
import com.project.ccts.service.HealthStatusService;
import com.project.ccts.service.VisitService;
import com.project.ccts.util.exception.CustomApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.project.ccts.dto.CustomResponseObjectUtil.createResponse;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    private CredentialService credentialService;
    private VisitService visitService;
    private HealthStatusService healthStatusService;

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Autowired
    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    @Autowired
    public void setHealthStatusService(HealthStatusService healthStatusService) {
        this.healthStatusService = healthStatusService;
    }

    @PutMapping("/signout")
    @PreAuthorize("hasAnyAuthority('USER_WRITE_PRIVILEGE')")
    public ResponseEntity<CustomResponseObjectDTO> signout(@RequestHeader(name = "NotificationToken") String notificationToken) {
        try {
            credentialService.signout(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), notificationToken);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, "You logout is OK!"), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), "Invalid notification token", "The notification token you provided is invalid"), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getNotifications")
    @PreAuthorize("hasAnyAuthority('USER_READ_PRIVILEGE')")
    public ResponseEntity<CustomResponseObjectDTO> getNotifications(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "25") Integer size) {
        try {
            return new ResponseEntity<>(createResponse(HttpStatus.OK, credentialService.getNotification(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), page, size)), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), "Username not found", "The username is not in the DB."), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getVisits")
    @PreAuthorize("hasAnyAuthority('USER_READ_PRIVILEGE')")
    public ResponseEntity<CustomResponseObjectDTO> getVisits(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "25") Integer size, @RequestParam(defaultValue = "") String search) {
        try {
            return new ResponseEntity<>(createResponse(HttpStatus.OK, visitService.getVisits(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), page, size, search)), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), e.getMessage(), "The username is not a person!"), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getHealthStatus")
    @PreAuthorize("hasAnyAuthority('USER_READ_PRIVILEGE')")
    public ResponseEntity<CustomResponseObjectDTO> getHealthStatus(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "25") Integer size) {
        try {
            return new ResponseEntity<>(createResponse(HttpStatus.OK, healthStatusService.getHealthStatus(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), page, size)), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), e.getMessage(), "The username is not a person!"), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/addHealthStatus")
    @PreAuthorize("hasAnyAuthority('USER_WRITE_PRIVILEGE')")
    public ResponseEntity<CustomResponseObjectDTO> addHealthStatus(@RequestBody HealthStatusMobileAddDTO healthStatusMobileAddDTO) {
        try {
            return new ResponseEntity<>(createResponse(HttpStatus.OK, healthStatusService.addHealthStatusByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), healthStatusMobileAddDTO)), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), e.getMessage(), "The username is not a person!"), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getExposition")
    @PreAuthorize("hasAnyAuthority('USER_READ_PRIVILEGE')")
    public ResponseEntity<CustomResponseObjectDTO> getExposition(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "25") Integer size) {
        try {
            return new ResponseEntity<>(createResponse(HttpStatus.OK, healthStatusService.getExposition(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), page, size)), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), e.getMessage(), "The username is not a person!"), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getStatus")
    @PreAuthorize("hasAnyAuthority('USER_READ_PRIVILEGE')")
    public ResponseEntity<CustomResponseObjectDTO> getExposition() {
        try {
            return new ResponseEntity<>(createResponse(HttpStatus.OK, healthStatusService.getStatus(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), e.getMessage(), "The username is not a person!"), HttpStatus.CONFLICT);
        }
    }
}
