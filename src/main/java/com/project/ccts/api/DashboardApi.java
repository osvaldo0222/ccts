package com.project.ccts.api;

import com.project.ccts.dto.*;
import com.project.ccts.model.entities.*;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.service.CredentialService;
import com.project.ccts.service.HealthStatusService;
import com.project.ccts.service.LocalityService;
import com.project.ccts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

import static com.project.ccts.dto.CustomResponseObjectUtil.createResponse;

@RestController
@RequestMapping("api/dashboard")
public class DashboardApi {
    private LocalityService localityService;
    private PersonService personService;
    private CredentialService credentialService;
    private HealthStatusService healthStatusService;


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

    @GetMapping(path = "node-distribution", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> nodeDistributionByLocation() {
        Collection<Locality> locality = localityService.findAll();

        if (locality != null){
            return new ResponseEntity<>(createResponse(HttpStatus.OK,localityService.parseToLocalityDTO(locality)), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST,"No existen localidades Registradas"), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "locations", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getAllLocationName() {
        Collection<Locality> localities = localityService.findAll();
        Collection<LocalityByNameIdDTO> localityByNameIdDTOS = new ArrayList<>();
        if (localities != null) {
            localities.stream().forEach(locality -> {
                localityByNameIdDTOS.add(new LocalityByNameIdDTO(locality.getName(), locality.getId()));
            });
            return new ResponseEntity<>(createResponse(HttpStatus.OK, localityByNameIdDTOS), HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.NOT_FOUND, "No existen localidades"), HttpStatus.NOT_FOUND);

    }

    @GetMapping(path = "locations/{id}", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> localityInfo(@PathVariable Long id) throws Exception {
        Locality locality = localityService.findById(id);
        Collection<NodeDetailsDTO> nodeDetailsDTOS = new ArrayList<>();
        locality.getNodes().stream().forEach(node ->
                nodeDetailsDTOS.add(new NodeDetailsDTO(node.getId(), node.getNodeIdentifier(),node.getBatteryLevel(),node.getVisits().size(), NodeStatus.ACTIVE)));

        LocalityDetailsDTO localityDetailsDTO = new LocalityDetailsDTO(locality.getId(), locality.getName(), locality.getAddress(),
                locality.getEmail(), locality.getCellPhone(), nodeDetailsDTOS);
        return new ResponseEntity<>(createResponse(HttpStatus.OK,localityDetailsDTO), HttpStatus.OK);
    }

    @GetMapping(path = "node-distribution/containing/{keyword}", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> findNodeDistributionByNameContaining(@PathVariable String keyword) {
        Collection<Locality> locality = localityService.findByNameContaining(keyword);
        if(locality != null){
            return new ResponseEntity<>(createResponse(HttpStatus.OK,localityService.parseToLocalityDTO(locality)), HttpStatus.OK);

        }else {
            return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST,"No existen usuarios "), HttpStatus.BAD_REQUEST);

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
        }

        return new ResponseEntity<>(createResponse(HttpStatus.OK,personDTO), HttpStatus.OK);
    }


    @GetMapping(path = "users", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getAllUsers() {
        Collection<Credential> credentials = credentialService.findAll();
        if (credentials != null) {
            Collection<PersonCredentialListDTO> personCredentialListDTOS = new ArrayList<>();
            credentials.stream().forEach(credential -> {
                personCredentialListDTOS
                        .add(new PersonCredentialListDTO(credential.getId(), credential.getUsername(),
                                credential.getAuthenticated(), getRoles(credential.getRoles())
                        ));
            });
            return new ResponseEntity<>(createResponse(HttpStatus.OK, personCredentialListDTOS), HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.NOT_FOUND, "No existen usuarios en esta lista"), HttpStatus.NOT_FOUND);
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
    @PostMapping(path = "new/user",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> createNewUser(@RequestBody PersonDTO personDTO){
        System.out.println(personDTO.getAddress().getDirection());
        Person person = personService.findPersonByPersonalIdentifier(personDTO.getPersonalIdentifier());
        if (person == null){
            person = new Person(personDTO.getPersonalIdentifier(),personDTO.getFirstName(),personDTO.getLastName(),
                    personDTO.getEmail(),personDTO.getOccupation(),personDTO.getAddress());

            personService.createOrUpdate(person);
            return new ResponseEntity<>(createResponse(HttpStatus.OK,"Usuario creado sastifactoriamente"),HttpStatus.OK);
        }
        //haven't finished yet
        return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST,"Datos incorrectos, revisar datos"),HttpStatus.BAD_REQUEST);
    }



    public Collection<String> getRoles(Collection<Role> roles) {
        Collection<String> aux_roles = new ArrayList<>();
        if (roles.size() > 0) {
            roles.stream().forEach(role -> {
                aux_roles.add(role.getName());
            });
        }
        return aux_roles;
    }


}
