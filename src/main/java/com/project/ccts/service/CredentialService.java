package com.project.ccts.service;

import com.project.ccts.model.entities.*;
import com.project.ccts.repository.CredentialRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.exception.CustomApiException;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CredentialService extends AbstractCrud<Credential, Long> {

    private CredentialRepository credentialRepository;
    private PersonService personService;
    private RoleService roleService;
    private NotificationTokenService notificationTokenService;
    private NotificationService notificationService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository, PersonService personService, RoleService roleService, NotificationTokenService notificationTokenService, NotificationService notificationService, PasswordEncoder passwordEncoder) {
        this.credentialRepository = credentialRepository;
        this.personService = personService;
        this.roleService = roleService;
        this.notificationTokenService = notificationTokenService;
        this.notificationService = notificationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JpaRepository<Credential, Long> getDao() {
        return credentialRepository;
    }

    public Credential findByUsername(String username) { return credentialRepository.findByUsername(username); }

    public Person findPersonByUsername(String username) throws CustomApiException {
        Credential credential = findByUsername(username);

        if (credential != null && credential instanceof UserCredential) {
            return ((UserCredential) credential).getPerson();
        } else {
            throw new CustomApiException("This user is not a person!", 605);
        }
    }

    public void signup(String personalIdentifier, String email, String username, String password) throws CustomApiException {
        //Validating the person
        Person person = checkPersonalIdentifier(personalIdentifier);

        //Check if the email is valid
        checkEmail(email, person);

        //Check if the username is valid
        checkUsername(username);

        //Finishing the entire process
        UserCredential credential = new UserCredential(
                username,
                passwordEncoder.encode(password),
                true,
                person,
                List.of(roleService.findByName("ROLE_USER"))
        );
        credential = (UserCredential) createOrUpdate(credential);
        person.setUserCredential(credential);
        person.setEmail(email);
        personService.createOrUpdate(person);

        Logger.getInstance().getLog(getClass()).info(String.format("%s signup to CCTS", credential.getUsername()));
    }

    public Person checkPersonalIdentifier(String personalIdentifier) throws CustomApiException {
        Person person = personService.findPersonByPersonalIdentifier(personalIdentifier);
        if (!personalIdentifier.matches("[0-9]+") || personalIdentifier.length() != 11 || person == null || person.getUserCredential() != null) {
            throw new CustomApiException("Personal identifier is invalid. Try again.", 600);
        } else {
            return person;
        }
    }

    public void checkEmail(String email) throws CustomApiException {
        Person emailPerson = personService.findByEmail(email);
        if (emailPerson != null) {
            throw new CustomApiException("Email is invalid. Try with other!", 601);
        }
    }

    public void checkEmail(String email, Person person) throws CustomApiException {
        Person emailPerson = personService.findByEmail(email);
        if (emailPerson != null && !person.equals(emailPerson)) {
            throw new CustomApiException("Email is invalid. Try with other!", 601);
        }
    }

    public void checkUsername(String username) throws CustomApiException {
        if (username.length() <= 0 || !username.matches("^[a-zA-Z0-9]*$") || findByUsername(username) != null) {
            throw new CustomApiException("Username is invalid. Try with other!", 602);
        }
    }

    public void signout(String username, String notificationToken) throws CustomApiException {
        NotificationToken token = notificationTokenService.findByToken(notificationToken);
        if (token != null && token.getUserCredential().getUsername().equals(username)) {
            notificationTokenService.delete(token);
        } else {
            throw new CustomApiException("Invalid notification token", 603);
        }
        Logger.getInstance().getLog(getClass()).info(String.format("%s logout", username));
    }

    public void addNotificationToken(Credential username, String notificationToken) {
        NotificationToken token = notificationTokenService.findByToken(notificationToken);
        if (token == null) {
            token = new NotificationToken(notificationToken, (UserCredential) username);
            notificationTokenService.createOrUpdate(token);
        }
    }

    public Collection<Notification> getNotification(String username, Integer page, Integer size) throws CustomApiException {
        Credential credential = findByUsername(username);
        if (credential != null) {
            return notificationService.findByUserCredentialAndOrderBySendDateDataDesc(((UserCredential) credential), PageRequest.of(page, size));
        } else {
            throw new CustomApiException("User not found", 604);
        }
    }
}
