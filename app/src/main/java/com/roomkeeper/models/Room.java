package com.roomkeeper.models;

import java.util.List;

public class Room {

    private final String image;
    private String title;
    private String description;
    private String status;

    private List<Reservation> reservations;

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
}
