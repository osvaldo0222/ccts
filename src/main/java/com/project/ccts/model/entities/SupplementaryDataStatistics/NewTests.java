package com.project.ccts.model.entities.SupplementaryDataStatistics;

import com.project.ccts.util.logger.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
 @Entity
public class NewTests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer total;

    public NewTests() {
    }

    public NewTests(Integer total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     public Integer getTotal() {
         return total;
     }

     public void setTotal(Integer total) {
         this.total = total;
     }
 }
