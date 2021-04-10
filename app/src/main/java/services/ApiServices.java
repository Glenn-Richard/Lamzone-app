package services;

import com.example.lamzone.Meeting;

import java.util.List;

public class ApiServices implements ApiSerivces {
    private List<Meeting> meetings = generateMeetings();

    @Override
    public List<Meeting> generateMeetings() {
        return meetings;
    }

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
