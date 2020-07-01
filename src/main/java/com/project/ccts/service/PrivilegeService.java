package com.project.ccts.service;

import com.project.ccts.model.entities.Privilege;
import com.project.ccts.repository.PrivilegeRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrivilegeService extends AbstractCrud<Privilege, Long> {

    private PrivilegeRepository privilegeRepository;

    @Autowired
    public void setPrivilegeRepository(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public JpaRepository<Privilege, Long> getDao() {
        return privilegeRepository;
    }

    public Privilege findByName(String name) { return privilegeRepository.findByName(name); }
}

