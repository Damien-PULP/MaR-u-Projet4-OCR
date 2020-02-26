package com.delombaertdamien.mareu.model;

import java.util.List;

public class Metting {

    private Integer id;
    private String subject;
    private List<String> contributors;
    private float hourOfMetting;
    private String place;

    public Metting(Integer id, String subject, List<String> contributors, float hourOfMetting, String place) {
        this.id = id;
        this.subject = subject;
        this.contributors = contributors;
        this.hourOfMetting = hourOfMetting;
        this.place = place;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getContributors() {
        return contributors;
    }

    public void setContributors(List<String> contributors) {
        this.contributors = contributors;
    }

    public float getHourOfMetting() {
        return hourOfMetting;
    }

    public void setHourOfMetting(float hourOfMetting) {
        this.hourOfMetting = hourOfMetting;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
