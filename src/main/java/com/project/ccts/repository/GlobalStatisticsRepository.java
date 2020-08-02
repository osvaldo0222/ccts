package com.project.ccts.repository;

import com.project.ccts.model.entities.GlobalStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface GlobalStatisticsRepository extends JpaRepository<GlobalStatistics,Long> {
    @Query("SELECT u FROM GlobalStatistics u WHERE u.date = :date")
    GlobalStatistics findGlobalStatisticsByDate(LocalDate date);
    @Query("SELECT u.date,u.cases.total FROM GlobalStatistics u ORDER BY u.date DESC ")
    Page<Object> findAllCasesByDateAndLimit(Pageable pageable);
    @Query("SELECT u.date,u.deaths.total FROM GlobalStatistics u ORDER BY u.date DESC ")
    Page<Object> findAllDeathsByDateAndLimit(Pageable pageable);
    @Query("SELECT u.date,u.deaths.New FROM GlobalStatistics u ORDER BY u.date DESC ")
    Page<Object> findAllNewDeathsByDateAndLimit(Pageable pageable);
    @Query("SELECT u.date,u.cases.New FROM GlobalStatistics u ORDER BY u.date DESC ")
    Page<Object> findAllNewCasesByDateAndLimit(Pageable pageable);
    @Query("SELECT u.date,u.cases.recovered FROM GlobalStatistics u ORDER BY u.date DESC ")
    Page<Object> findAllRecoveredByDateAndLimit(Pageable pageable);
}
