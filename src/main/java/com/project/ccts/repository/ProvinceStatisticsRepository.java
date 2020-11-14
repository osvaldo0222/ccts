package com.project.ccts.repository;

import com.project.ccts.model.entities.Province;
import com.project.ccts.model.entities.ProvinceStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProvinceStatisticsRepository extends JpaRepository<ProvinceStatistics, Long> {

    ProvinceStatistics findByDateAndProvince(LocalDate localDate, Province province);

    List<ProvinceStatistics> findByDate(LocalDate date);

}
