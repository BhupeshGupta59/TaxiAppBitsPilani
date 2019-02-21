package com.taxiapp.bitspilani.pojo;

import java.util.*;

public class User extends PersonDetails
{
    private String department;
    private List<Booking> listOfBooking;
    private String location;


    public User(String id, String name, String phoneNo, String emailId, String department, List<Booking> listOfBooking,
                String location) {
        super(id, name, phoneNo, emailId);
        this.department = department;
        this.listOfBooking = listOfBooking;
        this.location = location;
    }


    public String getDepartment() {
        return department;
    }


    public void setDepartment(String department) {
        this.department = department;
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
