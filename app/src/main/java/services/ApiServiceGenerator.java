package services;

import com.example.lamzone.Meeting;
import com.example.lamzone.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ApiServiceGenerator {

    //Liste d'emails
    public static List<String> EMAILS = Arrays.asList(
            "toto@gmail.com",
            "anissa.b@yahoo.fr",
            "maxime.dumont@gmail.com",
            "jp.martin@hotmail.com",
            "lisa.thomas@laposte.fr"
    );

    //Liste de salles de reunion
    public static List<Room> ROOMS = Arrays.asList(
            new Room(0,"Reunion A"),
            new Room(1,"Reunion B"),
            new Room(2,"Reunion C")
    );

    //Liste de meeting
    public static List<Meeting> MEETINGS = Arrays.asList(
      new Meeting(000,1618491600,"Brainstorming",EMAILS, ROOMS.get(0)),
      new Meeting(001,1618484400,"Planning",EMAILS, ROOMS.get(2)),
      new Meeting(002,1618504200,"Budget",EMAILS, ROOMS.get(1)),
      new Meeting(003,1618504200,"Fusion",EMAILS, ROOMS.get(2)),
      new Meeting(004,1618565400,"Press",EMAILS, ROOMS.get(0))
    );

    static List<Meeting> generateMeetings(){return new ArrayList<>(MEETINGS);}
}
