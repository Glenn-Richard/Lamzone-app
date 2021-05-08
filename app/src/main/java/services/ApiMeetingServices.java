package services;

import android.annotation.SuppressLint;

import com.example.lamzone.Meeting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static services.ApiServiceGenerator.generateMeetings;

public class ApiMeetingServices implements ApiSerivces {

    private List<Meeting> meetings = generateMeetings();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


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
    public ArrayList<Meeting> filterMeetingByRoom(String room, List<Meeting> meetings){
        ArrayList<Meeting> sortedMeetings = new ArrayList<>();
        for (int i=0;i<meetings.size();i++){
            if (meetings.get(i).getLocation().getName().equals(room)){
                sortedMeetings.add(meetings.get(i));
            }
        }
        return sortedMeetings;
    }

    @Override
    public List<Meeting> filterByDate(List<Meeting> meetings, Calendar cal){

        String DateString = dateFormat.format(cal.getTime());
        String DateComp;
        ArrayList<Meeting> meetingsFilter = new ArrayList<>();

        for(Meeting meeting : meetings){
            DateComp = dateFormat.format(meeting.getTimestamp());
            if (DateComp.equals(DateString)){
                meetingsFilter.add(meeting);
            }
        }
        return meetingsFilter;


    }
}
