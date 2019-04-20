package com.taxiapp.bitspilani.pojo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.firebase.firestore.GeoPoint;
import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.io.IOException;
import java.util.*;

public class Station
{
    private String id;
    private String name;
    private GeoPoint location;
       //private Map<String, GeoPoint> nearestSubstations ;

    public Station() {
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Station(Context context,String name) {
        Database dB = new Database();
        setId(dB.getFirestoreInstance().collection("stations").document().getId());

        this.name = name;
        //this.nearestSubstations = nearestSubstations;
        this.location = toGeoPoint(context,name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Map<String, GeoPoint> getNearestSubstations() {
        return nearestSubstations;
    }*/

   /* public void setNearestSubstations(Map<String, GeoPoint> nearestSubstations) {
        this.nearestSubstations = nearestSubstations;
    }*/
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
    public GeoPoint toGeoPoint(Context context,String address)
    {
        Geocoder coder = new Geocoder(context,Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = coder.getFromLocationName(address, 1);
            Address location = addresses.get(0);
            GeoPoint g = new GeoPoint(location.getLatitude(),location.getLongitude());
            return g;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
