package com.project.ccts.api;

import com.project.ccts.dto.*;
import com.project.ccts.dto.locality.RealTimeSearch;
import com.project.ccts.dto.locality.SetUsersToLocality;
import com.project.ccts.model.entities.*;
import com.project.ccts.model.enums.InstitutionType;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.project.ccts.dto.CustomResponseObjectUtil.createResponse;

@RestController
@RequestMapping("api/dashboard")
public class DashboardApi {
    private LocalityService localityService;
    private PersonService personService;
    private CredentialService credentialService;
    private HealthStatusService healthStatusService;
    private RoleService roleService;
    private InstitutionService institutionService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setLocalityService(LocalityService localityService) {
        this.localityService = localityService;
    }

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Autowired
    public void setHealthStatusService(HealthStatusService healthStatusService) {
        this.healthStatusService = healthStatusService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    public void setInstitutionService(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping(path = "node-distribution", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> nodeDistributionByLocation() {
        Collection<Locality> locality = localityService.findAll();

        if (locality != null) {
            return new ResponseEntity<>(createResponse(HttpStatus.OK, localityService.parseToLocalityDTO(locality)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST, "No existen localidades Registradas"), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "locations", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getAllLocationName() {
        Collection<Locality> localities = localityService.findAll();
        if (localities != null) {
            Collection<LocalityByNameIdDTO> localityByNameIdDTOS = createLocalityByNameIdDTO(localities);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, localityByNameIdDTOS), HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.NOT_FOUND, "No existen localidades"), HttpStatus.NOT_FOUND);
    }
    @GetMapping(path = "locations/health",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getAllHealthLocalities(){
        Collection<Institution> institutions = institutionService.getInstitutionByType(InstitutionType.HEALTH);
        if (institutions != null){
            Collection<LocalityByNameIdDTO> localityByNameIdDTOS = createLocalityByNameIdDTO(institutions);
            return new ResponseEntity<>(createResponse(HttpStatus.OK,localityByNameIdDTOS),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,"No existen localidades de salud registradas"),HttpStatus.ACCEPTED);
    }
    @GetMapping(path = "locations/health/{name}/{email}/{id}",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getHealthLocality(@PathVariable String name,@PathVariable String email,@PathVariable Long id){
        Institution institution = institutionService.findByTypeAndNameAndEmailAndId(InstitutionType.HEALTH,name,email,id);
        if (institution != null){
            LocalityDetailsDTO localityDetailsDTO = new LocalityDetailsDTO(institution.getId(),institution.getName(),
                    institution.getAddress(),institution.getEmail(),institution.getCellPhone(),null);
            return new ResponseEntity<>(createResponse(HttpStatus.OK,localityDetailsDTO),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,"No existen localidades de salud registradas"),HttpStatus.ACCEPTED);
    }
    @GetMapping(path = "locations/health/containing/{name}",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getHealthLocalityContaining(@PathVariable String name){
        Collection<Institution> institutions = institutionService.findAllByTypeAndNameContaining(name,InstitutionType.HEALTH);
        Collection<RealTimeSearch> toLocalities = new ArrayList<>();
        institutions.stream().forEach(institution -> toLocalities.add(new RealTimeSearch(institution.getName(),institution.getEmail(),institution.getId())));
        return new ResponseEntity<>(createResponse(HttpStatus.OK,toLocalities),HttpStatus.OK);
    }

    @GetMapping(path = "admins/health/unregistered",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getUnregisteredAdmins(){
        Collection<Credential> userCredential = credentialService.findAll();
        if (userCredential != null){
            Collection<String> usernames = new ArrayList<>();
            userCredential.stream().forEach(credential -> usernames.add(credential.getUsername()));
            return new ResponseEntity<>(createResponse(HttpStatus.OK,usernames),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,"No existen usuarios registrados"),HttpStatus.ACCEPTED);
    }
    @GetMapping(path = "admins", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getAdmins(){
        Collection<UserCredential> userCredentials = credentialService.findUsersCredentialType();
        Collection<AdminListDTO> listDTO = credentialService.createAdminDTO(userCredentials);
        if (listDTO != null){
            return new ResponseEntity<>(createResponse(HttpStatus.OK,listDTO),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,""),HttpStatus.ACCEPTED);
    }
    @GetMapping(path = "admins/{username}",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getAdminByUsername(@PathVariable String username){
        UserCredential credential = (UserCredential) credentialService.findByUsername(username);
        if (credential != null){
            AdminListDTO listDTO = new AdminListDTO(credential.getPerson().getEmail(),credential.getUsername(),
                    credentialService.createRoleList(credential.getRoles()));
            Collection<AdminListDTO> adminListDTOS = new ArrayList<>();
            adminListDTOS.add(listDTO);
            return new ResponseEntity<>(createResponse(HttpStatus.OK,adminListDTOS),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,String.format("No existen entidades con el nombre de usuario %s",username)),HttpStatus.ACCEPTED);

    }
    @GetMapping(path = "roles",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getAllRoles(){
        Collection<Role> roles = roleService.findAll();
        if (roles != null){
            Collection<String> rolesNames = new ArrayList<>();
            roles.stream().forEach(role -> {
                rolesNames.add(role.getName());
            });

            return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,rolesNames),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,"No existen roles"),HttpStatus.OK);
    }
    @GetMapping(path = "roles/privileges",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getAllRolesPrivileges(){
        Collection<Role> roles = roleService.findAll();
        if (roles != null){
            Collection<RolesAndPrivilegesDTO> rolesAndPrivilegesDTOS = new ArrayList<>();
            roles.stream().forEach(role -> {
                Collection<String> privileges = new ArrayList<>();
                role.getPrivileges().stream().forEach(privilege -> {
                    privileges.add(privilege.getName());
                });
                rolesAndPrivilegesDTOS.add(new RolesAndPrivilegesDTO(role.getName(),privileges));
            });
            return new ResponseEntity<>(createResponse(HttpStatus.OK,rolesAndPrivilegesDTOS),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,"No existen roles"),HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "locations/{id}", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> localityInfo(@PathVariable Long id) throws Exception {
        Locality locality = localityService.findById(id);
        Collection<NodeDetailsDTO> nodeDetailsDTOS = new ArrayList<>();
        locality.getNodes().stream().forEach(node ->
                nodeDetailsDTOS.add(new NodeDetailsDTO(node.getId(), node.getNodeIdentifier(), node.getBatteryLevel(), node.getVisits().size(), NodeStatus.ACTIVE)));

        LocalityDetailsDTO localityDetailsDTO = new LocalityDetailsDTO(locality.getId(), locality.getName(), locality.getAddress(),
                locality.getEmail(), locality.getCellPhone(), nodeDetailsDTOS,locality.getGpsLocation());
        return new ResponseEntity<>(createResponse(HttpStatus.OK, localityDetailsDTO), HttpStatus.OK);
    }

    @GetMapping(path = "node-distribution/containing/{keyword}", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> findNodeDistributionByNameContaining(@PathVariable String keyword) {
        Collection<Locality> locality = localityService.findByNameContaining(keyword);
        if (locality != null && locality.size() > 0) {
            return new ResponseEntity<>(createResponse(HttpStatus.OK, localityService.parseToLocalityDTO(locality)), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED, String.format("No existen localidades con el nombre %s",keyword)), HttpStatus.ACCEPTED);

        }
    }

    @GetMapping(path = "test/patient/{id}", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getPatientDetailsByPersonalIdentifier(@PathVariable String id) {

        Person person = personService.findPersonByPersonalIdentifier(id);
        PersonDTO personDTO = new PersonDTO();
        if (person != null) {
            personDTO = new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress()
                    , person.getOccupation(), Period.between(person.getBirthDate(), LocalDate.now()).getYears(),
                    personService.getPersonTestStatus(person), person.getEmail());
            return new ResponseEntity<>(createResponse(HttpStatus.OK, personDTO), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED, String.format("El usuario cedula %s no existe, puede proceder al registro",id)), HttpStatus.ACCEPTED);
        }


    }
    @PutMapping(value="locality/users",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> setUsersToLocalities(@RequestBody SetUsersToLocality setUsersToLocality){
            Institution institution = institutionService.findByNameEmailType(setUsersToLocality.getName(),setUsersToLocality.getEmail(),InstitutionType.HEALTH);
            if (institution != null) {
                Collection<UserCredential> userCredentials = credentialService.getExistingUserCredentials(setUsersToLocality.getTag());
                if (userCredentials != null){
                    institution.setUserCredentials(userCredentials);
                    institutionService.createOrUpdate(institution);
                    return new ResponseEntity<>(createResponse(HttpStatus.OK,"Usuarios Anadidos Existosamente"),HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,"Datos Incorrectos, Revise nuevamente"),HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "covid-test/set-patient-status", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> updatePatientStatus(@RequestBody PersonHealthStatusUpdateDTO statusUpdateDTO) {

        Person person = personService.findPersonByPersonalIdentifier(statusUpdateDTO.getId());
        if (person != null) {
            HealthStatus healthStatus = new HealthStatus(statusUpdateDTO.isFever(), statusUpdateDTO.isCough(),
                    statusUpdateDTO.isBreathDifficulty(), statusUpdateDTO.isSoreThroat(), statusUpdateDTO.isSmellLoss(),
                    statusUpdateDTO.isTasteLoss());
            healthStatus.setTest(new Test(statusUpdateDTO.isStatus()));
            healthStatus.setPerson(person);
            healthStatusService.createOrUpdate(healthStatus);

            return new ResponseEntity<>(createResponse(HttpStatus.OK, "Su solicitud ha sido satisfactoria, Perfil del paciente Actualizado"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(createResponse(HttpStatus.NOT_FOUND, "Revise los datos ingresados en el formulario"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "new/user", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> createNewUser(@RequestBody Map<String, Object> payload) {
         Person person = personService.findPersonByPersonalIdentifier((String) payload.get("id"));
        if (person == null) {
            person = new Person((String) payload.get("id"), (String) payload.get("firstName"), (String) payload.get("lastName"),
                    (String) payload.get("email"), (String) payload.get("occupation"),
                    new Address((String) payload.get("direction"), Integer.toString((Integer) payload.get("postalCode")), (String) payload.get("city"), (String) payload.get("country")));
            personService.createOrUpdate(person);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, "Usuario creado sastifactoriamente"), HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST, "Datos incorrectos, revisar datos"), HttpStatus.BAD_REQUEST);
    }
    @PutMapping(path = "admin/roles",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> updateAdminPrivileges(@RequestBody UpdateAdminPrivilegesDTO privilegesDTO){
        UserCredential userCredential = (UserCredential)credentialService.findByUsername(privilegesDTO.getUsername());
         if (userCredential != null && userCredential.getPerson().getEmail().equals(privilegesDTO.getEmail())
        && privilegesDTO.getTags() != null){
            userCredential.setRoles(roleService.rolesToUpdate(userCredential,privilegesDTO));
            credentialService.createOrUpdate(userCredential);
            return new ResponseEntity<>(createResponse(HttpStatus.OK,"Roles de usuario Actualizados"),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,"Revise los datos nuevamente"),HttpStatus.ACCEPTED);

    }

    @PostMapping(path = "location",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> createNewLocation(@RequestBody NewLocalityDTO newLocalityDTO){
        Locality locality = localityService.findByCellPhone(newLocalityDTO.getCellPhone());
        if (locality == null && !newLocalityDTO.getName().isEmpty() && !newLocalityDTO.getCellPhone().isEmpty() && !newLocalityDTO.getDirection().isEmpty()){
            Locality localityAux = new Locality(newLocalityDTO.getName(),new Address(newLocalityDTO.getDirection(),
                    newLocalityDTO.getPostalCode(),newLocalityDTO.getCity(),"Republica Dominicana"),newLocalityDTO.getEmail(),
                    newLocalityDTO.getCellPhone());
            localityAux.setGpsLocation(new GpsLocation(newLocalityDTO.getLatitude(),newLocalityDTO.getLongitude()));


            Institution institution = new Institution(newLocalityDTO.getName(),new Address(newLocalityDTO.getDirection(),
                    newLocalityDTO.getPostalCode(),newLocalityDTO.getCity(),"Republica Dominicana"),newLocalityDTO.getEmail(),
                    newLocalityDTO.getCellPhone(), InstitutionType.valueOf(newLocalityDTO.getType()));
             localityService.createOrUpdate(localityAux);
            institutionService.createOrUpdate(institution);
            return new ResponseEntity<>(createResponse(HttpStatus.OK,"La localidad ha sido creada correctamente"),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,newLocalityDTO),HttpStatus.ACCEPTED);
    }
    public <T> Collection<LocalityByNameIdDTO> createLocalityByNameIdDTO(Collection<T> collection){
        Collection<LocalityByNameIdDTO> localityByNameIdDTOS = new ArrayList<>();
        if (collection != null){
            collection.stream().forEach(institution -> {
                if (institution instanceof Locality || institution instanceof Institution){
                    localityByNameIdDTOS.add(new LocalityByNameIdDTO(((SampleLocality) institution).getName(),
                            ((SampleLocality) institution).getId()));
                }
            });
         }
        return localityByNameIdDTOS;
    }


}
