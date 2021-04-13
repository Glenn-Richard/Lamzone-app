package services;

import com.example.lamzone.Meeting;

import java.text.ParseException;
import java.util.List;

public interface ApiSerivces {


    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);

    long getSpinnerTime(String day, String month, String year, String hour, String minute) throws ParseException;
}
