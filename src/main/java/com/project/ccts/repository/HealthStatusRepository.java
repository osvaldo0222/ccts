package com.project.ccts.repository;

import com.project.ccts.model.entities.HealthStatus;
import com.project.ccts.model.entities.Person;
import com.project.ccts.model.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {

    Page<HealthStatus> findByPersonOrderByStatusDateDesc(Person person, Pageable pageable);

    HealthStatus findTopByPersonOrderByStatusDateDesc(Person person);
    @Query("SELECT v FROM HealthStatus v WHERE v.test.status = true")
    Page<HealthStatus> findAllByTestStatusEqualsTrue(Pageable pageable);


}
