package com.project.ccts.service;

import com.project.ccts.model.Credential;
import com.project.ccts.repository.CredentialRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CredentialService extends AbstractCrud<Credential, Long> {

    private CredentialRepository credentialRepository;

    @Autowired
    public void setCredentialRepository(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public JpaRepository<Credential, Long> getDao() {
        return credentialRepository;
    }

    public Credential findByUsername(String username) { return credentialRepository.findByUsername(username); }
}
