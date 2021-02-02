package com.project.ccts.repository;

import com.project.ccts.model.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Province findByProvinceName(String provinceName);

}
