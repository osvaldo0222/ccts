package com.project.ccts.service;

import com.project.ccts.model.entities.Person;
import com.project.ccts.model.entities.PersonAndKInfectors;
import com.project.ccts.repository.PersonAndKInfectorRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonAndKInfectorService extends AbstractCrud<PersonAndKInfectors,Long> {

    private PersonAndKInfectorRepository personAndKInfectorRepository;

    @Autowired
    public void setPersonAndKInfectorRepository(PersonAndKInfectorRepository personAndKInfectorRepository) {
        this.personAndKInfectorRepository = personAndKInfectorRepository;
    }

    @Override
    public JpaRepository<PersonAndKInfectors, Long> getDao() {
        return personAndKInfectorRepository;
    }

    public PersonAndKInfectors findTopByPersonOrderByDateDesc(Person person) {
        return personAndKInfectorRepository.findTopByPersonOrderByDateDesc(person);
    }
}
