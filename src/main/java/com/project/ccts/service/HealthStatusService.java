package com.project.ccts.service;

import com.project.ccts.model.HealthStatus;
import com.project.ccts.repository.HealthStatusRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HealthStatusService extends AbstractCrud<HealthStatus, Long> {

    private HealthStatusRepository healthStatusRepository;

    @Autowired
    public void setHealthStatusRepository(HealthStatusRepository healthStatusRepository) {
        this.healthStatusRepository = healthStatusRepository;
    }

    @Override
    public JpaRepository<HealthStatus, Long> getDao() {
        return healthStatusRepository;
    }
}
