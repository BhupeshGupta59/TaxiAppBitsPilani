package com.taxiapp.bitspilani.pojo;

import com.google.firebase.firestore.GeoPoint;
import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.util.*;

public class Station
{
    private String id;
    private String name;
    private Map<String, GeoPoint> nearestSubstations ;

    public Station() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Station(String name, Map<String, GeoPoint> nearestSubstations) {
        Database dB = new Database();
        setId(dB.getFirestoreInstance().collection("stations").document().getId());
        this.name = name;
        this.nearestSubstations = nearestSubstations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, GeoPoint> getNearestSubstations() {
        return nearestSubstations;
    }

    public void setNearestSubstations(Map<String, GeoPoint> nearestSubstations) {
        this.nearestSubstations = nearestSubstations;
    }
}
