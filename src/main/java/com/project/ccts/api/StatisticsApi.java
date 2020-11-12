package com.project.ccts.api;


import com.project.ccts.dto.CustomResponseObjectDTO;
import com.project.ccts.dto.mobileStatistics.GenericXYDTO;
import com.project.ccts.model.entities.GlobalStatistics;
import com.project.ccts.service.GlobalStatisticsService;
import com.project.ccts.service.HealthStatusService;
import jdk.jfr.SettingDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;

import static com.project.ccts.dto.CustomResponseObjectUtil.createResponse;
@RestController
@RequestMapping("api/statistics")
public class StatisticsApi {
    private GlobalStatisticsService globalStatisticsService;
    private HealthStatusService healthStatusService;
    @Autowired
    public void setGlobalStatisticsService(GlobalStatisticsService globalStatisticsService) {
        this.globalStatisticsService = globalStatisticsService;
    }
    @Autowired
    public void setHealthStatusService(HealthStatusService healthStatusService) {
        this.healthStatusService = healthStatusService;
    }

    @GetMapping(path = "dashboard",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> globalStatistics() throws JSONException {
        return new ResponseEntity<>(createResponse(HttpStatus.OK, globalStatisticsService.findTopByOrderByDateDesc()), HttpStatus.OK);
    }
    @GetMapping(path = "infected/{time}",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getInfectedByTimeIntervalFromCurrentDate(@PathVariable("time") Integer time){
        Pageable pageable = PageRequest.of(0,time);
        Collection<GenericXYDTO> globalStatistics = globalStatisticsService.findAllCasesByDateAndLimit(pageable);
        return new ResponseEntity<>(createResponse(HttpStatus.OK,globalStatistics),HttpStatus.OK);
    }
    @GetMapping(path = "new/infected/{time}",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getNewInfectedByTimeIntervalFromCurrentDate(@PathVariable("time") Integer time){
        Pageable pageable = PageRequest.of(0,time);
        Collection<GenericXYDTO> globalStatistics = globalStatisticsService.findAllNewCasesByDateAndLimit(pageable);
        return new ResponseEntity<>(createResponse(HttpStatus.OK,globalStatistics),HttpStatus.OK);
    }
    @GetMapping(path = "recovered/{time}",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> findAllRecoveredByDateAndLimit(@PathVariable("time") Integer time){
        Pageable pageable = PageRequest.of(0,time);
        Collection<GenericXYDTO> globalStatistics = globalStatisticsService.findAllRecoveredByDateAndLimit(pageable);
        return new ResponseEntity<>(createResponse(HttpStatus.OK,globalStatistics),HttpStatus.OK);
    }
    @GetMapping(path = "death/{time}",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getDeathByTimeIntervalFromCurrentDate(@PathVariable("time") Integer time){
        Pageable pageable = PageRequest.of(0,time);
        Collection<GenericXYDTO> globalStatistics = globalStatisticsService.findAllDeathsByDateAndLimit(pageable);
        return new ResponseEntity<>(createResponse(HttpStatus.OK,globalStatistics),HttpStatus.OK);
    }
    @GetMapping(path = "new/death/{time}",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getNewDeathByTimeIntervalFromCurrentDate(@PathVariable("time") Integer time){
        Pageable pageable = PageRequest.of(0,time);
        Collection<GenericXYDTO> globalStatistics = globalStatisticsService.findAllNewDeathsByDateAndLimit(pageable);
        return new ResponseEntity<>(createResponse(HttpStatus.OK,globalStatistics),HttpStatus.OK);
    }






}
