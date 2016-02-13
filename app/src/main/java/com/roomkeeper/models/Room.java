package com.roomkeeper.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Room implements Serializable {

    private final String image;
    private String title;
    private String description;
    private String status;
    private int time;

    public Room(String title, String description) {
        this.title = title;
        this.description = description;
        this.image = "http://ryanreporting.com/wp-content/uploads/2013/10/Conference-Room-Rental-in-Orlando-Orange-County-and-Brevard-County-Florida.jpg";
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm-ss", Locale.getDefault());
        String time = sdf.format(new Date(System.currentTimeMillis()));

        return time;
    }
}
