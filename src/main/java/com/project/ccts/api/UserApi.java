package com.project.ccts.api;

import com.project.ccts.dto.CustomResponseObjectDTO;
import com.project.ccts.service.CredentialService;
import com.project.ccts.util.exception.CustomApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.ccts.dto.CustomResponseObjectUtil.createResponse;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    private CredentialService credentialService;

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PutMapping("/signout")
    public ResponseEntity<CustomResponseObjectDTO> signout(@RequestHeader(name = "NotificationToken") String notificationToken) {
        try {
            credentialService.signout(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), notificationToken);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, "You logout is OK!"), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), "Invalid notification token", "The notification token you provided is invalid"), HttpStatus.CONFLICT);
        }
    }
}
