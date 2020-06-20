package com.project.ccts.service;

import com.project.ccts.model.Beacon;
import com.project.ccts.repository.BeaconRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BeaconService extends AbstractCrud<Beacon, Long> {

    private BeaconRepository beaconRepository;

    @Autowired
    public void setBeaconRepository(BeaconRepository beaconRepository) {
        this.beaconRepository = beaconRepository;
    }

    @Override
    public JpaRepository<Beacon, Long> getDao() {
        return beaconRepository;
    }

}
