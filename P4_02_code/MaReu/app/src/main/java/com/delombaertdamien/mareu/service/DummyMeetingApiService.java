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
            Calendar cal2 = t1.getEndHourOfMeeting();

            float hour1 = cal1.get(Calendar.HOUR) + cal1.get(Calendar.MINUTE);
            float hour2 = cal2.get(Calendar.HOUR) + cal2.get(Calendar.MINUTE);

            return (String.valueOf(hour1)).compareToIgnoreCase(String.valueOf(hour2));
        });

    }

    @Override
    public List<String> getListPlaceAvailable(float startH, float endH) {

        List<String> listPlace = new ArrayList<>(PlaceCompany.getPlaceAvailable());

        for(int i = 0; i < meetings.size(); i++){

            Calendar startHour = meetings.get(i).getStartHourOfMeeting();
            Calendar endHour = meetings.get(i).getEndHourOfMeeting();

            float f1 = startHour.get(Calendar.HOUR) + startHour.get(Calendar.MINUTE);
            float f2 = endHour.get(Calendar.HOUR) + endHour.get(Calendar.MINUTE);

            if((startH >= f1 && startH <= f2) || (endH >= f1 && endH <= f2)){
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
