package com.project.ccts.bootstrap;

import com.project.ccts.model.entities.*;
import com.project.ccts.model.enums.CivilStatus;
import com.project.ccts.model.enums.Gender;
import com.project.ccts.service.*;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

@Component
public class DefaultUserDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private CredentialService credentialService;
    private PrivilegeService privilegeService;
    private RoleService roleService;
    private PersonService personService;
    private PasswordEncoder passwordEncoder;
    private NotificationService notificationService;

    @Autowired
    public DefaultUserDataLoader(CredentialService credentialService, PrivilegeService privilegeService, RoleService roleService, PersonService personService, PasswordEncoder passwordEncoder, NotificationService notificationService) {
        this.credentialService = credentialService;
        this.privilegeService = privilegeService;
        this.roleService = roleService;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger.getInstance().getLog(this.getClass()).info("Default user data bootstrap [...]");

        //Creating default Roles and Privileges
        createDefaultRolesAndPrivilege();

        //Creating default superusers
        createDefaultSuperusers();

        Logger.getInstance().getLog(this.getClass()).info("Ending default user bootstrap [...]");
    }

    private void createDefaultSuperusers() {
        Logger.getInstance().getLog(this.getClass()).info("Creating and updating superusers...");

        //All roles for the superuser
        Collection<Role> superuserRoles = roleService.findAll();

        //Check if the superusers exists
        Credential admin = credentialService.findByUsername("admin");
        if (admin == null) {
            admin = new UserCredential();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(superuserRoles);
            admin.setAuthenticated(true);
            admin = credentialService.createOrUpdate(admin);
        }

        Notification notification = new Notification("Precaucion", "Mi primera notificacion", null, (UserCredential) admin);

        notificationService.createOrUpdate(notification);

        Person person = new Person(
                "00000000000",
                "Administrador",
                "",
                "admin@ccts",
                "849-253-6511",
                LocalDate.of(1999, Calendar.FEBRUARY + 1,22),
                Gender.MALE,
                CivilStatus.SINGLE,
                "Ingeniero",
                new Address("Calle 80 #5 Los tocones", "51000", "Santiago", "Republica Dominicana"),
                (UserCredential) admin
        );

        personService.createOrUpdate(person);
    }

    private void createDefaultRolesAndPrivilege() {
        Logger.getInstance().getLog(this.getClass()).info("Creating and updating application default privileges...");

        Collection<Privilege> beaconPrivilege = Arrays.asList(
                privilegeService.createPrivilegeIfNotFound("NODE_READ_PRIVILEGE"),
                privilegeService.createPrivilegeIfNotFound("NODE_WRITE_PRIVILEGE")
        );
        Collection<Privilege> adminPrivilege = Arrays.asList(
                privilegeService.createPrivilegeIfNotFound("ADMIN_READ_PRIVILEGE"),
                privilegeService.createPrivilegeIfNotFound("ADMIN_WRITE_PRIVILEGE")
        );
        Collection<Privilege> userPrivilege = Arrays.asList(
                privilegeService.createPrivilegeIfNotFound("USER_READ_PRIVILEGE"),
                privilegeService.createPrivilegeIfNotFound("USER_WRITE_PRIVILEGE")
        );

        Logger.getInstance().getLog(this.getClass()).info("Creating and updating application default roles...");

        roleService.createRoleIfNotFound("ROLE_ADMIN", adminPrivilege);
        roleService.createRoleIfNotFound("ROLE_NODE", beaconPrivilege);
        roleService.createRoleIfNotFound("ROLE_USER", userPrivilege);
    }
}