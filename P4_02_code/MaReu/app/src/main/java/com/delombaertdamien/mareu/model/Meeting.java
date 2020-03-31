package com.delombaertdamien.mareu.model;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Meeting {

    private Integer id;
    private String subject;
    private List<String> contributors;
    private final Calendar startHourOfMeeting;
    private final Calendar endHourOfMeeting;
    private String place;

    public Meeting(Integer id, String subject, List<String> contributors, Calendar hourOfMeeting, Calendar endHour, String place) {

        this.id = id;
        this.subject = subject;
        this.contributors = contributors;
        this.startHourOfMeeting = hourOfMeeting;
        this.endHourOfMeeting = endHour;
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

    public List<String> getContributors() {
        return contributors;
    }

    public Calendar getStartHourOfMeeting() {
        return startHourOfMeeting;
    }
    public Calendar getEndHourOfMeeting(){
        return endHourOfMeeting;
    }

    public String getPlace() {
        return place;
    }

    @Override
    public boolean equals(Object mo) {
        if (this == mo) return true;
        if (mo == null || getClass() != mo.getClass()) return false;
        Meeting mmMeeting = (Meeting) mo;
        return Objects.equals(id, mmMeeting.id) &&
                Objects.equals(subject, mmMeeting.subject) &&
                Objects.equals(contributors, mmMeeting.contributors) &&
                Objects.equals(startHourOfMeeting, mmMeeting.startHourOfMeeting) &&
                Objects.equals(endHourOfMeeting, mmMeeting.endHourOfMeeting) &&
                Objects.equals(place, mmMeeting.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, contributors, startHourOfMeeting, endHourOfMeeting, place);
    }
}
