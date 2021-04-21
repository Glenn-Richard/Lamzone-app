package com.example.lamzone;

import android.content.Context;
import android.view.View;

import androidx.annotation.ContentView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import services.ApiMeetingServices;
import services.ApiSerivces;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.*;

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
            new ActivityTestRule(ListActivity.class);

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
        onView(withId(R.id.list_item_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        View button = view.findViewById(R.id.item_list_user_delete_button);
                        button.performClick();
                    }
                }));
        onView(allOf(isDisplayed(),withId(R.id.list_item_rv)))
                .check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT-1));
    }

    @Test
    public void checkMeetingIsAppended(){
        onView(allOf(isDisplayed(),withId(R.id.list_item_rv)))
                .check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT));
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.reunionA)).perform(click());
        onView(withId(R.id.edittext)).perform(replaceText("Toto"));
        onView(withId(R.id.submit)).perform(click());
        onView(allOf(isDisplayed(),withId(R.id.list_item_rv)))
                .check(RecyclerViewItemCountAssertion.withItemCount(ITEM_COUNT+1));
    }

}