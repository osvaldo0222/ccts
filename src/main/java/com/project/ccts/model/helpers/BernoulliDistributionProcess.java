package com.project.ccts.model.helpers;

public class BernoulliDistributionProcess {
    private Integer n;
    private InfectionProbability infectionProbability;
    private static BernoulliDistributionProcess bernoulliDistributionProcess = null;

    public BernoulliDistributionProcess(Integer n, InfectionProbability infectionProbability) {
        this.n = n;
        this.infectionProbability = infectionProbability;
    }

    public BernoulliDistributionProcess() {
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public InfectionProbability getInfectionProbability() {
        return infectionProbability;
    }

    public void setInfectionProbability(InfectionProbability infectionProbability) {
        this.infectionProbability = infectionProbability;
    }
}
