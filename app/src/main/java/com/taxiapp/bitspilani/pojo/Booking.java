package com.taxiapp.bitspilani.pojo;

import com.google.firebase.Timestamp;

public class Booking {
    private String id;
    private String source;
    private String destination;
    private Timestamp journeyDate;
    private String journeyTIme;
    private String journetStartTime;
    private String journeyEndTIme;
    private String status;
    private String carType;

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Booking() {
    }

    public Booking(String id, String source, String destination, Timestamp journeyDate, String journeyTIme, String journetStartTime, String journeyEndTIme, String status, String carType) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.journeyDate = journeyDate;
        this.journeyTIme = journeyTIme;
        this.journetStartTime = journetStartTime;
        this.journeyEndTIme = journeyEndTIme;
        this.status = status;
        this.carType = carType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Timestamp getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(Timestamp journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getJourneyTIme() {
        return journeyTIme;
    }

    public void setJourneyTIme(String journeyTIme) {
        this.journeyTIme = journeyTIme;
    }

    public String getJournetStartTime() {
        return journetStartTime;
    }

    public void setJournetStartTime(String journetStartTime) {
        this.journetStartTime = journetStartTime;
    }

    public String getJourneyEndTIme() {
        return journeyEndTIme;
    }

    public void setJourneyEndTIme(String journeyEndTIme) {
        this.journeyEndTIme = journeyEndTIme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
