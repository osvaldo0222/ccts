package com.project.ccts.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.project.ccts.dto.*;
import com.project.ccts.model.entities.*;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.repository.VisitRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.exception.CustomApiException;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitService extends AbstractCrud<Visit, Long> {

    private VisitRepository visitRepository;
    private CredentialService credentialService;
    private NodeService nodeService;
    private PersonService personService;

    @Autowired
    public VisitService(VisitRepository visitRepository, CredentialService credentialService, NodeService nodeService, PersonService personService) {
        this.visitRepository = visitRepository;
        this.credentialService = credentialService;
        this.nodeService = nodeService;
        this.personService = personService;
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

    public void addVisits(MqttDTO mqttDTO) {
        try {
            VisitTemplateDTO[] visitTemplateDTOS = new Gson().fromJson(mqttDTO.getData().toString(), VisitTemplateDTO[].class);
            Node node = nodeService.findByNodeIdentifier(mqttDTO.getNodeIdentifier());
            if (node == null) {
                throw new Exception("Node not found " + mqttDTO.getNodeIdentifier());
            }
            Locality locality = node.getLocality();
            for (VisitTemplateDTO visitTemplateDTO : visitTemplateDTOS) {
                Person person = personService.findByUuid(getUuidFromString(visitTemplateDTO.getUuid()));
                if (person == null) {
                    continue;
                }
                LocalDateTime timeArrived = Instant.ofEpochMilli(visitTemplateDTO.getTimeArrived()).atZone(ZoneId.of("UTC-4")).toLocalDateTime();
                LocalDateTime timeLeft = Instant.ofEpochMilli(visitTemplateDTO.getTimeLeft()).atZone(ZoneId.of("UTC-4")).toLocalDateTime();
                Visit visit = checkIfVisitContinue(person, node, timeArrived, timeLeft);
                if (visit == null) {
                    visit = new Visit(
                            List.of(node),
                            locality,
                            person,
                            timeArrived,
                            timeLeft,
                            visitTemplateDTO.getRssi()
                    );
                } else {
                   visit.addNode(node);
                }
                createOrUpdate(visit);
            }

            if (node.getStatus().equals(NodeStatus.DOWN) || node.getStatus().equals(NodeStatus.INACTIVE)) {
                node.setStatus(NodeStatus.ACTIVE);
                nodeService.createOrUpdate(node);
            }
        } catch (Exception e) {
            Logger.getInstance().getLog(getClass()).info(e.getMessage());
        }
    }

    public UUID getUuidFromString(String uuid) {
        if (uuid.length() >= 32) {
            String newString = String.format("%s-%s-%s-%s-%s", uuid.substring(0, 8), uuid.substring(8, 12), uuid.substring(12, 16), uuid.substring(16, 20), uuid.substring(20, 32));
            return UUID.fromString(newString);
        } else {
            return null;
        }
    }

    public Visit checkIfVisitContinue(Person person, Node node, LocalDateTime timeArrived, LocalDateTime timeLeft) {
        List<Visit> visits = (List<Visit>) person.getVisits();
        for (Visit visit : visits) {
            if (visit.getLocality().equals(node.getLocality()) && (timeArrived.isAfter(visit.getTimeArrived().minusMinutes(720)) && timeArrived.isBefore(visit.getTimeArrived().plusMinutes(720)))) {
                if (timeArrived.isBefore(visit.getTimeArrived())) {
                    visit.setTimeArrived(timeArrived);
                }
                if (timeLeft.isAfter(visit.getTimeLeft())) {
                    visit.setTimeLeft(timeLeft);
                }
                return visit;
            }
        }
        return null;
    }
}
