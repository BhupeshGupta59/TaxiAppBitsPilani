package com.taxiapp.bitspilani.pojo;

import android.util.Log;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.*;

public class Admin
{
    private List<User> listOfUsers;
    private List<Owner> listOfOwners;



    private List<Driver> listOfDrivers;
    private List<Vehicle> listOfVehicles;
    private List<Owner> listOfOwner;

    public Admin() {
    }
    public List<User> getListOfUsers() {
        return listOfUsers;
    }
    public void setListOfUsers(List<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }
    public List<Owner> getListOfOwners() {
        return listOfOwners;
    }
    public void setListOfOwners(List<Owner> listOfOwners) {
        this.listOfOwners = listOfOwners;
    }
    public List<Driver> getListOfDrivers() {
        return listOfDrivers;
    }
    public void setListOfDrivers(List<Driver> listOfDrivers) {
        this.listOfDrivers = listOfDrivers;
    }
    public List<Vehicle> getListOfVehicles() {
        return listOfVehicles;
    }
    public void setListOfVehicles(List<Vehicle> listOfVehicles) {
        this.listOfVehicles = listOfVehicles;
    }
    public List<Owner> getListOfOwner() {
        return listOfOwner;
    }
    public void setListOfOwner(List<Owner> listOfOwner) {
        this.listOfOwner = listOfOwner;
    }

    // Get details of a particular booking
    public void bookingDetails() {

    }

    // Edit/Update driver details
    public void editDriver() {

    }

    // Edit/Update user details
    public void editUser() {

    }

    // Edit/Update Driver details ( optional to be approve by Mam)
    public void editVehicle() {

    }

    // Raise issue if any discrepancies w.r.t to driver
    public void raiseIssue() {

    }

    public void getFeedbackDetails() {

    }

    public void scheduleCabRequest() {

    }

    // Create dynamic graph of connected stations  and show cabs status at each location
    public void showTaxiNetwork()
    {

    }
    public void bookCab(List<Booking> bookingList,List<Owner> ownerList,List<Station> stationList)

    {

        String source;
        String destination;
        Timestamp journeyDate;
        //SimpleDateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String carType;
        String status;
        Integer indexOfSource;
        Integer indexOfDestination;
        List<Vehicle> vehicleList;
        Map<String,Integer> subStations;
        for(Booking booking: bookingList)
        {
            source = booking.getSource();
            destination = booking.getDestination();
            journeyDate =booking.getJourneyDate();
            //dateTime.format(journeyDate);
            carType = booking.getCarType();
            status = booking.getStatus();
            Log.i("Source: + dummy",source);
            Log.i("Destination + dummy ",destination);

            Log.i("dummy",ownerList.get(0).getCity());
            for(Owner owner: ownerList)
            {
                Log.i("OwnerCity + dummy:",owner.getCity());
                if(owner.getCity().equalsIgnoreCase(destination))
                {
                    vehicleList = owner.getListOfVehicle();
                    for(Vehicle vehicle: vehicleList)
                    {
                        Log.i("VehicleStatus + dummy",vehicle.getStatus());
                        if(vehicle.getStatus().equalsIgnoreCase("idle"))
                        {
                            Log.i("VLastStation+ dummy",vehicle.getLastStation());
                            Log.i("VehicleCarType + dummy",vehicle.getCarType());
                            if (vehicle.getLastStation().equalsIgnoreCase(source) && vehicle.getCarType().equalsIgnoreCase(carType))
                            {


                                Log.i("Booked", owner.getName() + " " + vehicle.getName() + " " + vehicle.getVehicleNo());
                                return;


                            }
                            else
                            {
                                for(Station station: stationList)
                                {
                                    if(station.getName().equalsIgnoreCase(source))
                                    {
                                        subStations =station.getNearestSubstations();
                                        for(String subStationName : subStations.keySet())
                                        {
                                            if (vehicle.getLastStation().equalsIgnoreCase(subStationName) && vehicle.getCarType().equalsIgnoreCase(carType))
                                            {


                                                Log.i("Booked", owner.getName() + " " + vehicle.getName() + " " + vehicle.getVehicleNo());
                                                return;


                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                    for(Vehicle vehicle: vehicleList)
                    {
                        if(vehicle.getStatus().equalsIgnoreCase("moving"))
                        {
                            if (vehicle.getCurrentBooking().getDestination().equalsIgnoreCase(source) && vehicle.getCarType().equalsIgnoreCase(carType))
                            {


                                Log.i("Booked", owner.getName() + " " + vehicle.getName() + " " + vehicle.getVehicleNo());
                                return;


                            }
                            else
                            {
                                for(Station station: stationList)
                                {
                                    if(station.getName().equalsIgnoreCase(source))
                                    {
                                        subStations =station.getNearestSubstations();
                                        for(String subStationName : subStations.keySet())
                                        {
                                            if (vehicle.getCurrentBooking().getDestination().equalsIgnoreCase(subStationName) && vehicle.getCarType().equalsIgnoreCase(carType))
                                            {


                                                Log.i("Booked", owner.getName() + " " + vehicle.getName() + " " + vehicle.getVehicleNo());
                                                return;


                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

            }
          /*  for(Owner owner: ownerList)
            {
                if(owner.getCity().equalsIgnoreCase(destination))
                {
                    vehicleList = owner.getListOfVehicle();
                    for(Vehicle vehicle: vehicleList)
                    {
                        if(vehicle.getStatus().equalsIgnoreCase("idle"))
                        {
                            if (vehicle.getLastStation().equalsIgnoreCase(destination) && vehicle.getCarType().equalsIgnoreCase(carType))
                            {


                                Log.i("Booked", owner.getName() + " " + vehicle.getName() + " " + vehicle.getVehicleNo());
                                return;


                            }
                            else
                            {
                                for(Station station: stationList)
                                {
                                    if(station.getName().equalsIgnoreCase(destination))
                                    {
                                        subStations =station.getNearestSubstations();
                                        for(String subStationName : subStations.keySet())
                                        {
                                            if (vehicle.getLastStation().equalsIgnoreCase(subStationName) && vehicle.getCarType().equalsIgnoreCase(carType))
                                            {


                                                Log.i("Booked", owner.getName() + " " + vehicle.getName() + " " + vehicle.getVehicleNo());
                                                return;


                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                    for(Vehicle vehicle: vehicleList)
                    {
                        if(vehicle.getStatus().equalsIgnoreCase("moving"))
                        {
                            if (vehicle.getCurrentBooking().getDestination().equalsIgnoreCase(source) && vehicle.getCarType().equalsIgnoreCase(carType))
                            {


                                Log.i("Booked", owner.getName() + " " + vehicle.getName() + " " + vehicle.getVehicleNo());
                                return;


                            }
                            else
                            {
                                for(Station station: stationList)
                                {
                                    if(station.getName().equalsIgnoreCase(destination))
                                    {
                                        subStations =station.getNearestSubstations();
                                        for(String subStationName : subStations.keySet())
                                        {
                                            if (vehicle.getCurrentBooking().getDestination().equalsIgnoreCase(subStationName) && vehicle.getCarType().equalsIgnoreCase(carType))
                                            {


                                                Log.i("Booked", owner.getName() + " " + vehicle.getName() + " " + vehicle.getVehicleNo());
                                                return;


                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

            }*/

        }
    }
}
