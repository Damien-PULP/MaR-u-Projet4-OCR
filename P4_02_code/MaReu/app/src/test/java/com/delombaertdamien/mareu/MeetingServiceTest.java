package com.delombaertdamien.mareu;

import android.graphics.Color;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService mApiService;

    @Before
    public void setup (){
        mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void addMeetingWithSuccess (){

        Random rnd = new Random();
        Integer id = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        String subject = "Sujet";
        List<String> contributors = Arrays.asList("contributor@lamzlo.com");
        String place = "B";

        Calendar calStartHour = (Calendar) Calendar.getInstance().clone();
        calStartHour.set(Calendar.HOUR_OF_DAY, 8);
        calStartHour.set(Calendar.MINUTE, 30);

        Calendar calEndHour = (Calendar) Calendar.getInstance().clone();
        calEndHour.set(Calendar.HOUR_OF_DAY, 9);
        calEndHour.set(Calendar.MINUTE, 15);

        Meeting meeting1 = new Meeting(id, subject, contributors, calStartHour, calEndHour, place);
        mApiService.addMeeting(id, subject, place, contributors, calStartHour, calEndHour);
        Meeting meeting2 = mApiService.getMeetings().get(0);

        assertEquals(meeting1, meeting2);

    }
}
