package com.example.lamzone;

public class Room {

    //VARIABLES

    private int id;
    private String name;

    //CONSTRUCTORS
    public Room(){}

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
