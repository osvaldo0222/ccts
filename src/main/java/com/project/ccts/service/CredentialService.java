package com.project.ccts.service;

import com.project.ccts.model.entities.Credential;
import com.project.ccts.model.entities.NotificationToken;
import com.project.ccts.model.entities.Person;
import com.project.ccts.model.entities.UserCredential;
import com.project.ccts.repository.CredentialRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.exception.CustomApiException;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CredentialService extends AbstractCrud<Credential, Long> {

    private CredentialRepository credentialRepository;
    private PersonService personService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository, PersonService personService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.credentialRepository = credentialRepository;
        this.personService = personService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JpaRepository<Credential, Long> getDao() {
        return credentialRepository;
    }

    public Credential findByUsername(String username) { return credentialRepository.findByUsername(username); }

    public void signup(String personalIdentifier, String email, String username, String password) throws CustomApiException {
        //Validating the person
        Person person = checkPersonalIdentifier(personalIdentifier);

        //Check if the email is valid
        checkEmail(email, person);

        //Check if the username is valid
        checkUsername(username);

        //Finishing the entire process
        UserCredential credential = new UserCredential();
        credential.setPerson(person);
        credential.setUsername(username);
        credential.setPassword(passwordEncoder.encode(password));
        credential.setAuthenticated(true);
        credential.setRoles(List.of(roleService.findByName("ROLE_USER")));
        credential = (UserCredential) createOrUpdate(credential);
        person.setUserCredential(credential);
        person.setEmail(email);
        personService.createOrUpdate(person);
        Logger.getInstance().getLog(getClass()).info(String.format("%s se ha registrado en CCTS", credential.getUsername()));
    }

    public Person checkPersonalIdentifier(String personalIdentifier) throws CustomApiException {
        Person person = personService.findPersonByPersonalIdentifier(personalIdentifier);
        if (person == null || person.getUserCredential() != null) {
            throw new CustomApiException("Personal identifier is invalid. Try again.", 600);
        } else {
            return person;
        }
    }

    public void checkEmail(String email) throws CustomApiException {
        Person emailPerson = personService.findByEmail(email);
        if (emailPerson != null) {
            throw new CustomApiException("Email in use. Try with other!", 601);
        }
    }

    public void checkEmail(String email, Person person) throws CustomApiException {
        Person emailPerson = personService.findByEmail(email);
        if (emailPerson != null && !person.equals(emailPerson)) {
            throw new CustomApiException("Email in use. Try with other!", 601);
        }
    }

    public void checkUsername(String username) throws CustomApiException {
        if (findByUsername(username) != null) {
            throw new CustomApiException("Username in use. Try with other!", 602);
        }
    }
}
