package com.taxiapp.bitspilani.pojo;

import java.util.*;

public class Driver extends PersonDetails
{
    private String address;
    private List<Booking> listOfBooking;
    private String location;
    public Driver()
    {

    }

    public Driver(String id, String name, String phoneNo, String emailId, String address, List<Booking> listOfBooking,
                  String location) {
        super(id, name, phoneNo, emailId);
        this.address = address;
        this.listOfBooking = listOfBooking;
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Booking> getListOfBooking() {
        return listOfBooking;
    }

    public void setListOfBooking(List<Booking> listOfBooking) {
        this.listOfBooking = listOfBooking;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
