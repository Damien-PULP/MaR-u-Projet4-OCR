package com.delombaertdamien.mareu;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.Utils.UtilsMeetingTest;
import com.delombaertdamien.mareu.model.Filter;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService mApiService;

    @Before
    public void setup (){
        mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void addMeetingWithSuccess (){

        //Given : Create the meeting to add
        Meeting meeting1 = UtilsMeetingTest.getAMeeting();
        //When : add the meeting in Api && create the first meeting of the list Api
        mApiService.addMeeting(meeting1.getId(), meeting1.getSubject(), meeting1.getPlace(), meeting1.getContributors(), meeting1.getStartHourOfMeeting(), meeting1.getEndHourOfMeeting());
        Meeting meeting2 = mApiService.getMeetings().get(0);
        //Then : check if the meeting is equal
        assertEquals(meeting1, meeting2);

    }

    @Test
    public void removeMeetingWithSuccess (){

        //Given : Create the meeting
        Meeting meeting1 = UtilsMeetingTest.getAMeeting();
        //When : add && remove the meeting of the list
        mApiService.addMeeting(meeting1.getId(), meeting1.getSubject(), meeting1.getPlace(), meeting1.getContributors(), meeting1.getStartHourOfMeeting(), meeting1.getEndHourOfMeeting());
        mApiService.removeMeeting(meeting1);
        //Then : check if the list contains not the meeting
        assertFalse(mApiService.getMeetings().contains(meeting1));
    }

    //TODO check if true
    @Test
    public void getListMeetingWithSuccess (){
        //Given : get the meetings for add
        List<Meeting> listMeeting = UtilsMeetingTest.getAListOfMeeting();
        //When : add the meetings of the list
        mApiService.addMeeting(listMeeting.get(0).getId(), listMeeting.get(0).getSubject(), listMeeting.get(0).getPlace(), listMeeting.get(0).getContributors(), listMeeting.get(0).getStartHourOfMeeting(), listMeeting.get(0).getEndHourOfMeeting());
        mApiService.addMeeting(listMeeting.get(1).getId(), listMeeting.get(1).getSubject(), listMeeting.get(1).getPlace(), listMeeting.get(1).getContributors(), listMeeting.get(1).getStartHourOfMeeting(), listMeeting.get(1).getEndHourOfMeeting());
        mApiService.addMeeting(listMeeting.get(2).getId(), listMeeting.get(2).getSubject(), listMeeting.get(2).getPlace(), listMeeting.get(2).getContributors(), listMeeting.get(2).getStartHourOfMeeting(), listMeeting.get(2).getEndHourOfMeeting());
        List<Meeting> listMeetingExpected = mApiService.getMeetings();
        //Then : check if the size of the list is true
        assertEquals(listMeeting.size(), listMeetingExpected.size());
    }

    @Test
    public void getMeetingWithHashCodeWithSuccess (){
        //Given : get the list for add
        Meeting meeting1 = UtilsMeetingTest.getAMeeting();
        //When : add the meetings of the list
        mApiService.addMeeting(meeting1.getId(), meeting1.getSubject(), meeting1.getPlace(), meeting1.getContributors(), meeting1.getStartHourOfMeeting(), meeting1.getEndHourOfMeeting());
        //Then : check if the meeting get with the hashcode is equals with the meeting
        assertEquals(meeting1,mApiService.getMeetingWithHashCode(meeting1.hashCode()));
    }

    @Test
    public void setAndGetMeetingToDisplayWithSuccess (){
        //Given : get meeting to display
        Meeting meetingForDisplay = UtilsMeetingTest.getAMeeting();
        //When : set meeting to display in api
        mApiService.setMeetingToDisplay(meetingForDisplay);
        //Then : check if the meeting to display is equals for meeting to display
        assertEquals(meetingForDisplay, mApiService.getMeetingToDisplay());
    }
    //TODO CHECK FIXED
    @Test
    public void getListSortByPlaceWithSuccess (){
        //Given : get list not sorted by place
        List<Meeting> listMeeting = UtilsMeetingTest.getAListOfMeetingNotSorted();
        //When : add the meeting of list in Api && sorted this && sorted the other list
        mApiService.addMeeting(listMeeting.get(0).getId(), listMeeting.get(0).getSubject(), listMeeting.get(0).getPlace(), listMeeting.get(0).getContributors(), listMeeting.get(0).getStartHourOfMeeting(), listMeeting.get(0).getEndHourOfMeeting());
        mApiService.addMeeting(listMeeting.get(1).getId(), listMeeting.get(1).getSubject(), listMeeting.get(1).getPlace(), listMeeting.get(1).getContributors(), listMeeting.get(1).getStartHourOfMeeting(), listMeeting.get(1).getEndHourOfMeeting());
        mApiService.addMeeting(listMeeting.get(2).getId(), listMeeting.get(2).getSubject(), listMeeting.get(2).getPlace(), listMeeting.get(2).getContributors(), listMeeting.get(2).getStartHourOfMeeting(), listMeeting.get(2).getEndHourOfMeeting());

        Filter filter = new Filter();
        filter.addPlace("A");

        mApiService.getListWithFilter(filter);

        List<Meeting> listFilterByPlaceA = mApiService.getMeetingsToShow();

        assertEquals(listFilterByPlaceA.size(), 2);
    }

    //TODO CHECK FIXED
    @Test
    public void getListSortByHourWithSuccess (){
        //Given : get list not sorted by hour
        List<Meeting> listMeeting = UtilsMeetingTest.getAListOfMeetingNotSorted();
        //When : add the meeting of list in Api && sorted this && sorted the other list
        mApiService.addMeeting(listMeeting.get(0).getId(), listMeeting.get(0).getSubject(), listMeeting.get(0).getPlace(), listMeeting.get(0).getContributors(), listMeeting.get(0).getStartHourOfMeeting(), listMeeting.get(0).getEndHourOfMeeting());
        mApiService.addMeeting(listMeeting.get(1).getId(), listMeeting.get(1).getSubject(), listMeeting.get(1).getPlace(), listMeeting.get(1).getContributors(), listMeeting.get(1).getStartHourOfMeeting(), listMeeting.get(1).getEndHourOfMeeting());
        mApiService.addMeeting(listMeeting.get(2).getId(), listMeeting.get(2).getSubject(), listMeeting.get(2).getPlace(), listMeeting.get(2).getContributors(), listMeeting.get(2).getStartHourOfMeeting(), listMeeting.get(2).getEndHourOfMeeting());

        Filter filter = new Filter();
        filter.addHour(8);

        mApiService.getListWithFilter(filter);

        List<Meeting> listFilterByPlaceA = mApiService.getMeetingsToShow();

        assertEquals(listFilterByPlaceA.size(), 2);
    }

    @Test
    public void getListPlaceAvailableWithSuccess (){
        //Given : get a meeting : hour : 8.30:9.15 - place : B && set calendar at the same hour
        Meeting meeting1 = UtilsMeetingTest.getAMeeting();

        Calendar calStartHour = (Calendar) Calendar.getInstance().clone();
        calStartHour.set(Calendar.HOUR_OF_DAY, 8);
        calStartHour.set(Calendar.MINUTE, 15);

        Calendar calEndHour = (Calendar) Calendar.getInstance().clone();
        calEndHour.set(Calendar.HOUR_OF_DAY, 9);
        calEndHour.set(Calendar.MINUTE, 10);
        //When : add the meeting in list
        mApiService.addMeeting(meeting1.getId(), meeting1.getSubject(), meeting1.getPlace(), meeting1.getContributors(), meeting1.getStartHourOfMeeting(), meeting1.getEndHourOfMeeting());
        //Then : check if the place of the meeting is not available
        assertFalse(mApiService.getListPlaceAvailable(calStartHour, calEndHour).contains("B"));
    }
}
