package com.delombaertdamien.mareu.controller.Activity.ui;

import android.app.Activity;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Activity.MainActivity;
import com.delombaertdamien.mareu.controller.Activity.utils.UtilsMeeting;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.delombaertdamien.mareu.controller.Activity.utils.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
public class ShowMeetingTest {

    private MeetingApiService mApiService;
    private int ITEMS_COUNT = 0;
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private class MyTestRule<T extends Activity> extends ActivityTestRule<T> {

        MyTestRule(Class<T> activityClass) {
            super(activityClass);
        }

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();

            MeetingApiService Api = DI.getMeetingApiService();

            List<Meeting> mListMeeting = UtilsMeeting.getAListOfMeetingNotSorted();
            for(int i = 0; i < mListMeeting.size(); i++) {
                Api.addMeeting(mListMeeting.get(i).getId(), mListMeeting.get(i).getSubject(), mListMeeting.get(i).getPlace(), mListMeeting.get(i).getContributors(), mListMeeting.get(i).getStartHourOfMeeting(), mListMeeting.get(i).getEndHourOfMeeting());
            }
        }
    }

    @Rule
    public MyTestRule<MainActivity> mActivityTestRule = new MyTestRule<>(MainActivity.class);

    @Before
    public void setUp (){

        mApiService = DI.getMeetingApiService();
        ITEMS_COUNT = mApiService.getMeetings().size();
    }

    @Test
    public void showFragmentDetail (){

        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(ITEMS_COUNT));

        onView(ViewMatchers.withId(R.id.list_meeting_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(ViewMatchers.withId(R.id.fragment_detail)).check(matches(isDisplayed()));

        Meeting mMeetingDisplay = mApiService.getMeetingToDisplay();

        onView(ViewMatchers.withId(R.id.fragment_detail_subject)).check(matches(withText(mMeetingDisplay.getSubject())));

        String listContributor = "";
        List<String> listContributorOfMeeting = mMeetingDisplay.getContributors();

        for(int i = 0; i < listContributorOfMeeting.size(); i++){
            listContributor += listContributorOfMeeting.get(i);
            if(i < listContributorOfMeeting.size() - 1){
                listContributor += "\n";
            }
        }
        onView(ViewMatchers.withId(R.id.fragment_detail_contributor)).check(matches(withText(listContributor)));
        onView(ViewMatchers.withId(R.id.fragment_detail_place)).check(matches(withText("Local " + mMeetingDisplay.getPlace())));

        String hour = format.format(mMeetingDisplay.getStartHourOfMeeting().getTime()) + " - " +format.format(mMeetingDisplay.getEndHourOfMeeting().getTime());
        onView(ViewMatchers.withId(R.id.fragment_detail_hour)).check(matches(withText(hour)));
    }

}
