package com.project.ccts.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String tagId;
    private String data;
    @OneToOne(mappedBy = "tag", fetch = FetchType.LAZY)
    private Person person;
    @OneToMany(mappedBy = "tag")
    private Collection<Visit> visits;
    @CreationTimestamp
    private Date creationDate;

    public Tag() {
    }

    public Tag(String tagId, String data) {
        this.tagId = tagId;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Collection<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Collection<Visit> visits) {
        this.visits = visits;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
