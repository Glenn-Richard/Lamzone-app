package services;

import com.example.lamzone.Meeting;

import java.util.List;

public interface ApiSerivces {

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);
}
