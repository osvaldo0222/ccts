package com.project.ccts.task;

import com.project.ccts.service.ProjectStatisticsService;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LocalStatisticsTask {

    private final ProjectStatisticsService projectStatisticsService;

    @Autowired
    public LocalStatisticsTask(ProjectStatisticsService projectStatisticsService) {
        this.projectStatisticsService = projectStatisticsService;
    }

    @Scheduled(cron="0 0 0 * * *")
    public void localStatisticsCheck() {
        Logger.getInstance().getLog(getClass()).info("Checking today's project statistics...");
        projectStatisticsService.checkTodayStatistics();
        Logger.getInstance().getLog(getClass()).info("Today's project statistics ready!!");
    }
}
