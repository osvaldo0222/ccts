package com.project.ccts.service;

import com.project.ccts.model.Role;
import com.project.ccts.repository.RoleRepository;
import com.project.ccts.service.common.ICrudOperation;
import com.project.ccts.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class RoleService implements ICrudOperation<Role, Long> {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createOrUpdate(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            roleRepository.delete(role);
        } else {
            Logger.getInstance().getLog(this.getClass()).error("This role not exists...!");
        }
    }

    @Override
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role findByName(String name) { return roleRepository.findByName(name); }
}

