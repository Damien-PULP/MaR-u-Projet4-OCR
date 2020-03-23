package com.delombaertdamien.mareu.service;

import com.delombaertdamien.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *  Implementation of ApiService
 */
public class DummyMeetingApiService implements MeetingApiService {


    private List<Meeting> meetings = new ArrayList<>();
    private Meeting meetingToDisplay;

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void addMeeting(int id, String subject, String place, List<String> contributors, Calendar startHour, Calendar endHour) {

        Meeting meeting = new Meeting(id, subject, contributors, startHour, endHour, place);

        meetings.add(meeting);
    }

    @Override
    public Meeting getMeetingWithHashCode(Object obj) {

        for(int i = 0; i < meetings.size(); i++){
            if(meetings.get(i).hashCode() == obj.hashCode()){
                return meetings.get(i);
            }
        }
        return null;
    }

    @Override
    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void getListWithFilterPlace() {
        Collections.sort(meetings, (t0, t1) -> t0.getPlace().compareToIgnoreCase(t1.getPlace()));
    }

    @Override
    public void getListWithFilterHour() {

        Collections.sort(meetings, (t0, t1) -> {

            Calendar cal1 = t0.getStartHourOfMeeting();
            Calendar cal2 = t1.getStartHourOfMeeting();

            return (cal1.compareTo(cal2));
        });

    }

    @Override
    public List<String> getListPlaceAvailable(Calendar startH, Calendar endH) {

        List<String> listPlace = new ArrayList<>(PlaceCompany.getPlaceAvailable());

        for(int i = 0; i < meetings.size(); i++){

            Calendar startHourLocal = meetings.get(i).getStartHourOfMeeting();
            Calendar endHourLocal = meetings.get(i).getEndHourOfMeeting();

            if((startH.getTimeInMillis() >= startHourLocal.getTimeInMillis() &&
                    startH.getTimeInMillis() <= endHourLocal.getTimeInMillis()) ||
                    (endH.getTimeInMillis() >= startHourLocal.getTimeInMillis() &&
                            endH.getTimeInMillis() <= endHourLocal.getTimeInMillis())){

                String place = meetings.get(i).getPlace();

                for(int ii = 0 ; ii < listPlace.size(); ii ++){
                    if(place.equals(listPlace.get(ii))) {
                        listPlace.remove(ii);
                    }
                }
            }
        }

        return listPlace;

    }

    @Override
    public Meeting getMeetingToDisplay() {
        return meetingToDisplay;
    }

    @Override
    public void setMeetingToDisplay(Meeting meeting) {
        meetingToDisplay = meeting;
    }

}
