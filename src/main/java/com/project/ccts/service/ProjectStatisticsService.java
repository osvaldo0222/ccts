package com.project.ccts.service;

import com.project.ccts.dto.visitSearch.PersonAndKInfectors;
import com.project.ccts.dto.visitSearch.VisitAndTimeShared;
import com.project.ccts.model.entities.HealthStatus;
import com.project.ccts.model.entities.Person;
import com.project.ccts.model.entities.ProjectStatistics;
import com.project.ccts.model.enums.Gender;
import com.project.ccts.repository.ProjectStatisticsRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ProjectStatisticsService extends AbstractCrud<ProjectStatistics, Long> {

    private ProjectStatisticsRepository projectStatisticsRepository;
    private VisitService visitService;

    @Autowired
    public void setProjectStatisticsRepository(ProjectStatisticsRepository projectStatisticsRepository,VisitService visitService) {
        this.projectStatisticsRepository = projectStatisticsRepository;
        this.visitService = visitService;
    }

    @Override
    public JpaRepository<ProjectStatistics, Long> getDao() {
        return projectStatisticsRepository;
    }

    public ProjectStatistics findByDate(LocalDate localDate) {
        return projectStatisticsRepository.findByLocalDate(localDate);
    }

    public Long countProjectStatistics() {
        return projectStatisticsRepository.count();
    }

    public void addRegisteredTest(HealthStatus status) {
        ProjectStatistics projectStatistics = checkTodayStatistics();
        projectStatistics.setRegisteredTest(projectStatistics.getRegisteredTest() + 1);
        if (status.getTest().getStatus()) {
            projectStatistics.setConfirmedCases(projectStatistics.getConfirmedCases() + 1);
            projectStatistics.setConfirmedCasesToday(projectStatistics.getConfirmedCasesToday() + 1);
            if (status.getPerson().getGender().equals(Gender.MALE)) {
                projectStatistics.setMaleInfected(projectStatistics.getMaleInfectedToday() + 1);
                projectStatistics.setMaleInfectedToday(projectStatistics.getMaleInfectedToday() + 1);
            } else {
                projectStatistics.setFemaleInfected(projectStatistics.getFemaleInfected() + 1);
                projectStatistics.setFemaleInfectedToday(projectStatistics.getFemaleInfectedToday() + 1);
            }
        }
        createOrUpdate(projectStatistics);
    }

    public void addMessageReceivedNodes() {
        ProjectStatistics projectStatistics = checkTodayStatistics();
        projectStatistics.setMessageReceivedNodes(projectStatistics.getMessageReceivedNodes() + 1);
        projectStatistics.setMessageReceivedNodesToday(projectStatistics.getMessageReceivedNodesToday() + 1);
        createOrUpdate(projectStatistics);
    }

    public void addNotificationSent() {
        ProjectStatistics projectStatistics = checkTodayStatistics();
        projectStatistics.setNotificationsSent(projectStatistics.getNotificationsSent() + 1);
        projectStatistics.setNotificationsSentToday(projectStatistics.getNotificationsSentToday() + 1);
        createOrUpdate(projectStatistics);
    }

    public void addNodeRegistered() {
        ProjectStatistics projectStatistics = checkTodayStatistics();
        projectStatistics.setNodeRegistered(projectStatistics.getNodeRegistered() + 1);
        createOrUpdate(projectStatistics);
    }

    public ProjectStatistics checkTodayStatistics() {
        ProjectStatistics statistics = projectStatisticsRepository.findFirstByOrderByLocalDateDesc();

        if (statistics == null) {
            statistics =  createOrUpdate(new ProjectStatistics());
        } else {
            if (!statistics.getLocalDate().isEqual(LocalDate.now())) {
                statistics = createOrUpdate(new ProjectStatistics(
                        statistics.getRegisteredTest(),
                        statistics.getConfirmedCases(),
                        0L,
                        statistics.getMessageReceivedNodes(),
                        0L,
                        statistics.getNotificationsSent(),
                        0L,
                        statistics.getMaleInfected(),
                        0L,
                        statistics.getFemaleInfected(),
                        0L,
                        statistics.getNodeRegistered()
                ));
            }
        }
        return statistics;
    }

    public List<ProjectStatistics> getStatistics(Pageable pageable) {
        return projectStatisticsRepository.findAllByOrderByLocalDateDesc(pageable).getContent();
    }



}
