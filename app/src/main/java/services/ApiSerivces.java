package services;

import com.example.lamzone.Meeting;

import java.util.List;

public interface ApiSerivces {

    List<Meeting> generateMeetings();

    List<Meeting> getMeetings();

    void setMeetings(List<Meeting> meetings);
}
