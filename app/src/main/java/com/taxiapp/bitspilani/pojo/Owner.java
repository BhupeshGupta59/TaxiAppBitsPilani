package com.taxiapp.bitspilani.pojo;

import com.google.firebase.firestore.FirebaseFirestore;
import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.util.*;

public class Owner extends PersonDetails
{
    private List<Vehicle> listOfVehicle;
    private List<Driver> listOfDriver;



    public Owner()
    {

    }

    public Owner(String name, String phoneNo, String emailId, String city) {
        super(name, phoneNo, emailId,city);

        setId(FirebaseFirestore.getInstance().collection("owners").document().getId());


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

   public void addVehicle(Vehicle vehicle)
   {
       listOfVehicle.add(vehicle);
   }
    public void addDriver(Driver driver)
    {
       listOfDriver.add(driver);
    }
}
