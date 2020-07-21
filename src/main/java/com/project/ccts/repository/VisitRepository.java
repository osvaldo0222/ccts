package com.project.ccts.repository;

import com.project.ccts.model.entities.Person;
import com.project.ccts.model.entities.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    Page<Visit> findByPersonOrderByTimeLeftDesc(Person person,  Pageable pageable);

    @Query(value = "SELECT v FROM Visit v WHERE v.person = ?1 AND lower(v.locality.name) LIKE lower(concat('%', ?2,'%')) ORDER BY v.timeLeft DESC")
    Page<Visit> customVisitSearch(Person person, String search, Pageable pageable);

}
