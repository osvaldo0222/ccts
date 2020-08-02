package com.project.ccts.repository;

import com.project.ccts.model.entities.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {

    Locality findByName(String name);

    Collection<Locality> findByNameContainingIgnoreCase(String name);

    Locality findByCellPhone(String cellphone);

}
