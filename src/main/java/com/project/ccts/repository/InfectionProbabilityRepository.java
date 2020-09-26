package com.project.ccts.repository;

import com.project.ccts.model.helpers.InfectionProbability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface InfectionProbabilityRepository extends JpaRepository<InfectionProbability,Long> {


}
