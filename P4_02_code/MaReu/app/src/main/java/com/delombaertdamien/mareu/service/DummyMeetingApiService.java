package com.delombaertdamien.mareu.service;

import android.util.Log;

import com.delombaertdamien.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void addMeeting(int id,String subject, String place, List<String> contributors, int startHour, int startMin) {

        Log.d("API", "hour : " + startHour + " min : " + startMin);
        float time = (float)startHour + (((float)startMin) / 60f);
        Meeting meeting = new Meeting(id, subject, contributors, time, place);
        meetings.add(meeting);
    }

    @Override
    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void getListWithFilterPlace() {
        Collections.sort(meetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting t0, Meeting t1) {

                return t0.getPlace().compareToIgnoreCase(t1.getPlace());
            }
        });
    }

    @Override
    public void getListWithFilterHour() {
        Collections.sort(meetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting t0, Meeting t1) {

                return Float.compare(t0.getHourOfMeeting(), t1.getHourOfMeeting());
                        //(String.valueOf(t0.getHourOfMeeting())).compareToIgnoreCase(String.valueOf(t1.getHourOfMeeting()));
            }
        });
        Log.d("ApiService", "The list is sorted");

    }


    @Override
    public List<String> getListPlaceAvailable(float hour) {

        List<String> listPlace = new ArrayList<>();
        listPlace.addAll(PlaceCompany.getPlaceAvailable());

        for(int i = 0; i < meetings.size(); i++){
            float hourMeeting = meetings.get(i).getHourOfMeeting();

            if( hour > (hourMeeting - 0.75f)  &&  hour < (hourMeeting + 0.75f)){
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
}
