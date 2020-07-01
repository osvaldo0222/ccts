package com.project.ccts.repository;

import com.project.ccts.model.entities.HealthStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {

}
