package com.project.ccts.service.security;

import com.project.ccts.model.Credential;
import com.project.ccts.model.Privilege;
import com.project.ccts.model.Role;
import com.project.ccts.repository.CredentialRepository;
import com.project.ccts.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Interface for user validation in the database.
 * This also defines the roles of the user who is being authenticated.
 *
 */
@Service
@Transactional
public class SecurityService implements UserDetailsService {

    private CredentialRepository credentialRepository;

    @Autowired
    public void setCredentialRepository(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential credential = credentialRepository.findByUsername(username);

        if (credential == null) {
            Logger.getInstance().getLog(this.getClass()).warn("Username " + username + " not found!!");
            throw new UsernameNotFoundException("Username not fount!!!");
        }

        System.out.println(username);

        return new org.springframework.security.core.userdetails.User(credential.getUsername(), credential.getPassword(), credential.getAuthenticated(), true, true, true, getAuthorities(credential.getRoles()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}

