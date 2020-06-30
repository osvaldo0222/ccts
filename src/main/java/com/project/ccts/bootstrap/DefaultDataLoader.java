package com.project.ccts.bootstrap;

import com.project.ccts.model.*;
import com.project.ccts.service.BeaconService;
import com.project.ccts.service.LocalityService;
import com.project.ccts.service.PersonService;
import com.project.ccts.util.enums.BeaconStatus;
import com.project.ccts.util.enums.CivilStatus;
import com.project.ccts.util.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;

@Component
public class DefaultDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private PersonService personService;
    private LocalityService localityService;
    private BeaconService beaconService;

    @Autowired
    public DefaultDataLoader(PersonService personService) {
        this.personService = personService;
    }
    @Autowired
    public void setLocalityService(LocalityService localityService) {
        this.localityService = localityService;
    }
    @Autowired
    public void setBeaconService(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1999, Calendar.FEBRUARY, 22);
        Person person = new Person(
                "40214842094",
                "Osvaldo",
                "Fernandez",
                "osvaldof22@hotmail.com",
                "849-253-6511",
                LocalDate.of(1999,Calendar.FEBRUARY,22),
                Gender.MALE,
                CivilStatus.SINGLE,
                "Estudiante",
                new Address("Calle 80 #5 Los tocones", "51000", "Santiago", "Republica Dominicana"),
                null
        );

        personService.createOrUpdate(person);
        loadLocality();
    }
    public void loadLocality(){
        Locality  locality = new Locality("Osvaldo's Home",new Address("Calle 80 #5 Los tocones"
                ,"51000","Santiago","Republica Dominicana"),"sample@spring.com","8097776766",
                "beacon-123");

        localityService.createOrUpdate(locality);
        beaconService.createOrUpdate(new Beacon("123",locality,new BeaconCredential("beacon-123",
                "123",false,BeaconStatus.ACTIVE), (float) 87.3,550));
    }
}
