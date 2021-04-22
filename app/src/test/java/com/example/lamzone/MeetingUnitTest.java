package com.example.lamzone;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import services.ApiMeetingServices;
import services.ApiSerivces;
import services.ApiServiceGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MeetingUnitTest {

    ApiSerivces apiSerivces;

    @Before
    public void setup(){
        apiSerivces = new ApiMeetingServices();
    }

    @Test
    public void getMeetingWithSuccess(){
        List<Meeting> meetings = apiSerivces.getMeetings();
        List<Meeting> comp = ApiServiceGenerator.MEETINGS;
        assertEquals(meetings,comp);
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
    public void getPickerDateWithSuccess(){
        String day = "1", month = "2";
        String date = apiSerivces.getPickerDate(day,month,"2021");
        assertEquals(date,"02-01-2021");
    }

    @Test
    public void getPickerTimeWithSuccess(){
        String hour = "0", minute = "9";
        String date = apiSerivces.getPickerTime(hour,minute);
        assertEquals(date,"00:09:00");
    }
}