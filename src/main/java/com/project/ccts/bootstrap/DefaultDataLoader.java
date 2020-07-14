package com.project.ccts.bootstrap;

import com.project.ccts.model.entities.*;
import com.project.ccts.service.NodeService;
import com.project.ccts.service.LocalityService;
import com.project.ccts.service.PersonService;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.model.enums.CivilStatus;
import com.project.ccts.model.enums.Gender;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Component
public class DefaultDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private PersonService personService;
    private LocalityService localityService;
    private NodeService nodeService;

    @Autowired
    public DefaultDataLoader(PersonService personService, LocalityService localityService, NodeService nodeService) {
        this.personService = personService;
        this.localityService = localityService;
        this.nodeService = nodeService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger.getInstance().getLog(this.getClass()).info("Default data bootstrap [...]");

        //Create default persons
        createDefaultPersons();

        //
        loadLocality();

        Logger.getInstance().getLog(this.getClass()).info("Ending default data bootstrap [...]");
    }

    public void createDefaultPersons() {
        Logger.getInstance().getLog(getClass()).info("Creating default persons [...]");

        Collection<Person> personCollection = List.of(
                new Person(
                        "11111111111",
                        "Osvaldo",
                        "Fernandez",
                        null,
                        "849-253-6511",
                        LocalDate.of(1999, Calendar.FEBRUARY + 1,22),
                        Gender.MALE,
                        CivilStatus.SINGLE,
                        "Estudiante",
                        new Address("Calle 80 #5 Los tocones", "51000", "Santiago", "Republica Dominicana"),
                        null
                ),
                new Person(
                        "22222222222",
                        "Edgar",
                        "Garcia",
                        null,
                        "829-200-6511",
                        LocalDate.of(1999, Calendar.JUNE + 1,10),
                        Gender.MALE,
                        CivilStatus.SINGLE,
                        "Estudiante",
                        new Address("Calle 80 Los Minas", "51020", "La Vega", "Republica Dominicana"),
                        null
                ),
                new Person(
                        "33333333333",
                        "Armando",
                        "Roman",
                        null,
                        "809-253-6610",
                        LocalDate.of(1999, Calendar.DECEMBER + 1,30),
                        Gender.MALE,
                        CivilStatus.SINGLE,
                        "Estudiante",
                        new Address("Calle 80 Los Alcarrizos", "51030", "Navarrete", "Republica Dominicana"),
                        null
                ),
                new Person(
                        "44444444444",
                        "Daniel",
                        "Peña",
                        null,
                        "809-203-6001",
                        LocalDate.of(1999, Calendar.JANUARY + 1,24),
                        Gender.MALE,
                        CivilStatus.SINGLE,
                        "Estudiante",
                        new Address("Calle 9 Los Prados", "53000", "Navarrete", "Republica Dominicana"),
                        null
                )
        );
        personService.createAll(personCollection);
    }
    public void loadLocality(){
        Locality locality = new Locality("Osvaldo's Home",new Address("Calle 80 #5 Los tocones"
                ,"51000","Santiago","Republica Dominicana"),"sample@spring.com","8097776766");

        localityService.createOrUpdate(locality);
        Node node = new Node("N-1000","This is demo",(float)98.3);
        node.setLocality(locality);
        nodeService.createOrUpdate(node);

    }
}
