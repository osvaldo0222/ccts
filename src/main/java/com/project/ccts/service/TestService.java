package com.project.ccts.service;

import com.project.ccts.model.Test;
import com.project.ccts.repository.TestRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestService extends AbstractCrud<Test, Long> {

    private TestRepository testRepository;

    @Autowired
    public void setTestRepository(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public JpaRepository<Test, Long> getDao() {
        return testRepository;
    }
}
