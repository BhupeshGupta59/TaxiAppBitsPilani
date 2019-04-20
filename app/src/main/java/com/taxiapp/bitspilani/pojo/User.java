package com.taxiapp.bitspilani.pojo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.io.IOException;
import java.util.*;

public class User extends PersonDetails
{
    private String department;

    private GeoPoint location;

    public User()
    {

    }

    public User(String name, String phoneNo, String emailId, String department,String city) {

        super(name, phoneNo, emailId,city);

        setId(FirebaseFirestore.getInstance().collection("users").document().getId());
        this.department = department;

    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }



    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

}
