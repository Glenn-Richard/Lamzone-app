package services;

import android.annotation.SuppressLint;
import android.widget.DatePicker;

import com.example.lamzone.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static services.ApiServiceGenerator.generateMeetings;

public class ApiMeetingServices implements ApiSerivces {

    private List<Meeting> meetings = generateMeetings();


    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting){
        getMeetings().remove(meeting);
    }

    @Override
    public void addMeeting(Meeting meeting){
        getMeetings().add(meeting);
    }

    @Override
    public java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
