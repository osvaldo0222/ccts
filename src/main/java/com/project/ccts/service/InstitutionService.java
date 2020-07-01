package com.project.ccts.service;

import com.project.ccts.model.entities.Institution;
import com.project.ccts.repository.InstitutionRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InstitutionService extends AbstractCrud<Institution, Long> {

    private InstitutionRepository institutionRepository;

    @Autowired
    public void setInstitutionRepository(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Override
    public JpaRepository<Institution, Long> getDao() {
        return institutionRepository;
    }
}
