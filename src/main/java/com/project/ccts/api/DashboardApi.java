package com.project.ccts.api;

import com.project.ccts.dto.LocalityDTO;
import com.project.ccts.model.Locality;
import com.project.ccts.service.LocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("api/dashboard")
public class DashboardApi {
    private LocalityService localityService;
    @Autowired
    public void setLocalityService(LocalityService localityService) {
        this.localityService = localityService;
    }
    @GetMapping(path = "node_distribution", produces = "application/json")
    public ResponseEntity<Collection<LocalityDTO>> nodeDistributionByLocation(){
        Collection<Locality> locality = localityService.findAll();
        Collection<LocalityDTO> localityDTOS = new ArrayList<>();
        locality.stream().forEach((aux_locality)-> {
            localityDTOS.add(new LocalityDTO(aux_locality.getId(),aux_locality.getName(),aux_locality.getAddress(),aux_locality.getVisits().size()));
        });
        return new ResponseEntity<>(localityDTOS, HttpStatus.OK);
    }
}
