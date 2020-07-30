package com.project.ccts.dto.covidSummary.genericInfo;

import javax.persistence.Embeddable;


public class Tests {
    private Integer total;

    public Tests(Integer total) {
        this.total = total;
    }

    public Tests() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Tests{" +
                "total=" + total +
                '}';
    }
}
