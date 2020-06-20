package com.project.ccts.bootstrap;

import com.project.ccts.model.Address;
import com.project.ccts.model.Person;
import com.project.ccts.service.PersonService;
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

    @Autowired
    public DefaultDataLoader(PersonService personService) {
        this.personService = personService;
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
    }
}
