package com.project.ccts.dto.covidSummary.genericInfo;

import javax.persistence.Embeddable;


public class Cases {
    private String New;
    private Integer active;
    private Integer critical;
    private Integer recovered;
    private Integer total;

    public Cases(String aNew, Integer active, Integer critical, Integer recovered, Integer total) {
        New = aNew;
        this.active = active;
        this.critical = critical;
        this.recovered = recovered;
        this.total = total;
    }
    public Cases(){}

    @Override
    public String toString() {
        return "Cases{" +
                "New='" + New + '\'' +
                ", active=" + active +
                ", critical=" + critical +
                ", recovered=" + recovered +
                ", total=" + total +
                '}';
    }

    public String getNew() {
        return New;
    }

    public void setNew(String aNew) {
        New = aNew;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
