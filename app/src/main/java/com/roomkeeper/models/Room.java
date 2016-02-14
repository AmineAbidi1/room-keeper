package com.roomkeeper.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Room implements Serializable {

    private long id;
    private String title;
    private String description;
    private String image;
    private Status status;
    private String capacity;
    private int time;

    public Room(String title, String description) {
        this.title = title;
        this.description = description;
        this.image = "http://ryanreporting.com/wp-content/uploads/2013/10/Conference-Room-Rental-in-Orlando-Orange-County-and-Brevard-County-Florida.jpg";
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Status getStatus() {
        if (status != null) {
            return status;
        }
        return Status.FREE;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



    public String getCapacity() {
        return capacity;
    }
}
