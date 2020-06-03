package com.project.ccts.service;

import com.project.ccts.model.Role;
import com.project.ccts.repository.RoleRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.service.common.ICrudOperation;
import com.project.ccts.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class RoleService extends AbstractCrud<Role, Long> {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public JpaRepository<Role, Long> getDao() {
        return roleRepository;
    }

    public Role findByName(String name) { return roleRepository.findByName(name); }
}

