package com.project.ccts.service;

import com.project.ccts.model.Privilege;
import com.project.ccts.repository.PrivilegeRepository;
import com.project.ccts.service.common.ICrudOperation;
import com.project.ccts.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class PrivilegeService implements ICrudOperation<Privilege, Long> {

    private PrivilegeRepository privilegeRepository;

    @Autowired
    public void setPrivilegeRepository(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Privilege createOrUpdate(Privilege entity) {
        return privilegeRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Privilege privilege = privilegeRepository.findById(id).orElse(null);
        if (privilege != null) {
            privilegeRepository.delete(privilege);
        } else {
            Logger.getInstance().getLog(this.getClass()).error("This privilege not exists...!");
        }

    }

    @Override
    public Collection<Privilege> findAll() {
        return privilegeRepository.findAll();
    }

    @Override
    public Privilege findById(Long id) {
        return privilegeRepository.findById(id).orElse(null);
    }

    public Privilege findByName(String name) { return privilegeRepository.findByName(name); }
}

