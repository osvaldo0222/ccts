package com.project.ccts.service;

import com.project.ccts.model.entities.PersonAndKInfectors;
import com.project.ccts.repository.PersonAndKInfectorRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

    @Override
    public PersonAndKInfectors createOrUpdate(PersonAndKInfectors entity) {
        return super.createOrUpdate(entity);
    }

    @Override
    public Collection<PersonAndKInfectors> createAll(Collection<PersonAndKInfectors> entities) {
        return super.createAll(entities);
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }

    @Override
    public void delete(PersonAndKInfectors entity) {
        super.delete(entity);
    }

    @Override
    public Collection<PersonAndKInfectors> findAll() {
        return super.findAll();
    }

    @Override
    public PersonAndKInfectors findById(Long aLong) throws Exception {
        return super.findById(aLong);
    }
}
