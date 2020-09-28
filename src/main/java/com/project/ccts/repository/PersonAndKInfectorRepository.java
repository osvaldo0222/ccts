package com.project.ccts.repository;

import com.project.ccts.model.entities.PersonAndKInfectors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonAndKInfectorRepository extends JpaRepository<PersonAndKInfectors,Long> {
}
