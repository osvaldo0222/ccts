package com.project.ccts.repository;

import com.project.ccts.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByPersonalIdentifier(String personalIdentifier);

    Person findByEmail(String email);

    Person findByUserCredentialUuid(UUID uuid);

    Collection<Person> findByPersonalIdentifierContaining(String id);

}
