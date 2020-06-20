package com.project.ccts.bootstrap;

import com.project.ccts.model.*;
import com.project.ccts.service.*;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class DefaultUserDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private CredentialService credentialService;
    private PrivilegeService privilegeService;
    private RoleService roleService;
    private BeaconService beaconService;
    private LocalityService localityService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserDataLoader(CredentialService credentialService, PrivilegeService privilegeService, RoleService roleService, BeaconService beaconService, PasswordEncoder passwordEncoder, LocalityService localityService) {
        this.credentialService = credentialService;
        this.privilegeService = privilegeService;
        this.roleService = roleService;
        this.beaconService = beaconService;
        this.passwordEncoder = passwordEncoder;
        this.localityService = localityService;
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
            ((UserCredential) admin).setNotificationToken("123456789");
            ((UserCredential) admin).setValidationCode("123456789");
            credentialService.createOrUpdate(admin);
        }
    }

    private void createDefaultRolesAndPrivilege() {
        Logger.getInstance().getLog(this.getClass()).info("Creating and updating application privileges...");

        Collection<Privilege> beaconPrivilege = Arrays.asList(createPrivilegeIfNotFound("BEACON_READ_PRIVILEGE"), createPrivilegeIfNotFound("BEACON_WRITE_PRIVILEGE"));

        Collection<Privilege> adminPrivilege = Arrays.asList(createPrivilegeIfNotFound("ADMIN_READ_PRIVILEGE"), createPrivilegeIfNotFound("ADMIN_WRITE_PRIVILEGE"));

        Logger.getInstance().getLog(this.getClass()).info("Creating and updating application roles...");

        createRoleIfNotFound("ROLE_ADMIN", adminPrivilege);
        createRoleIfNotFound("ROLE_BEACON", beaconPrivilege);
        createRoleIfNotFound("ROLE_USER", null);
    }

    private void createRoleIfNotFound(String role_user, Collection<Privilege> privileges) {
        Role role = roleService.findByName(role_user);
        if (role == null) {
            role = new Role(role_user);
            role.setPrivileges(privileges);
            roleService.createOrUpdate(role);
        }
    }

    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeService.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeService.createOrUpdate(privilege);
        }
        return privilege;
    }
}