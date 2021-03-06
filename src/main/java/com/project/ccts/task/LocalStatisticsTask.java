package com.project.ccts.task;

import com.project.ccts.model.entities.GlobalStatistics;
import com.project.ccts.service.GlobalStatisticsService;
import com.project.ccts.service.ProjectStatisticsService;
import com.project.ccts.service.ProvinceStatisticsService;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalStatisticsTask {

    private final ProjectStatisticsService projectStatisticsService;
    private GlobalStatisticsService globalStatisticsService;
    private ProvinceStatisticsService provinceStatisticsService;

    @Autowired
    public LocalStatisticsTask(ProjectStatisticsService projectStatisticsService, GlobalStatisticsService globalStatisticsService, ProvinceStatisticsService provinceStatisticsService) {
        this.projectStatisticsService = projectStatisticsService;
        this.globalStatisticsService = globalStatisticsService;
        this.provinceStatisticsService = provinceStatisticsService;
    }

    @Scheduled(cron="0 0 0 * * *")
    public void localStatisticsCheck() {
        Logger.getInstance().getLog(getClass()).info("Checking today's project statistics...");
        projectStatisticsService.checkTodayStatistics();
        Logger.getInstance().getLog(getClass()).info("Today's project statistics ready!!");
    }

    @Scheduled(initialDelay = 20000, fixedDelay = 1800000)
    public void globalStatisticsCheck() throws JSONException {
        JSONArray jsonArray = globalStatisticsService.prepareRapidApiRequest("https://covid-193.p.rapidapi.com/statistics?country=Dominican-Republic");
        String[] date = jsonArray.getJSONObject(0).getString("day").split("-");
        GlobalStatistics globalStatistics = globalStatisticsService.getGlobalStatisticsByDate(LocalDate.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2])));
        if (globalStatistics == null){
            globalStatistics = globalStatisticsService.createGlobalStatisticsObject(jsonArray,date);
            globalStatisticsService.createOrUpdate(globalStatistics);
            Logger.getInstance().getLog(getClass()).info("Updating today's COVID statistics...");
        }
    }

    @Scheduled(initialDelay = 43200000, fixedDelay = 43200000)
    public void provinceStatisticsUpdate() throws JSONException {
        Logger.getInstance().getLog(getClass()).info("Checking today's provinces statistics...");
        provinceStatisticsService.getDataFromSheets("Casos y R0", "Fecha", String.format("*/%s/%s", LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
        provinceStatisticsService.getDataFromSheets("Defunciones", "Fecha", String.format("*/%s/%s", LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
    }
}
