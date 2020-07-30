package com.project.ccts.repository;

import com.project.ccts.model.entities.HealthStatus;
import com.project.ccts.model.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {

    Page<HealthStatus> findByPersonOrderByStatusDateDesc(Person person, Pageable pageable);
}
