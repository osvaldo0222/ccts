package com.project.ccts.service;

import com.project.ccts.model.entities.Province;
import com.project.ccts.repository.ProvinceRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProvinceService extends AbstractCrud<Province, Long> {

    private ProvinceRepository provinceRepository;

    @Autowired
    public void setProvinceRepository(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public JpaRepository<Province, Long> getDao() {
        return provinceRepository;
    }

    public Province findByProvinceName(String provinceName) {
        return provinceRepository.findByProvinceName(provinceName);
    }
}
