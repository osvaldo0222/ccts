package com.project.ccts.service.security;

import com.project.ccts.model.entities.Credential;
import com.project.ccts.model.entities.Privilege;
import com.project.ccts.model.entities.Role;
import com.project.ccts.repository.CredentialRepository;
import com.project.ccts.repository.PersonRepository;
import com.project.ccts.util.logger.Logger;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Interface for user validation in the database.
 * This also defines the roles of the user who is being authenticated.
 *
 */
@Service
@Transactional
public class SecurityService implements UserDetailsService {

    private CredentialRepository credentialRepository;
    private PersonRepository personRepository;

    @Autowired
    public void setCredentialRepository(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * User details implementation function that validate the user when try to login
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Finding the user based on the username and email
        Credential credential = credentialRepository.findByUsername(username);
        credential = credential == null ? personRepository.findByEmail(username).getUserCredential() : credential;

        //Validating the user
        if (credential == null) {
            String message = String.format("Username %s not found!!", username);
            Logger.getInstance().getLog(getClass()).warn(message);
            throw new UsernameNotFoundException(message);
        }

        //Returning a new UserDetails for Spring management with the authorities, username, password...
        return new org.springframework.security.core.userdetails.User(credential.getUsername(),
                credential.getPassword(),
                credential.getAuthenticated(),
                true,
                true,
                true,
                getAuthorities(credential.getRoles())
        );
    }

    /**
     * Custom function to validate the username and roles that comes with the token JWT.
     *
     * @param username
     * @return Collection of Granted Authorities
     */
    public Collection<? extends GrantedAuthority> getAuthorities(String username) {
        Credential credential = credentialRepository.findByUsername(username);

        if (credential == null) {
            throw new JwtException(String.format("Username %s is no part of the system!!!!!", username));
        }

        return getAuthorities(credential.getRoles());
    }

    /**
     * Function to extract all the authorities based on the roles.
     *
     * @param roles
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    /**
     * Function to extract all the privileges of a collection of roles.
     *
     * @param roles
     * @return
     */
    private Collection<String> getPrivileges(Collection<Role> roles) {
        return roles.stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .map(Privilege::getName)
                .collect(Collectors.toList());
    }

    /**
     * Function that parse all the privileges names to Granted Authority object for Spring management.
     *
     * @param privileges
     * @return
     */
    private Collection<? extends GrantedAuthority> getGrantedAuthorities(Collection<String> privileges) {
        return privileges.stream()
                .map(s -> new SimpleGrantedAuthority(s))
                .collect(Collectors.toList());
    }
}

