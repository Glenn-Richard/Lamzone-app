package services;

import com.example.lamzone.Meeting;
import com.example.lamzone.Room;

import java.util.Arrays;
import java.util.List;

public class ApiServiceGenerator {


    public List<Meeting> generateMeetings(){return MEETINGS;}

    //Liste d'emails
    List<String> EMAILS = Arrays.asList(
            "toto@gmail.com",
            "anissa.b@yahoo.fr",
            "maxime.dumont@gmail.com",
            "jp.martin@hotmail.com",
            "lisa.thomas@laposte.fr"
    );

    //Liste de meeting
    List<Meeting> MEETINGS = Arrays.asList(
      new Meeting(000,1618491600,"Brainstorming",EMAILS),
      new Meeting(001,1618484400,"Planning",EMAILS),
      new Meeting(002,1618504200,"Budget",EMAILS),
      new Meeting(003,1618504200,"Fusion",EMAILS),
      new Meeting(004,1618565400,"Press",EMAILS)
    );

    //Liste de salles de reunion
    List<Room> ROOMS = Arrays.asList(
            new Room(0,"Reunion A"),
            new Room(1,"Reunion B"),
            new Room(2,"Reunion C")
    );
}
