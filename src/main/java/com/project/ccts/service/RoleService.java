package com.project.ccts.service;

import com.project.ccts.dto.UpdateAdminPrivilegesDTO;
import com.project.ccts.model.entities.Privilege;
import com.project.ccts.model.entities.Role;
import com.project.ccts.model.entities.UserCredential;
import com.project.ccts.repository.RoleRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public Role createRoleIfNotFound(String role_user, Collection<Privilege> privileges) {
        Role role = findByName(role_user);
        if (role == null) {
            role = new Role(role_user);
            role.setPrivileges(privileges);
            role = createOrUpdate(role);
        }
        return role;
    }
    public Collection<Role> rolesToUpdate(UserCredential userCredential, UpdateAdminPrivilegesDTO privilegesDTO){
        Collection<String> strings = new ArrayList<>();
        userCredential.getRoles().stream().forEach(role -> {
            strings.add(role.getName());
        });
        userCredential.getRoles().removeAll(userCredential.getRoles());
        Collection<Role> roles = new ArrayList<>();
        privilegesDTO.getTags().stream().forEach(s -> {
            Role role = findByName(s.toUpperCase());
            roles.add(role);
        });
        return roles;
    }

}

