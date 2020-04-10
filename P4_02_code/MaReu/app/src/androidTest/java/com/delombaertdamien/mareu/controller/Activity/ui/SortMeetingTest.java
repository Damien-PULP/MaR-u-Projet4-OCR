package com.delombaertdamien.mareu.controller.Activity.ui;

import android.app.Activity;
import android.util.Log;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Activity.MainActivity;
import com.delombaertdamien.mareu.controller.Activity.utils.GetInfoMeetingViewAction;
import com.delombaertdamien.mareu.controller.Activity.utils.UtilsMeeting;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.delombaertdamien.mareu.controller.Activity.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class SortMeetingTest {

    private MeetingApiService mApiService;
    private MainActivity activity;
    private int ITEMS_COUNT = 0;


    private class MyTestRule<T extends Activity> extends ActivityTestRule<T> {

        public MyTestRule(Class<T> activityClass) {
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
    public void setUp(){

        activity = mActivityTestRule.getActivity();
        mApiService = DI.getMeetingApiService();

        ITEMS_COUNT = mApiService.getMeetings().size();
    }

    @Test
    public void SortListByPlaceTest (){
        // Given : Get the list not sorted
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(ITEMS_COUNT));

        List<String> placeOfMeetings = new ArrayList<>();

        for(int i = 0; i < ITEMS_COUNT; i++) {

            GetInfoMeetingViewAction vAction = new GetInfoMeetingViewAction();
            onView(ViewMatchers.withId(R.id.list_meeting_main_activity))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, vAction));
            String place = vAction.getPlace();
            placeOfMeetings.add(place);
        }

        // When : Sort the list and get the list sorted
        onView(ViewMatchers.withId(R.id.search_filter_menu_activity_main)).perform(click());
        onView(allOf(withId(R.id.title), withText("Par local"))).perform(click());

        Collections.sort(placeOfMeetings, (t0, t1) -> t0.compareToIgnoreCase(t1));

        List<String> placeOfMeetingsSortedByPlace = new ArrayList<>();

        for(int i = 0; i < ITEMS_COUNT; i++) {

            GetInfoMeetingViewAction vAction = new GetInfoMeetingViewAction();
            onView(ViewMatchers.withId(R.id.list_meeting_main_activity))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, vAction));
            String place = vAction.getPlace();
            placeOfMeetingsSortedByPlace.add(place);
        }
        //Then : compare the two lists
        assertThat(placeOfMeetings, is(placeOfMeetingsSortedByPlace));

    }

    @Test
    public void SortListByHourTest (){
        // Given : Get the list not sorted
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(ITEMS_COUNT));

        List<Calendar> calOfMeetings = new ArrayList<>();

        for(int i = 0; i < ITEMS_COUNT; i++) {

            GetInfoMeetingViewAction vAction = new GetInfoMeetingViewAction();
            onView(ViewMatchers.withId(R.id.list_meeting_main_activity))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, vAction));
            Calendar cal = vAction.getStartCalendar();
            calOfMeetings.add(cal);
        }

        // When : Sort the list and get the list sorted
        onView(ViewMatchers.withId(R.id.search_filter_menu_activity_main)).perform(click());
        onView(allOf(withId(R.id.title), withText("Par heure"))).perform(click());

        Collections.sort(calOfMeetings, Calendar::compareTo);

        List<Calendar> calOfMeetingsSortedByHour = new ArrayList<>();

        for(int i = 0; i < ITEMS_COUNT; i++) {

            GetInfoMeetingViewAction vAction = new GetInfoMeetingViewAction();
            onView(ViewMatchers.withId(R.id.list_meeting_main_activity))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, vAction));
            Calendar cal = vAction.getStartCalendar();
            calOfMeetingsSortedByHour.add(cal);
        }
        //Then : compare the two lists
        assertThat(calOfMeetings, is(calOfMeetingsSortedByHour));

    }
}
