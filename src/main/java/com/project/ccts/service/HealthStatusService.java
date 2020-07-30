package com.project.ccts.service;

import com.project.ccts.dto.PersonHealthStatusUpdateDTO;
import com.project.ccts.model.entities.HealthStatus;
import com.project.ccts.model.entities.Person;
import com.project.ccts.repository.HealthStatusRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HealthStatusService extends AbstractCrud<HealthStatus, Long> {

    private HealthStatusRepository healthStatusRepository;
    private PersonService personService;

    @Autowired
    public void setHealthStatusRepository(HealthStatusRepository healthStatusRepository) {
        this.healthStatusRepository = healthStatusRepository;
    }

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public JpaRepository<HealthStatus, Long> getDao() {
        return healthStatusRepository;
    }
}
