package com.project.ccts.service;

import com.project.ccts.model.entities.Visit;
import com.project.ccts.repository.VisitRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VisitService extends AbstractCrud<Visit, Long> {

    private VisitRepository visitRepository;

    @Autowired
    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public JpaRepository<Visit, Long> getDao() {
        return visitRepository;
    }
}
