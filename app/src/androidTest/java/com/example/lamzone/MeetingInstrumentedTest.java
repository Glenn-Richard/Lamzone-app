package com.example.lamzone;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import services.ApiMeetingServices;
import services.ApiSerivces;
import services.ApiServiceGenerator;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.lamzone.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 */
@RunWith(AndroidJUnit4.class)
public class MeetingInstrumentedTest extends  RecyclerViewUtils{

    private ApiSerivces apiSerivces = new ApiMeetingServices();
    final private int ITEM_COUNT = apiSerivces.getMeetings().size();
    private ListActivity mActivity;

    @Rule
    public ActivityTestRule<ListActivity> mActivityRule =
            new ActivityTestRule<>(ListActivity.class);

    @Before
    public void setup(){
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, IsNull.notNullValue());
    }

    @Test
    public void checkItemsAreDisplayed(){
        onView(allOf(isDisplayed(),withId(R.id.list_item_rv))).check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT));
    }

    @Test
    public  void checkItemIsDelete(){
        onView(allOf(isDisplayed(),withId(R.id.list_item_rv)))
                .check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT));
        Meeting meeting = apiSerivces.getMeetings().get(0);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(meeting.getTimestamp());
        String time = DateFormat.format("dd/MM HH:mm",cal).toString();
        onView(withRecyclerView(R.id.list_item_rv)
                .atPositionOnView(0, R.id.item_list_user_top))
                .check(matches(withText(meeting.getLocation().getName()+" - "+time+" - "+ meeting.getSubject())));
        onView(withId(R.id.list_item_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(allOf(isDisplayed(),withId(R.id.list_item_rv)))
                .check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT-1));
        onView(withRecyclerView(R.id.list_item_rv)
                .atPositionOnView(0, R.id.item_list_user_top))
                .check(matches(Matchers.not(withText(meeting.getLocation().getName()+" - "+time+" - "+ meeting.getSubject()))));
    }

    @Test
    public void checkMeetingIsAppended(){
        onView(allOf(isDisplayed(),withId(R.id.list_item_rv)))
                .check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT));
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Réunion A"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Réunion A"))));
        onView(withId(R.id.edittext)).perform(replaceText("Toto"));
        onView(withId(R.id.datePicker))
                .perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(2021, 2, 10));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.timePicker))
                .perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(12,10));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.email_selector)).perform(replaceText("toto@gmail.com,glenn@lamzone.fr"));
        onView(withId(R.id.submit)).perform(click());
        onView(allOf(isDisplayed(),withId(R.id.list_item_rv)))
                .check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT+1));
    }

    @Test
    public void checkMeetingsSortByDate(){

        Meeting meeting = ApiServiceGenerator.MEETINGS.get(4);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(meeting.getTimestamp());
        String time = DateFormat.format("dd/MM HH:mm",cal).toString();
        onView(allOf(withId(R.id.list_item_rv),isDisplayed()))
                .check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT));
        onView(withId(R.id.filter)).perform(click());
        onView(withText(R.string.filtre_par_date)).perform(click());
        onView(isAssignableFrom(DatePicker.class))
                .perform(setDate(2021, 4, 16));
        onView(withText("OK")).perform(click());
        onView(allOf(withId(R.id.list_item_rv),isDisplayed()))
                .check(RecyclerViewItemCountAssertion.withItemCount(1));
        onView(withRecyclerView(R.id.list_item_rv)
                .atPositionOnView(0,R.id.item_list_user_top))
                .check(matches(withText(meeting.getLocation().getName()+" - "+time+" - "+ meeting.getSubject())));
    }
    @Test
    public void checkMeetingsSortByRoom(){
        Meeting meeting = ApiServiceGenerator.MEETINGS.get(0);
        Meeting meeting2 = ApiServiceGenerator.MEETINGS.get(4);
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.setTimeInMillis(meeting.getTimestamp());
        cal2.setTimeInMillis(meeting2.getTimestamp());
        String time = DateFormat.format("dd/MM HH:mm",cal).toString();
        String time2 = DateFormat.format("dd/MM HH:mm",cal2).toString();
        onView(allOf(withId(R.id.list_item_rv),isDisplayed()))
                .check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT));
        onView(withId(R.id.filter)).perform(click());
        onView(withText(R.string.filtre_par_salle)).perform(click());
        onView(withText("FILTRER")).perform(click());
        onView(allOf(withId(R.id.list_item_rv),isDisplayed()))
                .check(RecyclerViewItemCountAssertion.withItemCount(2));
        onView(withRecyclerView(R.id.list_item_rv)
                .atPositionOnView(0,R.id.item_list_user_top))
                .check(matches(withText(meeting.getLocation().getName()+" - "+time+" - "+ meeting.getSubject())));
        onView(withRecyclerView(R.id.list_item_rv)
                .atPositionOnView(1,R.id.item_list_user_top))
                .check(matches(withText(meeting2.getLocation().getName()+" - "+time2+" - "+ meeting2.getSubject())));
    }
}