package com.project.ccts.api;

import com.project.ccts.dto.CustomResponseObjectDTO;
import com.project.ccts.dto.SignUpDTO;
import com.project.ccts.model.entities.Notification;
import com.project.ccts.model.entities.NotificationData;
import com.project.ccts.model.entities.UserCredential;
import com.project.ccts.service.CredentialService;
import com.project.ccts.service.NotificationService;
import com.project.ccts.util.exception.CustomApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.ccts.dto.CustomResponseObjectUtil.createResponse;

@RestController
@RequestMapping("/api/public")
public class PublicApi {

    private CredentialService credentialService;
    private NotificationService notificationService;

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomResponseObjectDTO> signup(@RequestBody SignUpDTO signUpDTO) {
        try {
            credentialService.signup(signUpDTO.getPersonalIdentifier(), signUpDTO.getEmail(), signUpDTO.getUsername(), signUpDTO.getPassword());
            return new ResponseEntity<>(createResponse(HttpStatus.OK, String.format("%s registrado con existo", signUpDTO.getUsername())), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), "User signup failed", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/checkPersonalIdentifier/{personalIdentifier}")
    public ResponseEntity<CustomResponseObjectDTO> checkPersonalIdentifier(@PathVariable String personalIdentifier) {
        try {
            credentialService.checkPersonalIdentifier(personalIdentifier);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, String.format("%s esta disponible para cuentas", personalIdentifier)), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), "Personal identifier not found or not available for accounts", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/checkUsername/{username}")
    public ResponseEntity<CustomResponseObjectDTO> checkUsername(@PathVariable String username) {
        try {
            credentialService.checkUsername(username);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, String.format("%s esta disponible para cuentas", username)), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), "Username not available for accounts", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/checkEmail/{email}")
    public ResponseEntity<CustomResponseObjectDTO> checkEmail(@PathVariable String email) {
        try {
            credentialService.checkEmail(email);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, String.format("%s esta disponible para cuentas", email)), HttpStatus.OK);
        } catch (CustomApiException e) {
            return new ResponseEntity<>(createResponse(e.getCode(), "Email not available for accounts", e.getMessage()), HttpStatus.CONFLICT);
        }
    }


    //TODO: DELETE TEMP ENDPOINT
    @GetMapping("/send")
    public void sendNotification(@RequestParam String username, @RequestParam String title, @RequestParam String message) {
        notificationService.sendNotificationToUser(new Notification(title, "", message, new NotificationData("Home"), (UserCredential) credentialService.findByUsername(username)));
    }
}
