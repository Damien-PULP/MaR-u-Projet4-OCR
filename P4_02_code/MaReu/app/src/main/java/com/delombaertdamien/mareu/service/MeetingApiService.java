package com.delombaertdamien.mareu.service;

import com.delombaertdamien.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();
    void addMeeting(int id,String subject, String place, List<String> contributors, int startHour, int startMin);
    void removeMeeting(Meeting meeting);
    void getListWithFilterPlace ();
    void getListWithFilterHour ();

    /**
     * This method return the list of place available when a hour
     * @param hour
     * @return
     */
    List<String> getListPlaceAvailable (float hour);
}
