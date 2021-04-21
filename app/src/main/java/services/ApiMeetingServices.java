package services;

import android.annotation.SuppressLint;

import com.example.lamzone.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    public String getPickerDate(String day, String month, String year) {

        switch (day){
            case "1":
                day = "01";
            break;
            case "2":
                day = "02";
            break;
            case "3":
                day = "03";
            break;
            case "4":
                day = "04";
            break;
            case "5":
                day = "05";
            break;
            case "6":
                day = "06";
            break;
            case "7":
                day = "07";
            break;
            case "8":
                day = "08";
            break;
            case "9":
                day = "09";
            break;
        }
        String mois = "";
        switch (month){
            case "1":
                mois = "01";
                break;
                case "2":
                mois = "02";
                break;
                case "3":
                mois = "03";
                break;
                case "4":
                mois = "04";
                break;
                case "5":
                mois = "05";
                break;
                case "6":
                mois = "06";
                break;
                case "7":
                mois = "07";
                break;
                case "8":
                mois = "08";
                break;
                case "9":
                mois = "09";
                break;
                case "10":
                mois = "10";
                break;
                case "11":
                mois = "11";
                break;
                case "12":
                mois = "12";
                break;
        }
        String date = mois+"-"+day+"-"+year;

        return date;
    }
    @Override
    public String getPickerTime(String hour, String minute){
        switch (hour){
            case "0":
                hour = "00";
            break;
            case "1":
                hour = "01";
            break;
            case "2":
                hour = "02";
            break;
            case "3":
                hour = "03";
            break;
            case "4":
                hour = "04";
            break;
            case "5":
                hour = "05";
            break;
            case "6":
                hour = "06";
            break;
            case "7":
                hour = "07";
            break;
            case "8":
                hour = "08";
            break;
            case "9":
                hour = "09";
            break;
        }
        switch (minute){
            case "0":
                minute = "00";
                break;
            case "1":
                minute = "01";
                break;
            case "2":
                minute = "02";
                break;
            case "3":
                minute = "03";
                break;
            case "4":
                minute = "04";
                break;
            case "5":
                minute = "05";
                break;
            case "6":
                minute = "06";
                break;
            case "7":
                minute = "07";
                break;
            case "8":
                minute = "08";
                break;
            case "9":
                minute = "09";
                break;
        }

        String time = hour+":"+minute+":00";
        return time;
    }
}
