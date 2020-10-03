package com.project.ccts.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.project.ccts.dto.*;
import com.project.ccts.dto.visitSearch.VisitAndTimeShared;
import com.project.ccts.model.entities.*;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.repository.VisitRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.exception.CustomApiException;
import com.project.ccts.util.logger.Logger;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitService extends AbstractCrud<Visit, Long> {

    private VisitRepository visitRepository;
    private CredentialService credentialService;
    private NodeService nodeService;
    private PersonService personService;
    private TestService testService;
    private HealthStatusService healthStatusService;


    @Autowired
    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }
    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }
    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
    @Autowired
    public void setTestService(TestService testService) {
        this.testService = testService;
    }
    @Autowired
    public void setHealthStatusService(HealthStatusService healthStatusService) {
        this.healthStatusService = healthStatusService;
    }

    /*
     * Find all visits in a period
     * */
    public Collection<Visit> findAllByTimeArrivedAfterAndPerson(LocalDateTime localDateTime,Person person){
        return visitRepository.findAllByTimeArrivedAfterAndPerson(localDateTime,person);
    }
    public Collection<Visit> findAllByTimeArrivedBetweenOrTimeLeftBetween(LocalDateTime timeArrived,LocalDateTime timeLeft){
        return visitRepository.findAllByTimeArrivedBetweenOrTimeLeftBetween(timeArrived,timeLeft);
    }

    /**?
     * Function that extract all visits in relation with time and space related to an infected user
     * @param person
     * @param daysBefore
     * @return
     */
    public Collection<VisitAndTimeShared> findAllVisitsCorrelatedTimeAndSpace(Person person, int daysBefore){
        //This function receives a person because it was identified with corona-virus, to detect the visits of the person in last days
        //next collection has all visit related to an user
        Collection<Visit> visits = findAllByTimeArrivedAfterAndPerson(LocalDateTime.now().minusDays(daysBefore),person);
        Collection<VisitAndTimeShared> visitsRelatedToInfectedUser = new ArrayList<>();

        visits.stream().forEach(visit -> {
            Collection<Visit> aux = findAllByTimeArrivedBetweenOrTimeLeftBetween(visit.getTimeArrived().minusMinutes(3),visit.getTimeLeft().plusMinutes(3));
            if(aux != null){
                aux.stream().forEach(visit1 -> {
                    if (visit1.getPerson() != visit.getPerson()){
                        visitsRelatedToInfectedUser.add(new VisitAndTimeShared(visit1,durationBetweenDates(visit.getTimeArrived().toEpochSecond(ZoneOffset.of("Z")),
                                visit.getTimeLeft().toEpochSecond(ZoneOffset.of("Z")),visit1.getTimeArrived().toEpochSecond(ZoneOffset.of("Z")),
                                visit1.getTimeLeft().toEpochSecond(ZoneOffset.of("Z")))));
                    }
                });
            }
         });
        return visitsRelatedToInfectedUser;
    }

    public Long durationBetweenDates(Long timeArriveInfected,Long timeLeftInfected,Long timeArrived,Long timeLeft){
        if (timeArriveInfected <= timeArrived  &&  timeLeftInfected <= timeLeft){
            return (timeLeftInfected - timeArrived)/60;
        }else if (timeArriveInfected >= timeArrived && timeLeftInfected >= timeLeft){
            return (timeLeft - timeArriveInfected)/60;
        }else if (timeArriveInfected <= timeArrived && timeLeftInfected >= timeLeft){
            return (timeLeft - timeArrived)/60;
        }
        return 0L;
    }

    public Collection<Person> getNearestContactOfVisit(Collection<VisitAndTimeShared> allVisitsOfProbablyInfectedUsers){
        Collection<Person> people = new ArrayList<>();
        allVisitsOfProbablyInfectedUsers.stream().forEach(visitAndTimeShared -> {
            Boolean state = false;
            Person person = visitAndTimeShared.getVisit().getPerson();
            state = people.contains(person);
            if (state == false){
                people.add(person);
            }
        });
        return people;
    }
    public int findK_NearestInfectedContactsOfProbablyInfected(Person person,Integer daysBefore){
        int k = 0;
        Collection<VisitAndTimeShared> visitAndTimeShared = findAllVisitsCorrelatedTimeAndSpace(person,daysBefore);
        if (visitAndTimeShared != null){
            Collection<Person> people = getNearestContactOfVisit(visitAndTimeShared);
            if (people != null){
                k = (int) people.stream().map(person1 -> healthStatusService.findTopByPersonOrderByStatusDateDesc(person1)).filter(healthStatus -> healthStatus != null && healthStatus.getTest().getStatus()).count();
            }
        }
        return k;
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
