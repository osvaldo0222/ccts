package com.project.ccts.service;

import com.project.ccts.model.entities.Institution;
import com.project.ccts.model.entities.UserCredential;
import com.project.ccts.model.enums.InstitutionType;
import com.project.ccts.repository.InstitutionRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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

    public Collection<Institution> getInstitutionByType(InstitutionType type){
        Collection<Institution> institutions = institutionRepository.findByType(type);
        return institutions;
    }
    public Institution findByTypeAndNameAndId(InstitutionType type,String name){
        return institutionRepository.findByTypeAndName(type,name);
    }
    public Institution findByNameEmailType( String name, String email,InstitutionType type){
        return institutionRepository.findByNameAndEmailAndType(name,email,type);
    }

}
