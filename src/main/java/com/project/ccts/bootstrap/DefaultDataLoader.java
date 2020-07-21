package com.project.ccts.bootstrap;

import com.project.ccts.model.entities.*;
import com.project.ccts.service.*;
import com.project.ccts.model.enums.CivilStatus;
import com.project.ccts.model.enums.Gender;
import com.project.ccts.util.exception.CustomApiException;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Component
public class DefaultDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private CredentialService credentialService;
    private PrivilegeService privilegeService;
    private RoleService roleService;
    private PersonService personService;
    private PasswordEncoder passwordEncoder;
    private NotificationService notificationService;
    private LocalityService localityService;
    private NodeService nodeService;
    private VisitService visitService;

    @Autowired
    public DefaultDataLoader(CredentialService credentialService, PrivilegeService privilegeService, RoleService roleService, PersonService personService, PasswordEncoder passwordEncoder, NotificationService notificationService, LocalityService localityService, NodeService nodeService, VisitService visitService) {
        this.credentialService = credentialService;
        this.privilegeService = privilegeService;
        this.roleService = roleService;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
        this.localityService = localityService;
        this.nodeService = nodeService;
        this.visitService = visitService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger.getInstance().getLog(this.getClass()).info("Default data bootstrap [...]");

        //Creating default Roles and Privileges
        createDefaultRolesAndPrivilege();

        //Creating default superusers
        createDefaultSuperusers();

        //Create default persons
        createDefaultPersons();

        //Create default localities
        createDefaultLocalities();

        //Create default visits
        createDefaultVisits();

        Logger.getInstance().getLog(this.getClass()).info("Ending default data bootstrap [...]");
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

        Notification notification = new Notification("Bienvenido", "Bienvenido a CCTS", "Bienvenido a CCTS", new NotificationData("Home"), (UserCredential) admin);

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

    public void createDefaultPersons() {
        Logger.getInstance().getLog(getClass()).info("Creating and updating default persons [...]");

        Collection<Person> personCollection = List.of(
                new Person(
                        "11111111111",
                        "Osvaldo",
                        "Fernandez",
                        null,
                        "849-253-6511",
                        LocalDate.of(1999, Calendar.FEBRUARY + 1,22),
                        Gender.MALE,
                        CivilStatus.SINGLE,
                        "Estudiante",
                        new Address("Calle 80 #5 Los tocones", "51000", "Santiago", "Republica Dominicana"),
                        null
                ),
                new Person(
                        "22222222222",
                        "Edgar",
                        "Garcia",
                        null,
                        "829-200-6511",
                        LocalDate.of(1999, Calendar.JUNE + 1,10),
                        Gender.MALE,
                        CivilStatus.SINGLE,
                        "Estudiante",
                        new Address("Calle 80 Los Minas", "51020", "La Vega", "Republica Dominicana"),
                        null
                ),
                new Person(
                        "33333333333",
                        "Armando",
                        "Roman",
                        null,
                        "809-253-6610",
                        LocalDate.of(1999, Calendar.DECEMBER + 1,30),
                        Gender.MALE,
                        CivilStatus.SINGLE,
                        "Estudiante",
                        new Address("Calle 80 Los Alcarrizos", "51030", "Navarrete", "Republica Dominicana"),
                        null
                ),
                new Person(
                        "44444444444",
                        "Daniel",
                        "Peña",
                        null,
                        "809-203-6001",
                        LocalDate.of(1999, Calendar.JANUARY + 1,24),
                        Gender.MALE,
                        CivilStatus.SINGLE,
                        "Estudiante",
                        new Address("Calle 9 Los Prados", "53000", "Navarrete", "Republica Dominicana"),
                        null
                )
        );
        personService.createAll(personCollection);
    }

    public void createDefaultLocalities(){
        Logger.getInstance().getLog(this.getClass()).info("Creating and updating default locality...");

        Locality locality = new Locality(
                "PUCMM-STI",
                new Address("Autopista Duarte Km 1 1/2","51000","Santiago","Republica Dominicana"),
                "sample@spring.com",
                "8097776766");
        locality.setGpsLocation(new GpsLocation(19.4437142D, -70.68265591D));
        locality = localityService.createOrUpdate(locality);

        Logger.getInstance().getLog(this.getClass()).info("Creating and updating default nodes...");

        List<Role> roles = List.of(roleService.findByName("ROLE_NODE"));

        NodeCredential prototype1 = new NodeCredential("prototype1", passwordEncoder.encode("prototype1"), true);
        prototype1.setRoles(roles);
        NodeCredential prototype2 = new NodeCredential("prototype2", passwordEncoder.encode("prototype2"), true);
        prototype2.setRoles(roles);

        prototype1 = (NodeCredential) credentialService.createOrUpdate(prototype1);
        prototype2 = (NodeCredential) credentialService.createOrUpdate(prototype2);

        Node node1 = new Node("Lab. Telematica", locality, prototype1, 98.3F);
        nodeService.createOrUpdate(node1);

        Node node2 = new Node("Lab. Microprocesadores", locality, prototype2, 80.2F);
        nodeService.createOrUpdate(node2);
    }

    private void createDefaultVisits() {
        Node telematica = nodeService.findByNodeIdentifier("prototype1-1");
        Node micro = nodeService.findByNodeIdentifier("prototype2-2");

        try {
            Person person = credentialService.findPersonByUsername("admin");
            visitService.createAll(List.of(
                    new Visit(List.of(telematica, micro), telematica.getLocality(), person, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 10.5F),
                    new Visit(List.of(micro), telematica.getLocality(), person, LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(3), 8F)
            ));
        } catch (CustomApiException e) {
            e.printStackTrace();
        }
    }
}
