package com.example.lamzone;


import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {

    //VARIABLES

    private int id;
    private String name;
    private int color;

    //CONSTRUCTORS
    public Room(){}

    public Room(int id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    protected Room(Parcel in) {
        id = in.readInt();
        name = in.readString();
        color = in.readInt();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getColor() { return color; }

    public void setColor(int color) { this.color = color; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(color);
    }


}
