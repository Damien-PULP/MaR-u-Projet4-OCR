package com.delombaertdamien.mareu.controller.Activity.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Activity.MainActivity;
import com.delombaertdamien.mareu.controller.Activity.utils.GetInfoMeetingViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.delombaertdamien.mareu.controller.Activity.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateMeetingTest {

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createMeetingTest() {

        //Launch the create meeting activity
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.floating_action_button_add_meeting),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frame_layout_activity_main),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        //Set subject : subject
        String subject = "subject";
        ViewInteraction textInputEditText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.configure_activity_edit_text_subject),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText.perform(replaceText(subject), closeSoftKeyboard());

        //Set contributor : contributor@lamzone.com
        String contributor = "contributor@lamzone.com";
        String mMeetingSecondaryText = " " + contributor;
        ViewInteraction textInputEditText2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.configure_activity_edit_text_contributor),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText(contributor), closeSoftKeyboard());

        //Valid contributor
        onView(allOf(
                withId(R.id.text_input_end_icon),
                withContentDescription("edit_text_contributor")
                )
        ).perform(click());

        //Set clock : actual clock
        Calendar startHourCal = (Calendar)format.getCalendar().clone();
        Calendar endHourCal = (Calendar)format.getCalendar().clone();
        endHourCal.add(Calendar.MINUTE, 45);

        String hour = format.format(startHourCal.getTime()) + " - " + format.format(endHourCal.getTime());

        onView(withId(R.id.configure_activity_button_set_start_clock)).perform(click());

        onView(withId(android.R.id.button1)).check(matches(isDisplayed())).perform(click());

        //Set place : A
        String place = "A";
        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.configure_activity_spinner_place),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView.perform(click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.configure_activity_spinner_place),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView2.perform(replaceText(place), closeSoftKeyboard());

        //Valid meeting
        onView(withId(R.id.configure_activity_edit_text_valid_meeting)).perform(click());

        //Check information on item create
        onView(ViewMatchers.withId(R.id.list_meeting_main_activity)).check(withItemCount(1));

        GetInfoMeetingViewAction getInfo = new GetInfoMeetingViewAction();

        onView(ViewMatchers.withId(R.id.list_meeting_main_activity))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, getInfo));


        String mMeetingPrimaryText = "Local " + place + " - " + hour + " , " + subject;

        String primaryText = getInfo.getPrimaryText();
        String secondaryText = getInfo.getSecondaryText();

        //Check if the information is true
        assertEquals(primaryText, (mMeetingPrimaryText));
        assertEquals(secondaryText, (mMeetingSecondaryText));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
