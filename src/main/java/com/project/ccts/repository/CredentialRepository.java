package com.project.ccts.repository;

import com.project.ccts.model.Credential;
import com.project.ccts.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByUsername(String username);

}
