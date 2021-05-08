package com.example.lamzone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<Meeting> meetings = Arrays.asList(
                new Meeting(00,1680000000L,"toto1",ApiServiceGenerator.EMAILS,ApiServiceGenerator.ROOMS.get(0)),
                new Meeting(01,1680000000L,"toto2",ApiServiceGenerator.EMAILS,ApiServiceGenerator.ROOMS.get(1)),
                new Meeting(02,1680000000L,"toto3",ApiServiceGenerator.EMAILS,ApiServiceGenerator.ROOMS.get(0))
        );
        List<Meeting> sorted = new ArrayList<>();
        sorted.addAll(apiSerivces.filterMeetingByRoom("Reunion A",meetings));
        Assert.assertTrue(sorted.contains(meetings.get(0)) && sorted.contains(meetings.get(2)));
    }
    @Test
    public void sortMeetingsByDateWithSuccess(){
        List<Meeting> meetings = Arrays.asList(
                new Meeting(00,1680000000000L,"toto1",ApiServiceGenerator.EMAILS,ApiServiceGenerator.ROOMS.get(0)),
                new Meeting(01,1618504200000L,"toto2",ApiServiceGenerator.EMAILS,ApiServiceGenerator.ROOMS.get(1)),
                new Meeting(02,1618565400000L,"toto3",ApiServiceGenerator.EMAILS,ApiServiceGenerator.ROOMS.get(0))
        );
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,16);
        cal.set(Calendar.MONTH,3);
        cal.set(Calendar.YEAR,2021);
        List<Meeting> comp = apiSerivces.filterByDate(meetings, cal);
        Assert.assertTrue(comp.contains(meetings.get(2)));
    }
}