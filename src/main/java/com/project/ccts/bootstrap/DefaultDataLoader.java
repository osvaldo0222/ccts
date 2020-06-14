package com.project.ccts.bootstrap;

import com.project.ccts.model.Address;
import com.project.ccts.model.Person;
import com.project.ccts.model.Tag;
import com.project.ccts.service.PersonService;
import com.project.ccts.service.TagService;
import com.project.ccts.util.enums.CivilStatus;
import com.project.ccts.util.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Component
public class DefaultDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private PersonService personService;
    private TagService tagService;

    @Autowired
    public DefaultDataLoader(PersonService personService, TagService tagService) {
        this.personService = personService;
        this.tagService = tagService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1999, Calendar.FEBRUARY, 22);
        Person person = new Person(
                "402-1484209-4",
                "Osvaldo",
                "Fernandez",
                "osvaldof22@hotmail.com",
                "849-253-6511",
                LocalDate.of(1999,Calendar.FEBRUARY,22),
                Gender.MALE,
                CivilStatus.SINGLE,
                "Estudiante",
                new Address("Calle 80 #5 Los tocones", "51000", "Santiago", "Republica Dominicana"),
                null,
                tagService.createOrUpdate(new Tag("12345", "ejgoirjtoi"))
        );

        personService.createOrUpdate(person);
    }


}
