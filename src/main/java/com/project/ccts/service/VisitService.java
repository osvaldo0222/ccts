package com.project.ccts.service;

import com.project.ccts.dto.LocalityMobileDTO;
import com.project.ccts.dto.NodeMobileDTO;
import com.project.ccts.dto.VisitMobileDTO;
import com.project.ccts.model.entities.*;
import com.project.ccts.repository.VisitRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.exception.CustomApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitService extends AbstractCrud<Visit, Long> {

    private VisitRepository visitRepository;
    private CredentialService credentialService;

    @Autowired
    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Override
    public JpaRepository<Visit, Long> getDao() {
        return visitRepository;
    }

    public Collection<VisitMobileDTO> getVisits(String username, Integer page, Integer size, String search) throws CustomApiException {
        Person person = credentialService.findPersonByUsername(username);

        List<Visit> visits = null;

        if (search == null || search.equalsIgnoreCase("")) {
            visits =  visitRepository.findByPersonOrderByTimeLeftDesc(person, PageRequest.of(page, size)).getContent();
        } else {
            visits = visitRepository.customVisitSearch(person, search, PageRequest.of(page, size)).getContent();
        }

        List<VisitMobileDTO> mobileDTOS = new ArrayList<>();

        for (Visit visit : visits) {
            Locality locality = visit.getLocality();

            VisitMobileDTO visitMobileDTO = new VisitMobileDTO(
                    visit.getId(),
                    new LocalityMobileDTO(
                            locality.getName(),
                            locality.getAddress(),
                            locality.getGpsLocation(),
                            locality.getNodes().size(),
                            5, //TODO
                            100 //TODO
                    ),
                    visit.getNodes().stream().map(node -> new NodeMobileDTO(node.getId(), node.getDescription())).collect(Collectors.toList()),
                    visit.getTimeArrived(),
                    visit.getTimeLeft(),
                    0.7F //TODO
            );
            mobileDTOS.add(visitMobileDTO);
        }

        return mobileDTOS;
    }
}
