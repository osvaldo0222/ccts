package com.project.ccts.repository;

import com.project.ccts.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByPersonalIdentifier(String personalIdentifier);

    Person findByEmail(String email);

}
