package com.project.ccts.repository;

import com.project.ccts.model.entities.Institution;
import com.project.ccts.model.enums.InstitutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
            Collection<Institution> findByType(InstitutionType type);
            Institution findByTypeAndNameAndEmailAndId(@Param("type") InstitutionType type,@Param("name") String name,
                                                       @Param("email") String email,@Param("id") Long id);
            Institution findByNameAndEmailAndType(@Param("name") String name,@Param("email") String email,@Param("type")InstitutionType type);
            Collection<Institution> findAllByTypeAndNameContainingIgnoreCase(@Param("type") InstitutionType type,@Param("name") String name);

}
