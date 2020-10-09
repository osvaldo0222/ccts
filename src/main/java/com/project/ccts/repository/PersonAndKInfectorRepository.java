package com.project.ccts.repository;

import com.project.ccts.model.entities.Person;
import com.project.ccts.model.entities.PersonAndKInfectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonAndKInfectorRepository extends JpaRepository<PersonAndKInfectors, Long> {

    PersonAndKInfectors findTopByPersonOrderByDateDesc(Person person);

    Page<PersonAndKInfectors> findByPersonOrderByDateDesc(Person person, Pageable pageable);
}
