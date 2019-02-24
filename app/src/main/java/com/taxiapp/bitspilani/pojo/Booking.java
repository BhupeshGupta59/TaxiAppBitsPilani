package com.taxiapp.bitspilani.pojo;


import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;
import com.taxiapp.bitspilani.CommonDBOperation.Database;

public class Booking {
    private String id;
    private String source;
    private String destination;
    private @ServerTimestamp Timestamp timestamp;
    private String journetStartTime;
    private String journeyEndTIme;
    private String status;
    private String carType;


    public Booking() {
    }

    public Booking(String source, String destination, Timestamp timestamp, String status, String carType) {
        Database dB = new Database();
        id = dB.getFirestoreInstance().collection("bookings").document().getId();
        this.source = source;
        this.destination = destination;
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }


}
