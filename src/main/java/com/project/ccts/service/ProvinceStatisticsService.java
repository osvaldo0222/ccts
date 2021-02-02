package com.project.ccts.service;

import com.project.ccts.dto.ProvinceStatisticsDTO;
import com.project.ccts.model.entities.Province;
import com.project.ccts.model.entities.ProvinceStatistics;
import com.project.ccts.repository.ProvinceStatisticsRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProvinceStatisticsService extends AbstractCrud<ProvinceStatistics, Long> {

    private ProvinceStatisticsRepository statisticsRepository;
    private ProvinceService provinceService;

    @Autowired
    public void setStatisticsRepository(ProvinceStatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @Override
    public JpaRepository<ProvinceStatistics, Long> getDao() {
        return statisticsRepository;
    }

    public JSONArray getDataFromSheets(String tab, String filterName, String filterValue) throws JSONException {
        String url = String.format("https://sheet.best/api/sheets/8e37231c-d086-49f0-8672-4f3e290401ca/tabs/%s", tab);
        if (filterName != null && filterValue != null && filterName != "" && filterValue != "") {
            url += String.format("/search?%s=%s", filterName, filterValue);
        }
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<String> entity = new HttpEntity<>("", new HttpHeaders());
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        if (responseEntity.getStatusCode().value() == 200){
            JSONArray jsonArray = new JSONArray(responseEntity.getBody());
            Map<String, Province> provinceMap = new HashMap<>();
            JSONObject temp = jsonArray.getJSONObject(0);
            for (int i = 0; i < temp.names().length(); i++) {
                if (temp.names().get(i).toString().equalsIgnoreCase("Fecha") || temp.names().get(i).toString().equalsIgnoreCase("Número de Boletín") || temp.names().get(i).toString().equalsIgnoreCase("RD_total") || temp.names().get(i).toString().contains("_Coef")) {
                    continue;
                } else {
                    Province province = provinceService.findByProvinceName(temp.names().get(i).toString());
                    if (province == null) {
                        province = provinceService.createOrUpdate(new Province(temp.names().get(i).toString()));
                    }
                    provinceMap.put(temp.names().get(i).toString(), province);
                }
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String[] dateSplit = object.get("Fecha").toString().split("/");
                LocalDate date = LocalDate.of(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]));
                for (Map.Entry<String, Province> entry : provinceMap.entrySet()) {
                    ProvinceStatistics provinceStatistics = findByDateAndProvince(date, entry.getValue());
                    if (provinceStatistics == null) {
                        provinceStatistics = new ProvinceStatistics();
                    }
                    provinceStatistics.setDate(date);
                    provinceStatistics.setDeaths(tab.equalsIgnoreCase("Defunciones") ? object.getLong(entry.getKey()) : provinceStatistics.getDeaths());
                    provinceStatistics.setInfected(tab.equalsIgnoreCase("Casos-y-R0") ? object.getLong(entry.getKey()) : provinceStatistics.getInfected());
                    provinceStatistics.setRecovered(tab.equalsIgnoreCase("Recuperados") ? object.getLong(entry.getKey()) : provinceStatistics.getRecovered());
                    provinceStatistics.setProvince(entry.getValue());
                    createOrUpdate(provinceStatistics);
                }
            }
            Logger.getInstance().getLog(this.getClass()).info(tab + " by province loaded [...]");
        }
        return null;
    }

    public ProvinceStatistics findByDateAndProvince(LocalDate date, Province province) {
        return statisticsRepository.findByDateAndProvince(date, province);
    }

    public List<ProvinceStatistics> findByDate(LocalDate date) {
        return statisticsRepository.findByDate(date);
    }

    public List<ProvinceStatisticsDTO> getLastProvinceStatistics() {
        List<ProvinceStatistics> provincesStatistics = null;
        List<ProvinceStatisticsDTO> statisticsDTOS = new ArrayList<>();
        LocalDate date = LocalDate.now();
        while (provincesStatistics == null || provincesStatistics.size() == 0) {
            provincesStatistics = findByDate(date);
            date = date.minusDays(1);
        }
        for (ProvinceStatistics statistics : provincesStatistics) {
            statisticsDTOS.add(new ProvinceStatisticsDTO(statistics.getProvince().getProvinceName(), statistics.getInfected(), statistics.getDeaths(), statistics.getRecovered(), statistics.getDate()));
        }
        return statisticsDTOS;
    }
}
