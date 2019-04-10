package com.taxiapp.bitspilani.pojo;

import com.google.firebase.firestore.GeoPoint;
import com.taxiapp.bitspilani.CommonDBOperation.Database;
import com.taxiapp.bitspilani.enums.*;
import java.util.*;

public class Vehicle
{
    private String id;
    private String name;
    private String carType;
    private String vehicleNo;
    private GeoPoint location;
    private String status;
    private String city;
    private String lastLocationName;
    private int noOfSeats;

    public Vehicle()
    {

    }

    public Vehicle(String name, String carType, String vehicleNo, String city, int noOfSeats) {
        Database dB = new Database();
        id = dB.getFirestoreInstance().collection("vehicles").document().getId();
        this.name = name;
        this.carType = carType;
        this.vehicleNo = vehicleNo;

        status = "idle";
        this.city = city;
        this.noOfSeats = noOfSeats;

    }
    
    public Vehicle(String name, String carType, String vehicleNo, GeoPoint location, String city, String lastLocationName, int noOfSeats) {
        Database dB = new Database();
        id = dB.getFirestoreInstance().collection("vehicles").document().getId();
        this.name = name;
        this.carType = carType;
        this.vehicleNo = vehicleNo;

        status = "idle";
        this.city = city;
        this.noOfSeats = noOfSeats;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getLastLocationName() {
        if(lastLocationName!=null)
        {
            return lastLocationName;
        }
        return city;
    }

    public void setLastLocation(String lastLocationName) {
        this.lastLocationName = lastLocationName;
    }



}
