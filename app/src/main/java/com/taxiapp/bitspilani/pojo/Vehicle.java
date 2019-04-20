package com.taxiapp.bitspilani.pojo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.taxiapp.bitspilani.CommonDBOperation.Database;
import com.taxiapp.bitspilani.enums.*;

import java.io.IOException;
import java.util.*;

public class Vehicle
{
    private String id;
    private String name;
    private String carType;
    private String vehicleNo;
    private GeoPoint location;
    private String status;
    private String ownerCity;
    private String lastLocationName;
    private int noOfSeats;

    public Vehicle()
    {

    }

    public String getOwnerCity() {
        return ownerCity;
    }

    public void setOwnerCity(String ownerCity) {
        this.ownerCity = ownerCity;
    }

    public void setLastLocationName(String lastLocationName) {
        this.lastLocationName = lastLocationName;
    }

    public Vehicle(String name, String carType, String vehicleNo, String ownerCity, int noOfSeats) {



        setId(FirebaseFirestore.getInstance().collection("vehicles").document().getId());
        this.name = name;
        this.carType = carType;
        this.vehicleNo = vehicleNo;

        status = "idle";
        this.ownerCity = ownerCity;
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
        return ownerCity;
    }

    public void setLastLocation(String lastLocationName) {
        this.lastLocationName = lastLocationName;
    }
    public float distanceTo(GeoPoint g)
    {
        Location l1 = new Location(LocationManager.GPS_PROVIDER);
        Location l2 = new Location(LocationManager.GPS_PROVIDER);
        l1.setLatitude(location.getLatitude());
        l1.setLongitude(location.getLongitude());
        l2.setLatitude(g.getLatitude());
        l2.setLongitude(g.getLongitude());
        return  l1.distanceTo(l2);
    }
    public String toCity(Context context )  {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses!=null)
            {
                if(addresses.get(0).getLocality().equalsIgnoreCase("New Delhi"))
                {
                    return "Delhi";
                }
                return addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public String getNearestStation(Context context,List<Station> stationList)

    {

        for(int i=0;i<stationList.size();i++)
        {
            Station currentStation = stationList.get(i);

            if(currentStation.distanceTo(location)<=40000)
            {
                return currentStation.getName();
            }
        }
        return null;

    }



}
