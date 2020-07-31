package com.project.ccts.service;

import com.project.ccts.dto.HealthStatusMobileAddDTO;
import com.project.ccts.dto.HealthStatusMobileDTO;
import com.project.ccts.model.entities.HealthStatus;
import com.project.ccts.model.entities.Person;
import com.project.ccts.repository.HealthStatusRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.exception.CustomApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HealthStatusService extends AbstractCrud<HealthStatus, Long> {

    private HealthStatusRepository healthStatusRepository;
    private CredentialService credentialService;

    @Autowired
    public void setHealthStatusRepository(HealthStatusRepository healthStatusRepository) {
        this.healthStatusRepository = healthStatusRepository;
    }

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Override
    public JpaRepository<HealthStatus, Long> getDao() {
        return healthStatusRepository;
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
                        List.of("Mantengase en casa"))) //TODO
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
}
