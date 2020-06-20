package com.project.ccts.repository;

import com.project.ccts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Persistence;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByPersonalIdentifier(String personalIdentifier);

}
