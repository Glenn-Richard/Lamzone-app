package com.example.lamzone;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Meeting implements Parcelable, Comparable<Meeting> {

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };
    //VARIABLES
    private int id;
    private long timestamp;
    private int period;
    private String subject;
    private List<String> emails;
    private Room location;

    //CONSTRUCTORS
    public Meeting() {
    }


    public Meeting(int id, long timestamp, int period, String subject, List<String> emails, Room location) {
        this.id = id;
        this.timestamp = timestamp;
        this.period = period;
        this.subject = subject;
        this.emails = emails;
        this.location = location;
    }

    protected Meeting(Parcel in) {
        id = in.readInt();
        timestamp = in.readLong();
        period = in.readInt();
        subject = in.readString();
        emails = in.createStringArrayList();
        location = in.readParcelable(Room.class.getClassLoader());
    }

    //GETTER AND SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
        this.location = location;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(timestamp);
        dest.writeInt(period);
        dest.writeString(subject);
        dest.writeStringList(emails);
        dest.writeParcelable(location, flags);
    }

    @Override
    public int compareTo(Meeting o) {
        return 0;
    }
}

