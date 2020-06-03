package com.project.ccts.service;

import com.project.ccts.model.Credential;
import com.project.ccts.repository.CredentialRepository;
import com.project.ccts.service.common.ICrudOperation;
import com.project.ccts.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class CredentialService implements ICrudOperation<Credential, Long> {

    private CredentialRepository credentialRepository;

    @Autowired
    public void setCredentialRepository(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public Credential createOrUpdate(Credential entity) {
        return credentialRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Credential credential = credentialRepository.findById(id).orElse(null);
        if (credential != null) {
            credentialRepository.delete(credential);
        } else {
            Logger.getInstance().getLog(this.getClass()).error("This user ...");
        }
    }

    @Override
    public Collection<Credential> findAll() {
        return credentialRepository.findAll();
    }

    @Override
    public Credential findById(Long id) {
        return credentialRepository.findById(id).orElse(null);
    }

    public Credential findByUsername(String username) { return credentialRepository.findByUsername(username); }
}
