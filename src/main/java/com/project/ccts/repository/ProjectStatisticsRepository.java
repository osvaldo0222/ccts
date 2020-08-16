package com.project.ccts.repository;

import com.project.ccts.model.entities.ProjectStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ProjectStatisticsRepository extends JpaRepository<ProjectStatistics, Long> {

    long count();

    ProjectStatistics findByLocalDate(LocalDate localDate);

    ProjectStatistics findFirstByOrderByLocalDateDesc();

    Page<ProjectStatistics> findAllByOrderByLocalDateDesc(Pageable pageable);

}
