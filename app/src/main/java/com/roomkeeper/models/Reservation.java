package com.roomkeeper.models;

import com.google.gson.annotations.Expose;

public class Reservation {


    private long id;
    @Expose
    private String roomID;
    @Expose
    private String nickname;
    @Expose
    private long startTime;
    @Expose
    private long endTime;

    public Reservation(long id, String roomID, String nickname, long startTime, long endTime) {
        this.id = id;
        this.roomID = roomID;
        this.nickname = nickname;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getNickname() {
        return nickname;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
