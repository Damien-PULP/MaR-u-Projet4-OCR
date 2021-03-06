package com.delombaertdamien.mareu.service;

import com.delombaertdamien.mareu.model.Filter;
import com.delombaertdamien.mareu.model.Meeting;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {

    /**
     * This method return the list of meetings
     * @return
     */
    List<Meeting> getMeetings();

    List<Meeting> getMeetingsToShow ();

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
     * This method return a meeting by hashcode
     * @param obj
     * @return
     */
    Meeting getMeetingWithHashCode(Object obj);

    /**
     * This method delete a meeting in the list
     * @param meeting
     */
    void removeMeeting(Meeting meeting);

    /**
     * This method sort the list trough place & hour with Filter
     */
    void getListWithFilter(Filter filter);

    /**
     * This method return the list of place available when a hour
     * @param endH
     * @return
     */
    List<String> getListPlaceAvailable (Calendar startH, Calendar endH);

    /**
     * This method return the meeting to display
     * @return
     */
    Meeting getMeetingToDisplay ();

    /**
     * This method set the meeting to display
     * @param meeting
     */
    void setMeetingToDisplay (Meeting meeting);
}
