package com.delombaertdamien.mareu.controller.Activity.ui;

import android.app.Activity;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Activity.MainActivity;
import com.delombaertdamien.mareu.controller.Activity.utils.DeleteViewAction;
import com.delombaertdamien.mareu.controller.Activity.utils.UtilsMeeting;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static com.delombaertdamien.mareu.controller.Activity.utils.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
public class DeleteMeetingTest {

    private int ITEMS_COUNT = 0;

    public class MyTestRule<T extends Activity> extends ActivityTestRule<T> {

        MyTestRule(Class<T> activityClass) {
            super(activityClass);
        }

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();

            MeetingApiService Api = DI.getNewInstanceApiService();

            Meeting mMeeting = UtilsMeeting.getAMeeting();
            Api.addMeeting(mMeeting.getId(), mMeeting.getSubject(), mMeeting.getPlace(), mMeeting.getContributors(), mMeeting.getStartHourOfMeeting(), mMeeting.getEndHourOfMeeting());
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
    public void deleteMeetingTest (){
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity))
               .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(ITEMS_COUNT - 1));
    }
}
