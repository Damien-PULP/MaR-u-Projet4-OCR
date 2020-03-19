package com.delombaertdamien.mareu.service;

import com.delombaertdamien.mareu.model.Meeting;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {

    /**
     * This method return the list of meetings
     * @return
     */
    List<Meeting> getMeetings();

    /**
     * This method add a meeting in the list
     * @param id
     * @param subject
     * @param place
     * @param contributors
     * @param startHour
     * @param endHour
     */
    void addMeeting(int id, String subject, String place, List<String> contributors, Calendar startHour, Calendar endHour);

    /**
     * This method delete a meeting in the list
     * @param meeting
     */
    void removeMeeting(Meeting meeting);

    /**
     * This method sort the list trough place
     */
    void getListWithFilterPlace ();

    /**
     * This method sort the list trough hour
     */
    void getListWithFilterHour ();

    /**
     * This method return the list of place available when a hour
     * @param endH
     * @return
     */
    List<String> getListPlaceAvailable (float startH, float endH);
}
