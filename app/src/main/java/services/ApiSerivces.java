package services;

import android.widget.DatePicker;

import com.example.lamzone.Meeting;

import java.text.ParseException;
import java.util.List;

public interface ApiSerivces {


    java.util.Date getDateFromDatePicker(DatePicker datePicker);

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);
}
