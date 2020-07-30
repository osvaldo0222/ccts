package com.project.ccts.repository;

import com.project.ccts.model.entities.GlobalStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface GlobalStatisticsRepository extends JpaRepository<GlobalStatistics,Long> {
    @Query("SELECT u FROM GlobalStatistics u WHERE u.date = :date")
    GlobalStatistics findGlobalStatisticsByDate
            (LocalDate date);
}
