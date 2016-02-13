package com.roomkeeper.models;

import com.google.gson.annotations.Expose;

public class Reservation {


    private long id;
    @Expose
    private String roomID;
    @Expose
    private String nickname;
    @Expose
    private String startTime;
    @Expose
    private String endTime;

    public String getRoomID() {
        return roomID;
    }

    public String getNickname() {
        return nickname;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
