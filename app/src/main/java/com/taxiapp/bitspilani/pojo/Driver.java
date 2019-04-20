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

public class Driver extends PersonDetails
{

    private String licenseNo;

    private GeoPoint location;

    public Driver()
    {

    }

    public Driver(String name, String phoneNo, String emailId, String licenseNo,String city) {
        super(name, phoneNo, emailId,city);


        setId(FirebaseFirestore.getInstance().collection("drivers").document().getId());
        this.licenseNo = licenseNo;


    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        licenseNo = licenseNo;
    }



    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
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
                return addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
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
