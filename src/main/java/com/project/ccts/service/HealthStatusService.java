package com.project.ccts.service;

import com.project.ccts.dto.*;

import com.project.ccts.model.entities.*;
import com.project.ccts.model.enums.Recommendations;
import com.project.ccts.repository.HealthStatusRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.exception.CustomApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HealthStatusService extends AbstractCrud<HealthStatus, Long> {

    private HealthStatusRepository healthStatusRepository;
    private PersonAndKInfectorService personAndKInfectorService;
    private CredentialService credentialService;

    @Autowired
    public void setHealthStatusRepository(HealthStatusRepository healthStatusRepository) {
        this.healthStatusRepository = healthStatusRepository;
    }

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Autowired
    public void setPersonAndKInfectorService(PersonAndKInfectorService personAndKInfectorService) {
        this.personAndKInfectorService = personAndKInfectorService;
    }

    @Override
    public JpaRepository<HealthStatus, Long> getDao() {
        return healthStatusRepository;
    }
    public long count(){
        return healthStatusRepository.count();
    }

    public Collection<HealthStatus> findAllPageable(int page){
        if(page > 0){
            return healthStatusRepository.findAllByTestStatusEqualsTrue(PageRequest.of(page-1,5)).getContent();
        }else{
            return healthStatusRepository.findAllByTestStatusEqualsTrue(PageRequest.of(0 ,5)).getContent();

        }
    }

    public HealthStatus findTopByPersonOrderByStatusDateDesc(Person person){
        return healthStatusRepository.findTopByPersonOrderByStatusDateDesc(person);
    }

    public List<HealthStatus> findByPersonOrderByStatusDateDesc(Person person, Pageable pageable) {
        return healthStatusRepository.findByPersonOrderByStatusDateDesc(person, pageable).getContent();
    }

    public List<HealthStatusMobileDTO> getHealthStatus(String username, Integer page, Integer size) throws CustomApiException {
        List<HealthStatus> statuses = findByPersonOrderByStatusDateDesc(
                credentialService.findPersonByUsername(username),
                PageRequest.of(page, size)
        );

        return statuses.stream()
                .map(status -> new HealthStatusMobileDTO(
                        status.getId(),
                        status.getStatusDate().toInstant().atZone(ZoneId.of("UTC-4")).toLocalDateTime(),
                        status.getTest() == null ? false : status.getTest().getStatus(),
                        status.isFever(),
                        status.isCough(),
                        status.isBreathDifficulty(),
                        status.isSoreThroat(),
                        status.isSmellLoss(),
                        status.isTasteLoss(),
                        Recommendations.getRecommendationsByHealthStatus(status)))
                .collect(Collectors.toList());
    }

    public Object addHealthStatusByUsername(String username, HealthStatusMobileAddDTO healthStatusMobileAddDTO) throws CustomApiException {
        Person person = credentialService.findPersonByUsername(username);
        HealthStatus healthStatus = new HealthStatus(
                healthStatusMobileAddDTO.getFever(),
                healthStatusMobileAddDTO.getCough(),
                healthStatusMobileAddDTO.getBreathDifficulty(),
                healthStatusMobileAddDTO.getSoreThroat(),
                healthStatusMobileAddDTO.getSmellLoss(),
                healthStatusMobileAddDTO.getTasteLoss()
        );
        healthStatus.setPerson(person);

        List<HealthStatus> statuses = (List<HealthStatus>) person.getStatus();
        if (statuses != null) {
            Date date = new Date();
            for (HealthStatus status : statuses) {
                if (status.getStatusDate().getDate() == date.getDate() && status.getStatusDate().getDay() == date.getDay() && status.getStatusDate().getMonth() == date.getMonth()) {
                    if (healthStatus.isFever() == status.isFever() &&
                            healthStatus.isCough() == status.isCough() &&
                            healthStatus.isBreathDifficulty() == status.isBreathDifficulty() &&
                            healthStatus.isSoreThroat() == status.isSoreThroat() &&
                            healthStatus.isSmellLoss() == status.isSmellLoss() &&
                            healthStatus.isTasteLoss() == status.isTasteLoss()
                    ) {
                        return "This health status is in the db!";
                    }
                }
            }
        }

        createOrUpdate(healthStatus);
        return "Health status added!";
    }

    public List<ExpositionDetailDTO> getExposition(String username, Integer page, Integer size) throws CustomApiException {
        Person person = credentialService.findPersonByUsername(username);
        List<PersonAndKInfectors> personAndKInfectors = personAndKInfectorService.findByPersonOrderByDateDesc(person, PageRequest.of(page, size));
        List<ExpositionDetailDTO> detailDTOS = new ArrayList<>();

        personAndKInfectors.forEach((e) -> {
            ExpositionDetailDTO detailDTO = new ExpositionDetailDTO();
            detailDTO.setId(e.getId());
            detailDTO.setDate(e.getDate());
            detailDTO.setK(e.getK());
            detailDTO.setProbabilityOfInfection(e.getProbabilityOfInfection());
            detailDTO.setVisit(
                    e.getVisits().stream().map(visit -> new VisitMobileDTO(
                        visit.getId(),
                        new LocalityMobileDTO(
                                visit.getLocality().getName(),
                                visit.getLocality().getAddress(),
                                visit.getLocality().getGpsLocation(),
                                visit.getLocality().getNodes().size(),
                                5, //TODO
                                100 //TODO
                        ),
                        visit.getNodes().stream().map(node -> new NodeMobileDTO(node.getId(), node.getDescription())).collect(Collectors.toList()),
                        visit.getTimeArrived(),
                        visit.getTimeLeft(),
                        0.7F //TODO
                    )).collect(Collectors.toList())
            );
            detailDTOS.add(detailDTO);
        });
        return detailDTOS;
    }

    public PersonExpositionStatusDTO getStatus(String username) throws CustomApiException {
        PersonAndKInfectors personAndKInfectors = personAndKInfectorService.findTopByPersonOrderByDateDesc(credentialService.findPersonByUsername(username));
        Double temp = personAndKInfectors != null ? personAndKInfectors.getProbabilityOfInfection() : 0;
        Integer k = personAndKInfectors != null ? personAndKInfectors.getK() : 0;
        return new PersonExpositionStatusDTO(temp, temp < 33.33F ? "bajo" : temp < 66.66 ? "medio" : "alto", k);
    }
}
