package com.roomkeeper.models;

public class RoomStatus {

    private long roomID;
    private int noiseLevel;
    private int proximity;
    private int luminocity;
    private int humidity;
    private int temperature;
    private Reservation currentReservation;
    private Reservation nextReservation;

    public long getRoomID() {
        return roomID;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }

    public int getProximity() {
        return proximity;
    }

    public int getLuminocity() {
        return luminocity;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public long getReservationTimeLeft() {
        return getCurrentReservation().getEndTime() - System.currentTimeMillis();
    }

    public long getOpenTimeLeft() {
        if (getCurrentReservation() != null) {
            return 0;
        }

        if (getNextReservation() != null) {
            return getNextReservation().getStartTime() - System.currentTimeMillis();
        } else {
            return 0;
        }

    }

    public Reservation getCurrentReservation() {
        return currentReservation;
    }

    public Reservation getNextReservation() {
        return nextReservation;
    }
}
