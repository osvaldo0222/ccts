package com.project.ccts.repository;

import com.project.ccts.model.entities.Institution;
import com.project.ccts.model.entities.Locality;
import com.project.ccts.model.enums.InstitutionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {

    Locality findByName(String name);

    Collection<Locality> findByNameContainingIgnoreCase(String name);

    Locality findByCellPhone(String cellphone);

    Locality findByNameAndEmail(String name,String email);

    Locality findByNameAndEmailAndId(String name,String email,Long id);

}
