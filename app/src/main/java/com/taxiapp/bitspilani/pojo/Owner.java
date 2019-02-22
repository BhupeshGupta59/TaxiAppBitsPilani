package com.taxiapp.bitspilani.pojo;

import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.util.*;

public class Owner extends PersonDetails
{
    private List<String> listOfVehicle;
    private List<String> listOfDriver;
    private String city;

    public Owner()
    {

    }

    public Owner(String name, String phoneNo, String emailId, List<String> listOfVehicle, List<String> listOfDriver, String city) {
        super(name, phoneNo, emailId);
        Database dB = new Database();
        setId(dB.getFirestoreInstance().collection("owners").document().getId());
        this.listOfVehicle = listOfVehicle;
        this.listOfDriver = listOfDriver;
        this.city = city;
    }

    public List<String> getListOfVehicle() {
        return listOfVehicle;
    }

    public void setListOfVehicle(List<String> listOfVehicle) {
        this.listOfVehicle = listOfVehicle;
    }

    public List<String> getListOfDriver() {
        return listOfDriver;
    }

    public void setListOfDriver(List<String> listOfDriver) {
        this.listOfDriver = listOfDriver;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
