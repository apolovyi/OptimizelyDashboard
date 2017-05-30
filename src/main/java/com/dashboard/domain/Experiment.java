package com.dashboard.domain;

import com.dashboard.domain.converter.DateConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Experiment {

    @Id
    @Column(name="ID")
    private String id;
    @Column
    private String name;
    @Column
    private String mainGoalId;
    @Column
    @Convert(converter = DateConverter.class)
    private LocalDate creationDate;
    @Column
    private String resultLink;
    @OneToMany(targetEntity = Variation.class, fetch = FetchType.EAGER, mappedBy="experiment")
    private List<Variation> variations;

    public Experiment() {
    }

    public Experiment(String id) {
        this.id = id;
        variations = new ArrayList<>();
    }

    public Experiment(String id, String name, String mainGoalId, String creationDate, String resultLink, List<Variation> variations) {
        this.id = id;
        this.name = name;
        this.mainGoalId = mainGoalId;
        String [] date = creationDate.substring(0,10).split("-");
        this.creationDate = LocalDate.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
        this.resultLink = resultLink;
        this.variations = variations;
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

    public String getMainGoalId() {
        return mainGoalId;
    }

    public void setMainGoalId(String mainGoalId) {
        this.mainGoalId = mainGoalId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getResultLink() {
        return resultLink;
    }

    public void setResultLink(String resultLink) {
        this.resultLink = resultLink;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public void setVariations(List<Variation> variations) {
        this.variations = variations;
    }
}
