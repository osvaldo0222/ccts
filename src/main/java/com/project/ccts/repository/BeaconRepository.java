package com.project.ccts.repository;

import com.project.ccts.model.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaconRepository extends JpaRepository<Beacon, Long> {

}
