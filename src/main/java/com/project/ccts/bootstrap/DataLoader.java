package com.project.ccts.bootstrap;

import com.project.ccts.model.Credential;
import com.project.ccts.model.Privilege;
import com.project.ccts.model.Role;
import com.project.ccts.model.UserCredential;
import com.project.ccts.service.CredentialService;
import com.project.ccts.service.PrivilegeService;
import com.project.ccts.service.RoleService;
import com.project.ccts.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Component
@Transactional
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private CredentialService credentialService;
    private PrivilegeService privilegeService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Autowired
    public void setPrivilegeService(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger.getInstance().getLog(this.getClass()).info("Data bootstrap [...]");

        //Creating default Roles and Privileges
        createDefaultRolesAndPrivilege();

        //Creating default superusers
        createDefaultSuperusers();

        Logger.getInstance().getLog(this.getClass()).info("Ending bootstrap [...]");
    }

    private void createDefaultSuperusers() {
        Logger.getInstance().getLog(this.getClass()).info("Creating and update superusers...");

        //All roles for the superuser
        Collection<Role> superuserRoles = roleService.findAll();

        //Check if the superusers exit
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

    public void createDefaultRolesAndPrivilege() {
        Logger.getInstance().getLog(this.getClass()).info("Creating and update Application Privileges...");

        Privilege nodeReadPrivilege = createPrivilegeIfNotFound("NODE_READ_PRIVILEGE");
        Privilege nodeWritePrivilege = createPrivilegeIfNotFound("NODE_WRITE_PRIVILEGE");

        Logger.getInstance().getLog(this.getClass()).info("Creating and update Application Roles...");

        //createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_NODE", Arrays.asList(nodeReadPrivilege, nodeWritePrivilege));
        //createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
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