package com.delombaertdamien.mareu.controller.Activity.ui;

import android.app.Activity;

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

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.delombaertdamien.mareu.controller.Activity.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SortMeetingTest {

    private int ITEMS_COUNT = 0;


    private class MyTestRule<T extends Activity> extends ActivityTestRule<T> {

        MyTestRule(Class<T> activityClass) {
            super(activityClass);
        }

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();

            MeetingApiService Api = DI.getNewInstanceApiService();

            List<Meeting> mListMeeting = UtilsMeeting.getAListOfMeetingNotSorted();
            for(int i = 0; i < mListMeeting.size(); i++) {
                Api.addMeeting(mListMeeting.get(i).getId(), mListMeeting.get(i).getSubject(), mListMeeting.get(i).getPlace(), mListMeeting.get(i).getContributors(), mListMeeting.get(i).getStartHourOfMeeting(), mListMeeting.get(i).getEndHourOfMeeting());
            }
        }
    }

    @Rule
    public final MyTestRule<MainActivity> mActivityTestRule = new MyTestRule<>(MainActivity.class);

    @Before
    public void setUp(){

        MeetingApiService mMApiService = DI.getMeetingApiService();

        ITEMS_COUNT = mMApiService.getMeetings().size();
    }

    @Test
    public void SortListByPlaceTest (){
        // Given : Check count of list not filtered
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(ITEMS_COUNT));

        // When : Apply the filter
        onView(ViewMatchers.withId(R.id.search_filter_menu_activity_main)).perform(click());
        onView(allOf(withId(R.id.title), withText("Par local"))).perform(click());
        onView(allOf(withId(R.id.title), withText("Local A"))).perform(click());

        //Then : check count of the view
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(ITEMS_COUNT - 1));
    }

    @Test
    public void SortListByHourTest (){
        // Given : Check count of list not filtered
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(ITEMS_COUNT));

        // When : Apply the filter
        onView(ViewMatchers.withId(R.id.search_filter_menu_activity_main)).perform(click());
        onView(allOf(withId(R.id.title), withText("Par heure"))).perform(click());
        onView(allOf(withId(R.id.title), withText("8H"))).perform(click());

        //Then : check count of the view
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(ITEMS_COUNT - 2));

    }
}
