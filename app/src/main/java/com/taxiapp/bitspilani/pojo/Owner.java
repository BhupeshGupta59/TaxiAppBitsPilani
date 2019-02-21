package com.taxiapp.bitspilani.pojo;

import java.util.*;

public class Owner extends PersonDetails
{
    private List<Vehicle> listOfVehicle;
    private List<Driver> listOfDriver;
    private String city;

    public Owner()
    {

    }
    public Owner(String id, String name, String phoneNo, String emailId, List<Vehicle> listOfVehicle,
                 List<Driver> listOfDriver,String city) {
        super(id, name, phoneNo, emailId);
        this.listOfVehicle = listOfVehicle;
        this.listOfDriver = listOfDriver;
        this.city =city;
    }

    public List<Vehicle> getListOfVehicle() {
        return listOfVehicle;
    }

    public void setListOfVehicle(List<Vehicle> listOfVehicle) {
        this.listOfVehicle = listOfVehicle;
    }

    public List<Driver> getListOfDriver() {
        return listOfDriver;
    }

    public void setListOfDriver(List<Driver> listOfDriver) {
        this.listOfDriver = listOfDriver;
    }

    public void setCity(String city)
    {
        this.city =city;
    }
    public String getCity()
    {
        return city;
    }
}
