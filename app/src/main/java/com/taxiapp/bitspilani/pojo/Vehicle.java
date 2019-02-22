package com.taxiapp.bitspilani.pojo;

import com.taxiapp.bitspilani.CommonDBOperation.Database;
import com.taxiapp.bitspilani.enums.*;
import java.util.*;

public class Vehicle
{
    private String id;
    private String name;
    private String carType;
    private String vehicleNo;
    private String location;
    private String status;
    private String city;
    private int noOfSeats;
    private List<String> listOfBooking;

    public Vehicle()
    {

    }

    public Vehicle(String name, String carType, String vehicleNo, String location, String status, String city, int noOfSeats, List<String> listOfBooking) {
        Database dB = new Database();
        id = dB.getFirestoreInstance().collection("vehicles").document().getId();
        this.name = name;
        this.carType = carType;
        this.vehicleNo = vehicleNo;
        this.location = location;
        this.status = status;
        this.city = city;
        this.noOfSeats = noOfSeats;
        this.listOfBooking = listOfBooking;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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

    public List<String> getBookings() {
        return listOfBooking;
    }

    public void setBookings(List<String> listOfBooking) {
        this.listOfBooking = listOfBooking;
    }
}
