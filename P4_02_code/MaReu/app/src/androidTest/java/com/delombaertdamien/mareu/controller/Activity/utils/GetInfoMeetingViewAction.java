package com.delombaertdamien.mareu.controller.Activity.utils;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.delombaertdamien.mareu.R;

import org.hamcrest.Matcher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GetInfoMeetingViewAction implements ViewAction {

    private String place;
    private Calendar calStart;
    private String primaryText;
    private String secondaryText;

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Get place of meeting";
    }

    @Override
    public void perform(UiController uiController, View view) {

        String textMeeting = ((TextView)view.findViewById(R.id.item_meeting_list_name)).getText().toString();

        primaryText = ((TextView) view.findViewById(R.id.item_meeting_list_name)).getText().toString();
        secondaryText = ((TextView) view.findViewById(R.id.item_meeting_list_name_participant)).getText().toString();
        //Place
        place = textMeeting.substring(6,7);
        //Hour
        calStart = (Calendar)format.getCalendar().clone();

        int startHour = Integer.parseInt(textMeeting.substring(10,12));
        int startMin = Integer.parseInt(textMeeting.substring(13,15));

        int endHour = Integer.parseInt(textMeeting.substring(18,20));
        int endMin = Integer.parseInt(textMeeting.substring(21,23));

        calStart.set(Calendar.HOUR_OF_DAY, startHour);
        calStart.set(Calendar.MINUTE, startMin);
    }

    public String getPrimaryText (){
        return primaryText;
    }
    public String getSecondaryText (){
        return secondaryText;
    }
}
