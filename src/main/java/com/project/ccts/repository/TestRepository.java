package com.project.ccts.repository;

import com.project.ccts.model.entities.Person;
import com.project.ccts.model.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {


}
