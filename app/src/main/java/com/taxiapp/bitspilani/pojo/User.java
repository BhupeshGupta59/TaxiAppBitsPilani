package com.taxiapp.bitspilani.pojo;

import com.google.firebase.firestore.GeoPoint;
import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.util.*;

public class User extends PersonDetails
{
    private String department;
    private List<String> listOfBooking;
    private GeoPoint location;

    public User()
    {

    }

    public User(String name, String phoneNo, String emailId, String department, List<String> listOfBooking, GeoPoint location) {

        super(name, phoneNo, emailId);
        Database dB = new Database();
        setId(dB.getFirestoreInstance().collection("users").document().getId());
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

    public List<String> getListOfBooking() {
        return listOfBooking;
    }

    public void setListOfBooking(List<String> listOfBooking) {
        this.listOfBooking = listOfBooking;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
