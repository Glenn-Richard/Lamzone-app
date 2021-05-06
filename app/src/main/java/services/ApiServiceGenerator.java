package services;

import android.os.Parcelable;

import com.example.lamzone.Meeting;
import com.example.lamzone.R;
import com.example.lamzone.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ApiServiceGenerator implements Parcelable {

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
            new Room(0,"Reunion A",R.mipmap.blue),
            new Room(1,"Reunion B", R.mipmap.orange),
            new Room(2,"Reunion C", R.mipmap.magenta)
    );

    //Liste de meeting
    public static List<Meeting> MEETINGS = Arrays.asList(
      new Meeting(000,1618504200000L,"Brainstorming",EMAILS, ROOMS.get(0)),
      new Meeting(001,1618484400000L,"Planning",EMAILS, ROOMS.get(2)),
      new Meeting(002,1618491600000L,"Budget",EMAILS, ROOMS.get(1)),
      new Meeting(003,1618504200000L,"Fusion",EMAILS, ROOMS.get(2)),
      new Meeting(004,1618565400000L,"Press",EMAILS, ROOMS.get(0))
    );

    static List<Meeting> generateMeetings(){return new ArrayList<>(MEETINGS);}
}
