package com.delombaertdamien.mareu.model;

import java.util.List;

public class Meeting {

    private Integer id;
    private String subject;
    private List<String> contributors;
    private float hourOfMeeting;
    private String place;

    public Meeting(Integer id, String subject, List<String> contributors, float hourOfMeeting, String place) {
        this.id = id;
        this.subject = subject;
        this.contributors = contributors;
        this.hourOfMeeting = hourOfMeeting;
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

    public float getHourOfMeeting() {
        return hourOfMeeting;
    }

    public void setHourOfMeeting(float hourOfMeeting) {
        this.hourOfMeeting = hourOfMeeting;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
