package com.project.ccts.service;

import com.project.ccts.model.entities.GlobalStatistics;
import com.project.ccts.model.entities.SupplementaryDataStatistics.NewCase;
import com.project.ccts.model.entities.SupplementaryDataStatistics.NewDeath;
import com.project.ccts.model.entities.SupplementaryDataStatistics.NewTests;
import com.project.ccts.repository.GlobalStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

@Service
@Transactional
public class GlobalStatisticsService extends AbstractCrud<GlobalStatistics,Long> {
    private GlobalStatisticsRepository globalStatisticsRepository;
    @Autowired
    public void setGlobalStatisticsRepository(GlobalStatisticsRepository globalStatisticsRepository) {
        this.globalStatisticsRepository = globalStatisticsRepository;
    }

    @Override
    public JpaRepository<GlobalStatistics, Long> getDao() {
        return globalStatisticsRepository;
    }

    public GlobalStatistics getGlobalStatisticsByDate(LocalDate date){
        return globalStatisticsRepository.findGlobalStatisticsByDate(date);
    }
    public GlobalStatistics createGlobalStatisticsObject(JSONArray jsonArray, String[] date) throws JSONException {
            JSONObject object = jsonArray.getJSONObject(0);
            JSONObject cases = object.getJSONObject("cases");
            JSONObject deaths = object.getJSONObject("deaths");
            JSONObject test = object.getJSONObject("tests");

            NewCase casesAux = new NewCase(cases.get("new").toString(),isStringInt(cases.get("active").toString()),
                    isStringInt(cases.get("critical").toString()),isStringInt(cases.get("recovered").toString()),
                    isStringInt(cases.get("total").toString()));

            NewDeath deathsAux = new NewDeath(deaths.get("new").toString(),isStringInt((deaths.get("total").toString())));

            NewTests testAux = new NewTests(isStringInt(test.get("total").toString()));
             GlobalStatistics global = new GlobalStatistics(object.getString("country"),casesAux,deathsAux,testAux, LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),Integer.parseInt(date[2])));
        return global;
    }
    public Collection<GlobalStatistics> createGlobalStatisticsHistory(JSONArray jsonArray) throws JSONException {
        Collection<GlobalStatistics> globalStatistics = new ArrayList<>();
        for (int i = 0;i<jsonArray.length();i++){
            String[] date = jsonArray.getJSONObject(i).getString("day").split("-");
            boolean x = false;
            if (i>0){
                String[] dateBefore = jsonArray.getJSONObject(i-1).getString("day").split("-");
                if (!Arrays.equals(date,dateBefore)){
                    JSONArray array = new JSONArray();
                    array.put(jsonArray.get(i));
                    GlobalStatistics globalStatisticsAux = createGlobalStatisticsObject(array,date);
                    globalStatistics.add(globalStatisticsAux);
                }
            }
         }
        return globalStatistics;
    }
    public JSONArray prepareRapidApiRequest(String url) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host","covid-193.p.rapidapi.com");
        headers.set("x-rapidapi-key","63f324cd41msh82695c80c85d8dbp185b5ajsnaf98b9aa4407");
        headers.set("useQueryString", String.valueOf(true));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<String> entity = new HttpEntity<String>("",headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,entity,String.class);
        if (responseEntity.getStatusCode().value() == 200){
            JSONArray jsonArray = new JSONObject(responseEntity.getBody()).getJSONArray("response");
            return jsonArray;
        }
        return null;
    }
    public Integer isStringInt(String s){
        try {
            return Integer.parseInt(s);
        }catch (NumberFormatException e){
            return 0;
        }
    }
}
