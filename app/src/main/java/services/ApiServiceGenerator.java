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
      new Meeting(0,1618504200000L, 3600000, "Brainstorming",EMAILS, ROOMS.get(0)),
      new Meeting(1,1618484400000L, 3600000, "Planning",EMAILS, ROOMS.get(2)),
      new Meeting(2,1618491600000L, 3600000, "Budget",EMAILS, ROOMS.get(1)),
      new Meeting(3,1618504200000L, 3600000, "Fusion",EMAILS, ROOMS.get(2)),
      new Meeting(4,1618565400000L, 3600000, "Press",EMAILS, ROOMS.get(0))
    );

    static List<Meeting> generateMeetings(){return new ArrayList<>(MEETINGS);}
}
