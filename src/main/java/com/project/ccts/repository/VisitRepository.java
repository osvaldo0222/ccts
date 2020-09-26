package com.project.ccts.repository;

import com.project.ccts.model.entities.Person;
import com.project.ccts.model.entities.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    Page<Visit> findByPersonOrderByTimeLeftDesc(Person person,  Pageable pageable);

    @Query(value = "SELECT v FROM Visit v WHERE v.person = ?1 AND lower(v.locality.name) LIKE lower(concat('%', ?2,'%')) ORDER BY v.timeLeft DESC")
    Page<Visit> customVisitSearch(Person person, String search, Pageable pageable);

    /**?
     *Must be changed to Creation time for production, just test time now
     * @param creationTime
     * @param person
     * @return
     */
     Collection<Visit> findAllByTimeArrivedAfterAndPerson(LocalDateTime creationTime,Person person);
    @Query(value = "SELECT v FROM Visit v WHERE v.timeArrived BETWEEN :timeArrived AND :timeLeft OR v.timeLeft BETWEEN :timeArrived AND :timeLeft OR v.timeArrived <= :timeArrived AND v.timeLeft >= :timeLeft")
     Collection<Visit> findAllByTimeArrivedBetweenOrTimeLeftBetween(LocalDateTime timeArrived,LocalDateTime timeLeft);


}
