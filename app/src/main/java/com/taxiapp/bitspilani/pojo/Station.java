package com.taxiapp.bitspilani.pojo;

import java.util.*;

public class Station
{
    private String name;
    private Map<String, Integer> nearestSubstations ;

    public Station() {
    }

    public Station(String name, Map<String, Integer> nearestSubstations) {
        this.name = name;
        this.nearestSubstations = nearestSubstations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getNearestSubstations() {
        return nearestSubstations;
    }

    public void setNearestSubstations(Map<String, Integer> nearestSubstations) {
        this.nearestSubstations = nearestSubstations;
    }
}
