package com.project.ccts.dto.covidSummary.genericInfo;

import javax.persistence.Embeddable;


public class Deaths {
    private String New;
    private Integer total;

    public Deaths(String aNew, Integer total) {
        New = aNew;
        this.total = total;
    }

    public Deaths() {
    }

    public String getNew() {
        return New;
    }

    public void setNew(String aNew) {
        New = aNew;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Deaths{" +
                "New='" + New + '\'' +
                ", total=" + total +
                '}';
    }
}
