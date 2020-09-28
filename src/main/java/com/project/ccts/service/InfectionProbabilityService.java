package com.project.ccts.service;

import com.project.ccts.model.entities.Person;
import com.project.ccts.model.entities.Visit;
import com.project.ccts.model.helpers.InfectionProbability;
import com.project.ccts.repository.InfectionProbabilityRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class InfectionProbabilityService extends AbstractCrud<InfectionProbability,Long> {
    private InfectionProbabilityRepository infectionProbabilityRepository;
    private VisitService visitService;
    private PersonService personService;

    @Autowired
    public void setInfectionProbabilityRepository(InfectionProbabilityRepository infectionProbabilityRepository) {
        this.infectionProbabilityRepository = infectionProbabilityRepository;
    }

    @Override
    public JpaRepository<InfectionProbability, Long> getDao() {
        return infectionProbabilityRepository;
    }

}
