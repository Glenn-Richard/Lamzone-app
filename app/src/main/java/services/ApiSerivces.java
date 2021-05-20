package services;

import com.example.lamzone.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface ApiSerivces {

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);

    ArrayList<Meeting> filterMeetingByRoom(String room, List<Meeting> meetings);

    List<Meeting> filterByDate(List<Meeting> meetings, Calendar cal);

}
