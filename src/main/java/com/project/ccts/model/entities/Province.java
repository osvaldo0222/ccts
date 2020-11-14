package com.project.ccts.model.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String provinceName;
    @OneToMany(mappedBy = "province")
    private Collection<ProvinceStatistics> provinceStatistics;

    public Province() {
    }

    public Province(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Collection<ProvinceStatistics> getProvinceStatistics() {
        return provinceStatistics;
    }

    public void setProvinceStatistics(Collection<ProvinceStatistics> provinceStatistics) {
        this.provinceStatistics = provinceStatistics;
    }
}
