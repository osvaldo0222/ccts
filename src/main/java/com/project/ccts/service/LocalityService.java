package com.project.ccts.service;

import com.project.ccts.dto.LocalityByNameIdDTO;
import com.project.ccts.dto.LocalityDTO;
import com.project.ccts.model.entities.Locality;
import com.project.ccts.repository.LocalityRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class LocalityService extends AbstractCrud<Locality, Long> {

    private LocalityRepository localityRepository;

    @Autowired
    public void setLocalityRepository(LocalityRepository localityRepository) {
        this.localityRepository = localityRepository;
    }

    @Override
    public JpaRepository<Locality, Long> getDao() {
        return localityRepository;
    }

    public Locality findByName(String name) {
        return localityRepository.findByName(name);
    }

    public Collection<Locality> findByNameContaining(String name){
        return localityRepository.findByNameContainingIgnoreCase(name);
    }
    public Locality findByCellPhone(String cellPhone){
        return localityRepository.findByCellPhone(cellPhone);
    }
    public Collection<LocalityDTO> parseToLocalityDTO(Collection<Locality> locality) {
        Collection<LocalityDTO> localityDTOS = new ArrayList<>();
        locality.stream().forEach((aux_locality) -> {
           localityDTOS.add(new LocalityDTO(aux_locality.getId(), aux_locality.getName(), aux_locality.getAddress().getCity(),
                     aux_locality.getNodes().size()));
        });
        return localityDTOS;
    }


}
