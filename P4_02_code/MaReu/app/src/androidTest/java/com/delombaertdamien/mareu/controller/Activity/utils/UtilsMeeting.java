package com.delombaertdamien.mareu.controller.Activity.utils;

import android.graphics.Color;
import android.util.Log;

import com.delombaertdamien.mareu.model.Meeting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class UtilsMeeting {

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public static Meeting getAMeeting (){
        Random rnd = new Random();
        Integer id = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        String subject = "Sujet";
        List<String> contributors = Collections.singletonList("contributor@lamzlo.com");
        String place = "B";

        Calendar calStartHour = (Calendar) format.getCalendar().clone();
        calStartHour.set(Calendar.HOUR_OF_DAY, 8);
        calStartHour.set(Calendar.MINUTE, 30);

        Calendar calEndHour = (Calendar) format.getCalendar().clone();
        calEndHour.set(Calendar.HOUR_OF_DAY, 9);
        calEndHour.set(Calendar.MINUTE, 15);

        return new Meeting(id, subject, contributors, calStartHour, calEndHour, place);
    }

    public static List<Meeting> getAListOfMeeting (){

        List<Meeting> list = new ArrayList<>();

        Random rnd = new Random();
        Integer id = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Integer id1 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Integer id2 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        String subject = "Sujet";
        String subject1 = "Sujet1";
        String subject2 = "Sujet2";

        List<String> contributors = Collections.singletonList("contributor@lamzlo.com");
        List<String> contributors1 = Collections.singletonList("contributor1@lamzlo.com");
        List<String> contributors2 = Collections.singletonList("contributor2@lamzlo.com");

        String place = "B";
        String place1 = "C";
        String place2 = "D";

        Calendar calStartHour = (Calendar) format.getCalendar().clone();
        calStartHour.set(Calendar.HOUR_OF_DAY, 8);
        calStartHour.set(Calendar.MINUTE, 30);

        Calendar calEndHour = (Calendar) format.getCalendar().clone();
        calEndHour.set(Calendar.HOUR_OF_DAY, 9);
        calEndHour.set(Calendar.MINUTE, 15);

        Calendar calStartHour1 = (Calendar) format.getCalendar().clone();
        calStartHour1.set(Calendar.HOUR_OF_DAY, 10);
        calStartHour1.set(Calendar.MINUTE, 40);

        Calendar calEndHour1 = (Calendar) format.getCalendar().clone();
        calEndHour1.set(Calendar.HOUR_OF_DAY, 11);
        calEndHour1.set(Calendar.MINUTE, 30);

        Calendar calStartHour2 = (Calendar) format.getCalendar().clone();
        calStartHour2.set(Calendar.HOUR_OF_DAY, 11);
        calStartHour2.set(Calendar.MINUTE, 45);

        Calendar calEndHour2 = (Calendar) format.getCalendar().clone();
        calEndHour2.set(Calendar.HOUR_OF_DAY, 12);
        calEndHour2.set(Calendar.MINUTE, 30);

        Meeting meeting1 = new Meeting(id, subject, contributors, calStartHour, calEndHour, place);
        Meeting meeting2 = new Meeting(id1, subject1, contributors1, calStartHour1, calEndHour1, place1);
        Meeting meeting3 = new Meeting(id2, subject2, contributors2, calStartHour2, calEndHour2, place2);

        list.add(meeting1);
        list.add(meeting2);
        list.add(meeting3);

        return list;
    }

    public static List<Meeting> getAListOfMeetingNotSorted (){

        List<Meeting> list = new ArrayList<>();

        Random rnd = new Random();
        Integer id = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Integer id1 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Integer id2 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        String subject = "Sujet";
        String subject1 = "Sujet1";
        String subject2 = "Sujet2";

        List<String> contributors = Collections.singletonList("contributor@lamzlo.com");
        List<String> contributors1 = Collections.singletonList("contributor1@lamzlo.com");
        List<String> contributors2 = Collections.singletonList("contributor2@lamzlo.com");

        String place = "F";
        String place1 = "C";
        String place2 = "A";

        //Meeting1
        Calendar calStartHour = (Calendar) format.getCalendar().clone();
        calStartHour.set(Calendar.HOUR_OF_DAY, 18);
        calStartHour.set(Calendar.MINUTE, 30);

        Calendar calEndHour = (Calendar) format.getCalendar().clone();
        calEndHour.set(Calendar.HOUR_OF_DAY, 19);
        calEndHour.set(Calendar.MINUTE, 15);

        //Meeting2
        Calendar calStartHour1 = (Calendar) format.getCalendar().clone();
        calStartHour1.set(Calendar.HOUR_OF_DAY, 7);
        calStartHour1.set(Calendar.MINUTE, 10);

        Calendar calEndHour1 = (Calendar) format.getCalendar().clone();
        calEndHour1.set(Calendar.HOUR_OF_DAY, 8);
        calEndHour1.set(Calendar.MINUTE, 30);

        //Meeting3
        Calendar calStartHour2 = (Calendar) format.getCalendar().clone();
        calStartHour2.set(Calendar.HOUR_OF_DAY, 8);
        calStartHour2.set(Calendar.MINUTE, 30);

        Calendar calEndHour2 = (Calendar) format.getCalendar().clone();
        calEndHour2.set(Calendar.HOUR_OF_DAY, 9);
        calEndHour2.set(Calendar.MINUTE, 15);
        //------//
        Meeting meeting1 = new Meeting(id, subject, contributors, calStartHour, calEndHour, place);
        Meeting meeting2 = new Meeting(id1, subject1, contributors1, calStartHour1, calEndHour1, place1);
        Meeting meeting3 = new Meeting(id2, subject2, contributors2, calStartHour2, calEndHour2, place2);

        list.add(meeting1);
        list.add(meeting2);
        list.add(meeting3);

        return list;
    }
}
