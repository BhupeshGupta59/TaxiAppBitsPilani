package com.taxiapp.bitspilani.pojo;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;
import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Booking implements Comparable<Booking> {
    private String id;
    private String source;
    private String destination;
    private @ServerTimestamp Timestamp timestamp;
    private String journeyStartTime;
    private String journeyEndTIme;
    private String status;
    private String carType;
    private DocumentReference userRef;
    private DocumentReference ownerRef;
    private DocumentReference vehicleRef;
    private DocumentReference driverRef;
    private String userId;
    private  String ownerId;
    private String vehicleId;
    private String driverId;


    public Booking() {
    }

    public Booking(String source, String destination, Timestamp timestamp, String carType,String userId) {
        Database dB = new Database();
        setId(dB.getFirestoreInstance().collection("bookings").document().getId());
        status = "pending";
        this.source = source;
        this.destination = destination;
        this.timestamp = timestamp;
        this.carType = carType;
        this.userId = userId;



    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getJourneyStartTime() {
        return journeyStartTime;
    }

    public void setJourneyStartTime(String journeyStartTime) {
        this.journeyStartTime = journeyStartTime;
    }

    public String getJourneyEndTIme() {
        return journeyEndTIme;
    }

    public void setJourneyEndTIme(String journeyEndTIme) {
        this.journeyEndTIme = journeyEndTIme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public DocumentReference getUserRef() {
        return userRef;
    }

    public void setUserRef(DocumentReference userRef) {
        this.userRef = userRef;
    }

    public DocumentReference getOwnerRef() {
        return ownerRef;
    }

    public void setOwnerRef(DocumentReference ownerRef) {
        this.ownerRef = ownerRef;
    }

    public DocumentReference getVehicleRef() {
        return vehicleRef;
    }

    public void setVehicleRef(DocumentReference vehicleRef) {
        this.vehicleRef = vehicleRef;
    }

    public DocumentReference getDriverRef() {
        return driverRef;
    }

    public void setDriverRef(DocumentReference driverRef) {
        this.driverRef = driverRef;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
    public void setRef(String userId)
    {
        Database dB = new Database();
        dB.getFirestoreInstance().collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Database dB = new Database();
                DocumentReference d= documentSnapshot.getReference();
                dB.getFirestoreInstance().collection("bookings").document(id).update("userRef",d).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });
    }



    @Override
    public int compareTo(Booking o) {
        return timestamp.compareTo(o.getTimestamp());
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

    public String getNearestStationFromSource(Context context,List<Station> stationList)

    {
        GeoPoint addressGeoPoint = toGeoPoint(context,source);
        for(int i=0;i<stationList.size();i++)
        {
            Station currentStation = stationList.get(i);

            if(currentStation.distanceTo(addressGeoPoint)<=40000)
            {
                return currentStation.getName();
            }
        }
        return null;

    }
    public String getNearestStationFromDestination(Context context,List<Station> stationList)

    {
        GeoPoint addressGeoPoint = toGeoPoint(context,destination);
        for(int i=0;i<stationList.size();i++)
        {
            Station currentStation = stationList.get(i);

            if(currentStation.distanceTo(addressGeoPoint)<=40000)
            {
                return currentStation.getName();
            }
        }
        return null;

    }


}
