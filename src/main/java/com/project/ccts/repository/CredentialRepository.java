package com.project.ccts.repository;

import com.project.ccts.model.entities.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByUsername(String username);

}
