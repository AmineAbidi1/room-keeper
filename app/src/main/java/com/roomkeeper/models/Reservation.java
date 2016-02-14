package com.roomkeeper.models;

import com.google.gson.annotations.Expose;

public class Reservation {


    private long id;
    @Expose
    private long roomID;
    @Expose
    private String nickname;

    private String sparkID;
    private String phoneNO;

    @Expose
    private long startTime;
    @Expose
    private long endTime;

    public Reservation(long id, long roomID, String nickname, long startTime, long endTime) {
        this.id = id;
        this.roomID = roomID;
        this.nickname = nickname;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reservation(long roomID, String nickname, String phoneNO, String sparkID, long startTime, long endTime) {
        this.id = id;
        this.roomID = roomID;
        this.nickname = nickname;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getRoomID() {
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
