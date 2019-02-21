package com.taxiapp.bitspilani.pojo;

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
    private List<Booking> bookings;

    public Vehicle()
    {

    }

    public Vehicle(String id, String name, String carType, String vehicleNo, String location, String status,
                   int noOfSeats, List<Booking> bookings,String city) {
        super();
        this.id = id;
        this.name = name;
        this.carType = carType;
        this.vehicleNo = vehicleNo;
        this.location = location;
        this.status = status;
        this.noOfSeats = noOfSeats;
        this.bookings = bookings;
        this.city =city;
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

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getLastStation()
    {
        if(status.equalsIgnoreCase("idle"))
        {
            return bookings.get(0).getDestination();
        }
        return null;
    }

   public Booking getCurrentBooking()
    {
        if(status.equalsIgnoreCase("moving"))
        {
            return bookings.get(0);
        }
        return null;
    }

    public Booking getLastBooking()
    {
        return null;
    }

}
