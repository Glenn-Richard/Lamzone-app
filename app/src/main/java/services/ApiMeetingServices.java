package services;

import android.annotation.SuppressLint;

import com.example.lamzone.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public long getSpinnerTime(String day, String month, String year, String hour, String minute) throws ParseException {
        String mois = "";
        switch (month){
            case "Janv":
                mois = "01";
                break;
                case "Fev":
                mois = "02";
                break;
                case "Mars":
                mois = "03";
                break;
                case "Avril":
                mois = "04";
                break;
                case "Mai":
                mois = "05";
                break;
                case "Juin":
                mois = "06";
                break;
                case "Juil":
                mois = "07";
                break;
                case "Août":
                mois = "08";
                break;
                case "Sept":
                mois = "09";
                break;
                case "Oct":
                mois = "10";
                break;
                case "Nov":
                mois = "11";
                break;
                case "Dec":
                mois = "12";
                break;
        }
        String date1 = "";
        date1 = year+"-"+mois+"-"+day+" "+hour+":"+minute+":00";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");
        Date lFromDate1 = datetimeFormatter1.parse(date1);

        return Objects.requireNonNull(lFromDate1).getTime();
    }
}
