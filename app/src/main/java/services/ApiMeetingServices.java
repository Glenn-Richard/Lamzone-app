package services;

import com.example.lamzone.Meeting;

import java.util.List;

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
}
