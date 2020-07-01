package com.project.ccts.service;

import com.project.ccts.model.entities.Role;
import com.project.ccts.repository.RoleRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

