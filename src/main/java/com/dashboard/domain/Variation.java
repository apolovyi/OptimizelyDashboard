package com.dashboard.domain;

import javax.persistence.*;

@Entity
public class Variation {

    @Id
    private String id;
    @Column
    private String name;
    @Column
    private Double cr;
    @Column
    private Double bs;
    @Column
    private Double crImprovement;
    @Column
    private Double bsImprovement;
    @ManyToOne
    @JoinColumn(name="EXPERIMENT_ID")
    private Experiment experiment;

    public Variation() {
    }

    public Variation(String id, Experiment experiment) {
        this.id = id;
        this.experiment = experiment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCr() {
        return cr;
    }

    public void setCr(Double cr) {
        this.cr = cr;
    }

    public Double getBs() {
        return bs;
    }

    public void setBs(Double bs) {
        this.bs = bs;
    }

    public Double getCrImprovement() {
        return crImprovement;
    }

    public void setCrImprovement(Double crImprovement) {
        this.crImprovement = crImprovement;
    }

    public Double getBsImprovement() {
        return bsImprovement;
    }

    public void setBsImprovement(Double bsImprovement) {
        this.bsImprovement = bsImprovement;
    }

    public void setCrFormat(Double cr) {
        this.cr = Math.round(cr * 10000.0) / 100.0;
    }

    public void setBsFormat(Double bs) {
        this.bs = Math.round(bs * 100.0) / 100.0;
    }

    public void setCrImprovementFormat(Double crImprovement) {
        this.crImprovement = Math.round(crImprovement * 10000.0) / 100.0;
    }

    public void setBsImprovementFormat(Double bsImprovement) {
        this.bsImprovement = Math.round(bsImprovement * 10000.0) / 100.0;
    }

    public String getCrClass() {
        if (this.crImprovement > 0)
            return "positive";
        return "negative";
    }

    public String getBsClass() {
        if (this.bsImprovement > 0)
            return "positive";
        return "negative";
    }
}
