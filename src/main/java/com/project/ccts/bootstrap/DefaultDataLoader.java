package com.project.ccts.bootstrap;

import com.project.ccts.model.entities.*;
import com.project.ccts.model.enums.InstitutionType;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.service.*;
import com.project.ccts.model.enums.CivilStatus;
import com.project.ccts.model.enums.Gender;
import com.project.ccts.util.exception.CustomApiException;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    private InstitutionService institutionService;
    private GlobalStatisticsService globalStatisticsService;


    @Autowired
    public DefaultDataLoader(InstitutionService institutionService,CredentialService credentialService,
                             PrivilegeService privilegeService, RoleService roleService,
                             PersonService personService, PasswordEncoder passwordEncoder,
                             NotificationService notificationService, LocalityService localityService,
                             NodeService nodeService, VisitService visitService,GlobalStatisticsService globalStatisticsService) {
        this.credentialService = credentialService;
        this.privilegeService = privilegeService;
        this.roleService = roleService;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
        this.localityService = localityService;
        this.nodeService = nodeService;
        this.visitService = visitService;
        this.institutionService = institutionService;
        this.globalStatisticsService = globalStatisticsService;
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
        //createDefaultVisits();

        try {
            loadGlobalStatistics();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        Credential admin2 = credentialService.findByUsername("admin2");
        if (admin2 == null){
            admin2 = new UserCredential();
            admin2.setUsername("admin2");
            admin2.setPassword(passwordEncoder.encode("admin"));
            admin2.addRole(roleService.findByName("ROLE_USER"));
            admin2.addRole(roleService.findByName("STAFF_MEDICO"));
            admin2.setAuthenticated(true);
            admin2 = credentialService.createOrUpdate(admin2);
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
        Person person2 = new Person(
                "10110101010",
                "Administrador 2",
                "",
                "admin2@ccts",
                "849-381-9028",
                LocalDate.of(1999, Calendar.JUNE + 1,04),
                Gender.MALE,
                CivilStatus.SINGLE,
                "Ingeniero",
                new Address("Calle 80 #5 Los tocones", "51000", "Santiago", "Republica Dominicana"),
                (UserCredential) admin2
        );


        personService.createOrUpdate(person);
        personService.createOrUpdate(person2);
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
        Collection<Privilege> medicalStaffPrivilege = Collections.singletonList(
                privilegeService.createPrivilegeIfNotFound("ACTUALIZAR_ESTADO_SALUD")
        );

        Logger.getInstance().getLog(this.getClass()).info("Creating and updating application default roles...");

        roleService.createRoleIfNotFound("ROLE_ADMIN", adminPrivilege);
        roleService.createRoleIfNotFound("ROLE_NODE", beaconPrivilege);
        roleService.createRoleIfNotFound("ROLE_USER", userPrivilege);
        roleService.createRoleIfNotFound("STAFF_MEDICO", medicalStaffPrivilege);
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

        Locality locality2 = new Locality(
                "REFERENCIA",
                new Address("Autopista Duarte Km 1 1/2","51000","Santiago","Republica Dominicana"),
                "referencia@spring.com",
                "8099999999");
        locality2.setGpsLocation(new GpsLocation(19.4433342D, -70.68232591D));
        localityService.createOrUpdate(locality2);
        Institution institution = new Institution(locality2.getName(),locality2.getAddress(),locality2.getEmail(),
                locality2.getCellPhone(), InstitutionType.HEALTH);
        institutionService.createOrUpdate(institution);

        Logger.getInstance().getLog(this.getClass()).info("Creating and updating default nodes...");

        List<Role> roles = List.of(roleService.findByName("ROLE_NODE"));

        NodeCredential prototype1 = new NodeCredential("prototype1", passwordEncoder.encode("prototype1"), true);
        prototype1.setRoles(roles);
        NodeCredential prototype2 = new NodeCredential("prototype2", passwordEncoder.encode("prototype2"), true);
        prototype2.setRoles(roles);

        prototype1 = (NodeCredential) credentialService.createOrUpdate(prototype1);
        prototype2 = (NodeCredential) credentialService.createOrUpdate(prototype2);

        Node node1 = new Node("Lab. Telematica", locality, prototype1, 98.3F, NodeStatus.DOWN);
        nodeService.createOrUpdate(node1);

        Node node2 = new Node("Lab. Microprocesadores", locality, prototype2, 80.2F, NodeStatus.DOWN);
        nodeService.createOrUpdate(node2);
    }

    private void createDefaultVisits() {
        Node telematica = nodeService.findByNodeIdentifier("NODE-1");
        Node micro = nodeService.findByNodeIdentifier("NODE-2");

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

    private void loadGlobalStatistics() throws JSONException {
        JSONArray jsonArray = globalStatisticsService.prepareRapidApiRequest("https://covid-193.p.rapidapi.com/history?country=Dominican-Republic");
        Collection<GlobalStatistics> globalStatistics = globalStatisticsService.createGlobalStatisticsHistory(jsonArray);
        globalStatisticsService.createAll(globalStatistics);

    }
}