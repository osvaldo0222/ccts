package com.project.ccts.repository;

import com.project.ccts.model.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {

    Locality findByName(String name);

}
