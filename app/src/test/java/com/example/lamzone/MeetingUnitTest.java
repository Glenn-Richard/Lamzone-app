package com.example.lamzone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import services.ApiMeetingServices;
import services.ApiSerivces;
import services.ApiServiceGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MeetingUnitTest{

    private ApiSerivces apiSerivces;

    @Before
    public void setup(){
        apiSerivces = new ApiMeetingServices();
    }

    @Test
    public void getMeetingsWithSuccess(){
        List<Meeting> meetings = new ArrayList<>();
        meetings.addAll(apiSerivces.getMeetings());
        assertEquals(ApiServiceGenerator.MEETINGS,meetings);
    }
    @Test
    public void deleteMeetingWithSuccess(){
        List<Meeting> meetings = apiSerivces.getMeetings();
        List<Meeting> comp = ApiServiceGenerator.MEETINGS;
        Meeting meeting = meetings.get(0);
        apiSerivces.deleteMeeting(meeting);
        assertEquals(meetings.size(),comp.size()-1);
        assertFalse(meetings.contains(meeting));
    }
    @Test
    public void addMeetingWithSuccess(){
        List<Meeting> meetings = apiSerivces.getMeetings();
        List<Meeting> comp = ApiServiceGenerator.MEETINGS;
        Meeting meeting = new Meeting();
        apiSerivces.addMeeting(meeting);
        assertEquals(meetings.size(),comp.size()+1);
        assertTrue(meetings.contains(meeting));
    }
    @Test
    public void sortMeetingsByRoomWithSuccess(){
        List<Meeting> meetings = new ArrayList<>();
        ListActivity instance = new ListActivity();
        meetings.addAll(instance.filterMeetingByRoom("Reunion A",apiSerivces.getMeetings()));
        assertEquals("Reunion A", meetings.get(0).getLocation().getName());
        assertEquals("Reunion A", meetings.get(1).getLocation().getName());
    }
    @Test
    public void sortMeetingsByDateWithSuccess(){
        ListActivity instance = new ListActivity();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,16);
        cal.set(Calendar.MONTH,3);
        cal.set(Calendar.YEAR,2021);
        List<Meeting> checking = new ArrayList<>();
        checking.add(ApiServiceGenerator.MEETINGS.get(4));
        List<Meeting> meetings = new ArrayList<>(
                instance.filterByDate(apiSerivces.getMeetings(), cal));
        Assert.assertEquals(checking,meetings);
    }
}