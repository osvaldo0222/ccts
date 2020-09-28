package com.project.ccts.service;

import com.project.ccts.dto.AdminListDTO;
import com.project.ccts.model.entities.*;
import com.project.ccts.repository.CredentialRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.exception.CustomApiException;
import com.project.ccts.util.logger.Logger;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public void setCredentialRepository(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }
    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    public void setNotificationTokenService(NotificationTokenService notificationTokenService) {
        this.notificationTokenService = notificationTokenService;
    }
    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JpaRepository<Credential, Long> getDao() {
        return credentialRepository;
    }

    public Collection<UserCredential> getUsersPaginated(Integer page){
        if(page > 0){
            Collection<Credential> credentials = credentialRepository.findAll(PageRequest.of(page-1,5)).getContent();
            return createUserCredentialCollection(credentials);
        }else{
            Collection<Credential> credentials = credentialRepository.findAll(PageRequest.of(0 ,5)).getContent();
            return createUserCredentialCollection(credentials);
        }
    }
    public int countUserCredential(){
        Collection<Credential> credentials = credentialRepository.findAll();
        int counter = (int) credentials.stream().filter(credential -> credential instanceof UserCredential).count();
        return counter;
    }
    public Collection<UserCredential> createUserCredentialCollection(Collection<Credential> credentials){
        Collection<UserCredential> auxCredentials = new ArrayList<>();
        credentials.stream().forEach(credential -> {
            if (credential instanceof  UserCredential){
                auxCredentials.add((UserCredential) credential);
            }
        });
        return auxCredentials;
    }

    public Collection<AdminListDTO> createAdminDTO(Collection<UserCredential> userCredentials){
        Collection<AdminListDTO> adminList = new ArrayList<>();
        if (userCredentials != null){
            userCredentials.stream().forEach(userCredential -> {
                Collection<String> roles  = createRoleList(userCredential.getRoles());
                adminList.add(new AdminListDTO(userCredential.getPerson().getEmail(),
                        userCredential.getUsername(),roles,userCredential.getAuthenticated()));
            });
        }
        return adminList;
    }
    public Collection<String> createRoleList(Collection<Role> role){
        Collection<String> roles = new ArrayList<>();
        role.stream().forEach(role1 -> roles.add(role1.getName()));
        return roles;
    }

    public Collection<UserCredential> findUsersCredentialType(){
        Collection<Credential> credentials = credentialRepository.findAll();
        Collection<UserCredential> auxCredentials = new ArrayList<>();
        credentials.stream().forEach(credential -> {
            if (credential instanceof  UserCredential){
                auxCredentials.add((UserCredential) credential);
            }
        });
        return auxCredentials;
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

        notificationService.sendNotificationToUser(new Notification("Bienvenido", "", "Bienvenido a CCTS", new NotificationData("Home"), (UserCredential) credential));

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
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(email) || personService.findByEmail(email) != null) {
            throw new CustomApiException("Email is invalid. Try with other!", 601);
        }
    }

    public void checkEmail(String email, Person person) throws CustomApiException {
        EmailValidator validator = EmailValidator.getInstance();
        Person emailPerson = personService.findByEmail(email);
        if (!validator.isValid(email) || (emailPerson != null && !person.equals(emailPerson))) {
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
    public Collection<UserCredential> getExistingUserCredentials(Collection<String> usernames){
        Collection<UserCredential> userCredentials = new ArrayList<>();
        if (usernames != null){
            usernames.stream().forEach(s -> {
                UserCredential userCredential = (UserCredential) findByUsername(s);
                if (userCredential != null){
                    userCredentials.add(userCredential);
                }
            });
        }
        return userCredentials;
    }

}
