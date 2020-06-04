package com.project.ccts.bootstrap;

import com.project.ccts.model.*;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.service.*;
import com.project.ccts.util.Logger;
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
    private NodeService nodeService;
    private LocalityService localityService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserDataLoader(CredentialService credentialService, PrivilegeService privilegeService, RoleService roleService, NodeService nodeService, PasswordEncoder passwordEncoder, LocalityService localityService) {
        this.credentialService = credentialService;
        this.privilegeService = privilegeService;
        this.roleService = roleService;
        this.nodeService = nodeService;
        this.passwordEncoder = passwordEncoder;
        this.localityService = localityService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger.getInstance().getLog(this.getClass()).info("User data bootstrap [...]");

        //Creating default Roles and Privileges
        createDefaultRolesAndPrivilege();

        //Creating default superusers
        createDefaultSuperusers();

        //Creating prototype nodes
        createPrototypesNodes();

        Logger.getInstance().getLog(this.getClass()).info("Ending user bootstrap [...]");
    }

    private void createPrototypesNodes() {
        Logger.getInstance().getLog(this.getClass()).info("Creating and updating prototypes nodes...");

        //Prototype locality
        Locality locality = localityService.findByName("Osvaldo Home");
        if (locality == null) {
            locality = new Locality();
            locality.setName("Osvaldo Home");
            locality.setEmail("osvaldof22@hotmail.com");
            locality.setAddress(new Address("Calle 80 #98 Los pepines", "51000", "Santiago", "Domincan Republic"));
            locality.setCellPhone("809-999-9999");
            localityService.createOrUpdate(locality);
        }

        //Find role for a node
        Role nodeRole = roleService.findByName("ROLE_NODE");

        //Check if the nodes prototype credentials exists
        Credential credential = credentialService.findByUsername("prototype");
        if (credential == null) {
            credential = new NodeCredential();
            credential.setUsername("prototype");
            credential.setPassword(passwordEncoder.encode("prototype"));
            credential.setRoles(Arrays.asList(nodeRole));
            credential.setAuthenticated(true);
            ((NodeCredential) credential).setStatus(NodeStatus.DOWN);
            credentialService.createOrUpdate(credential);
        }

        //Check if the node exists
        Node node = nodeService.findByUniqueIdentifier("NODE-PROTOTYPE");
        if (node == null) {
            node = new Node();
            node.setUniqueIdentifier("NODE-PROTOTYPE");
            node.setLocality(locality);
            node.setNodeCredential((NodeCredential) credential);
            node.setGpsLocation(new GpsLocation(19.479519D, -70.717491D));
            nodeService.createOrUpdate(node);
        }
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

        Collection<Privilege> nodePrivilege = Arrays.asList(createPrivilegeIfNotFound("NODE_READ_PRIVILEGE"), createPrivilegeIfNotFound("NODE_WRITE_PRIVILEGE"));

        Collection<Privilege> adminPrivilege = Arrays.asList(createPrivilegeIfNotFound("ADMIN_READ_PRIVILEGE"), createPrivilegeIfNotFound("ADMIN_WRITE_PRIVILEGE"));

        Logger.getInstance().getLog(this.getClass()).info("Creating and updating application roles...");

        createRoleIfNotFound("ROLE_NODE", nodePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivilege);
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