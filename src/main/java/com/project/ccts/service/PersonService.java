package com.project.ccts.service;

import com.project.ccts.model.entities.HealthStatus;
import com.project.ccts.model.entities.Person;
import com.project.ccts.repository.PersonRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PersonService extends AbstractCrud<Person, Long> {

    private PersonRepository personRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public JpaRepository<Person, Long> getDao() {
        return personRepository;
    }

    public Person findPersonByPersonalIdentifier(String personalIdentifier) {
        return personRepository.findByPersonalIdentifier(personalIdentifier);
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public Person findByUuid(UUID uuid) { return personRepository.findByUserCredentialUuid(uuid); }

    public Boolean getPersonTestStatus(Person person) {
        boolean status;
        Long listLength = person.getStatus().stream().count();
        if (listLength <= 0) {
            status = false;
        } else {
            HealthStatus healthStatus = person.getStatus().stream().skip(listLength - 1).findFirst().get();
            if (healthStatus == null) {
                status = false;
            } else {
                status = healthStatus.getTest() == null ? false : healthStatus.getTest().getStatus() ;
            }
        }
        return status;
    }
}
