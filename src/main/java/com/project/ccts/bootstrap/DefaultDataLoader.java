package com.project.ccts.bootstrap;

import com.project.ccts.model.entities.*;
import com.project.ccts.model.enums.InstitutionType;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.model.helpers.InfectionProbability;
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

import java.time.*;
import java.util.*;

@Component
public class DefaultDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CredentialService credentialService;
    private final PrivilegeService privilegeService;
    private final RoleService roleService;
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;
    private final LocalityService localityService;
    private final NodeService nodeService;
    private final VisitService visitService;
    private final InstitutionService institutionService;
    private final GlobalStatisticsService globalStatisticsService;
    private final ProjectStatisticsService projectStatisticsService;
    private final InfectionProbabilityService infectionProbabilityService;


    @Autowired
    public DefaultDataLoader(InstitutionService institutionService, CredentialService credentialService,
                             PrivilegeService privilegeService, RoleService roleService,
                             PersonService personService, PasswordEncoder passwordEncoder,
                             NotificationService notificationService, LocalityService localityService,
                             NodeService nodeService, VisitService visitService, GlobalStatisticsService globalStatisticsService, ProjectStatisticsService projectStatisticsService, InfectionProbabilityService infectionProbabilityService) {
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
        this.projectStatisticsService = projectStatisticsService;
        this.infectionProbabilityService = infectionProbabilityService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger.getInstance().getLog(this.getClass()).info("Default data bootstrap [...]");

        //Load local statistics
        loadLocalStatistics();

        //Creating default Roles and Privileges
        createDefaultRolesAndPrivilege();

        //Create default localities
        createDefaultLocalities();

        //Create default visits
        //createDefaultVisits();

        //Load global statistics
        loadGlobalStatistics();


        //Creating default superusers

        try {
            createDefaultSuperusers();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Create default persons
        createDefaultPersons();


        loadTransmisionProbability();

        Logger.getInstance().getLog(this.getClass()).info("Ending default data bootstrap [...]");
    }

    private void createDefaultSuperusers() throws Exception {
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

        Credential admin3 = credentialService.findByUsername("admin3");
        if (admin3 == null){
            admin3 = new UserCredential();
            admin3.setUsername("admin3");
            admin3.setPassword(passwordEncoder.encode("admin"));
            admin3.addRole(roleService.findByName("ROLE_USER"));
            admin3.addRole(roleService.findByName("STAFF_MEDICO"));
            admin3.setAuthenticated(true);
            admin3 = credentialService.createOrUpdate(admin3);
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
        Collection<Node> nodes = nodeService.nodeByLocality((long) 1);
        Locality locality = localityService.findById((long) 1);


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
        Person person3 = new Person(
                "10100123456",
                "Administrador 3",
                "",
                "admin3@ccts",
                "849-381-9028+1",
                LocalDate.of(1999, Calendar.JUNE + 1,04),
                Gender.MALE,
                CivilStatus.SINGLE,
                "Ingeniero",
                new Address("Calle 80 #5 Los tocones", "51000", "Santiago", "Republica Dominicana"),
                (UserCredential) admin3
        );

        personService.createOrUpdate(person);
        personService.createOrUpdate(person2);
        personService.createOrUpdate(person3);

        visitService.createOrUpdate(new Visit(nodes,locality,person,LocalDateTime.now(),LocalDateTime.now().plusMinutes(10), (float) 10));
        visitService.createOrUpdate(new Visit(nodes,locality,person2,LocalDateTime.now().plusMinutes(3),LocalDateTime.now().plusMinutes(7), (float) 10));


        visitService.createOrUpdate(new Visit(nodes,locality,person,LocalDateTime.now().minusDays(5),LocalDateTime.now().minusDays(5).plusMinutes(10), (float) 10));
        visitService.createOrUpdate(new Visit(nodes,locality,person2,LocalDateTime.now().minusDays(5).plusMinutes(2),LocalDateTime.now().minusDays(5).plusMinutes(7), (float) 10));
        visitService.createOrUpdate(new Visit(nodes,locality,person3,LocalDateTime.now().minusDays(5),LocalDateTime.now().minusDays(5).plusMinutes(13), (float) 10));
       // visitService.createOrUpdate(new Visit(nodes,locality,person2,LocalDateTime.now().plusMinutes(3),LocalDateTime.now().plusMinutes(7), (float) 10));



        visitService.findAllVisitsCorrelatedTimeAndSpace(person,13).stream().forEach(visitAndTimeShared -> {
            Logger.getInstance().getLog(DefaultDataLoader.class).warn("Close contact infected user ID: "+person.getId()
                    +" UserID: "+visitAndTimeShared.getVisit().getId()+" Time in minutes: "+visitAndTimeShared.getTimeInMinutes());
        });

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

        Locality locality3 = new Locality(
                "REFERENCIA LA VEGA",
                new Address("Avenida Rivas Km 1 1/2","41000","La Vega","Republica Dominicana"),
                "referencialavega@spring.com",
                "8091234567");
        locality3.setGpsLocation(new GpsLocation(19.4433142D, -70.68275591D));
        locality3 = localityService.createOrUpdate(locality3);


        Institution institution = new Institution(locality2.getName(),locality2.getAddress(),locality2.getEmail(),
                locality2.getCellPhone(), InstitutionType.HEALTH);
        Institution institution2 = new Institution(locality3.getName(),locality3.getAddress(),locality3.getEmail(),
                locality3.getCellPhone(), InstitutionType.HEALTH);
        institutionService.createOrUpdate(institution);
        institutionService.createOrUpdate(institution2);

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
        projectStatisticsService.addNodeRegistered();

        Node node2 = new Node("Lab. Microprocesadores", locality, prototype2, 80.2F, NodeStatus.DOWN);
        nodeService.createOrUpdate(node2);
        projectStatisticsService.addNodeRegistered();
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

    private void loadGlobalStatistics() {
        try {
            JSONArray jsonArray = globalStatisticsService.prepareRapidApiRequest("https://covid-193.p.rapidapi.com/history?country=Dominican-Republic");
            Collection<GlobalStatistics> globalStatistics = globalStatisticsService.createGlobalStatisticsHistory(jsonArray);
            globalStatisticsService.createAll(globalStatistics);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadLocalStatistics() {
        Logger.getInstance().getLog(getClass()).info("Init project statistics...");

        Long count = projectStatisticsService.countProjectStatistics();

        if (count == 0) {
            ProjectStatistics statistics = projectStatisticsService.createOrUpdate(new ProjectStatistics());
            statistics.setLocalDate(LocalDate.now().minusDays(1));
            projectStatisticsService.createOrUpdate(statistics);
        }

        projectStatisticsService.checkTodayStatistics();
    }
    private void loadTransmisionProbability(){
        Collection<InfectionProbability> infectionProbabilities = new ArrayList<>();
        infectionProbabilities.add(new InfectionProbability(0.007720,"ZERO_TO_TEN"));
        infectionProbabilities.add(new InfectionProbability(0.006734,"TEN_TO_TWENTY"));
        infectionProbabilities.add(new InfectionProbability(0.035369,"TWENTY_TO_THIRTY"));
        infectionProbabilities.add(new InfectionProbability(0.064782,"THIRTY_TO_FORTY"));
        infectionProbabilities.add(new InfectionProbability(0.075954,"FORTY_TO_FIFTY"));
        infectionProbabilities.add(new InfectionProbability(0.155723,"FIFTY_TO_SIXTY"));
        infectionProbabilities.add(new InfectionProbability(0.376402,"SIXTY_TO_SEVENTY"));
        infectionProbabilities.add(new InfectionProbability(0.520127,"SEVENTY_OR_MORE"));
        infectionProbabilityService.createAll(infectionProbabilities);
    }
}