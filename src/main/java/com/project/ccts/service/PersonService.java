package com.project.ccts.service;

import com.project.ccts.model.Person;
import com.project.ccts.repository.PersonRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
