package com.project.ccts.api;

import com.project.ccts.dto.*;
import com.project.ccts.model.entities.*;
import com.project.ccts.service.CredentialService;
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

    @GetMapping(path = "node-distribution", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> nodeDistributionByLocation() {
        Collection<Locality> locality = localityService.findAll();
        if (locality != null){
            return new ResponseEntity<>(createResponse(HttpStatus.OK,createLocalityDTO(locality)), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST,"No existen localidades Registradas"), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "listLocations", produces = "application/json")
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

    @GetMapping(path = "locality/{id}", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> localityInfo(@PathVariable Long id) throws Exception {
        Locality locality = localityService.findById(id);
        Collection<BeaconDetailsDTO> beaconDetailsDTOS = new ArrayList<>();
        locality.getBeacons().stream().forEach(beacon ->
                beaconDetailsDTOS.add(new BeaconDetailsDTO(beacon.getId(), beacon.getInstance(), beacon.getBeaconCredential().getStatus())));
        beaconDetailsDTOS.stream().forEach(beaconDetailsDTO -> System.out.println(beaconDetailsDTO.toString()));
        LocalityDetailsDTO localityDetailsDTO = new LocalityDetailsDTO(locality.getId(), locality.getName(), locality.getAddress(),
                locality.getEmail(), locality.getCellPhone(), beaconDetailsDTOS);
        return new ResponseEntity<>(createResponse(HttpStatus.OK,localityDetailsDTO), HttpStatus.OK);
    }

    @GetMapping(path = "node-distribution/containing/{keyword}", produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> findNodeDistributionByNameContaining(@PathVariable String keyword) {
        Collection<Locality> locality = localityService.findByNameContaining(keyword);
        if(locality != null){
            return new ResponseEntity<>(createResponse(HttpStatus.OK,createLocalityDTO(locality)), HttpStatus.OK);

        }else {
            return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST,"No existen usuarios "), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping(path = "covid-test/findPatientById/{id}", produces = "application/json")
    public ResponseEntity<PersonDTO> getPatientDetailsByPersonalIdentifier(@PathVariable String id) {
        boolean status;
        Person person = personService.findPersonByPersonalIdentifier(id);
        PersonDTO personDTO = new PersonDTO();
        if (person != null) {
            Long listLength = person.getStatus().stream().count();
            if (listLength <= 0) {
                status = false;
            } else {
                HealthStatus healthStatus = person.getStatus().stream().skip(listLength - 1).findFirst().get();
                if (healthStatus == null) {
                    status = false;
                } else {
                    status = healthStatus.getTest().getStatus();
                }
            }
            personDTO = new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress()
                    , person.getOccupation(), Period.between(person.getBirthDate(), LocalDate.now()).getYears(), status, person.getEmail());
        }

        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }


    @GetMapping(path = "listUsers", produces = "application/json")
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
            person.addHealthStatus(healthStatus);
            personService.createOrUpdate(person);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, "Su solicitud ha sido satisfactoria, Perfil del paciente Actualizado"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(createResponse(HttpStatus.NOT_FOUND, "Revise los datos ingresados en el formulario"), HttpStatus.NOT_FOUND);
        }

    }


    public Collection<LocalityDTO> createLocalityDTO(Collection<Locality> locality) {
        Collection<LocalityDTO> localityDTOS = new ArrayList<>();
        locality.stream().forEach((aux_locality) -> {
            localityDTOS.add(new LocalityDTO(aux_locality.getId(), aux_locality.getName(), aux_locality.getAddress().getCity(),
                    aux_locality.getVisits().size(), aux_locality.getBeacons().size()));
        });
        return localityDTOS;
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
