package com.roomkeeper.models;

import java.util.List;

public class Room {

    private String title;
    private String description;
    private String status;

    private List<Reservation> reservations;

    public Room(String title, String description) {
        this.title = title;
        this.description = description;
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
}
