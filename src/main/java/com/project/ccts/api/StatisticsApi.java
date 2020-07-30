package com.project.ccts.api;


import com.project.ccts.dto.CustomResponseObjectDTO;
import com.project.ccts.dto.covidSummary.Global;
import com.project.ccts.dto.covidSummary.genericInfo.Cases;
import com.project.ccts.dto.covidSummary.genericInfo.Deaths;
import com.project.ccts.dto.covidSummary.genericInfo.Tests;
import com.project.ccts.model.entities.GlobalStatistics;
import com.project.ccts.service.GlobalStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

import static com.project.ccts.dto.CustomResponseObjectUtil.createResponse;
@RestController
@RequestMapping("api/statistics")
public class StatisticsApi {
    private GlobalStatisticsService globalStatisticsService;
    @Autowired
    public void setGlobalStatisticsService(GlobalStatisticsService globalStatisticsService) {
        this.globalStatisticsService = globalStatisticsService;
    }
    @GetMapping(path = "dashboard",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> globalStatistics() throws JSONException {
        JSONArray jsonArray = globalStatisticsService.prepareRapidApiRequest("https://covid-193.p.rapidapi.com/statistics?country=Dominican-Republic");
        String[] date = jsonArray.getJSONObject(0).getString("day").split("-");
        GlobalStatistics globalStatistics = globalStatisticsService.getGlobalStatisticsByDate(LocalDate.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2])));
        if (globalStatistics == null){
            globalStatistics = globalStatisticsService.createGlobalStatisticsObject(jsonArray,date);
            globalStatisticsService.createOrUpdate(globalStatistics);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.OK,globalStatistics),HttpStatus.OK);
    }




}
