package com.taxiapp.bitspilani.UIComponent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.firestore.GeoPoint;
import com.taxiapp.bitspilani.CommonDBOperation.Database;
import com.taxiapp.bitspilani.pojo.Driver;
import com.taxiapp.bitspilani.pojo.Owner;
import com.taxiapp.bitspilani.pojo.Vehicle;
import com.taxiapp.bitspilani.taxiapp.R;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OwnerDriverVehicleTestCases extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_driver_vehicle_test_cases);
        Database dB = new Database();

        //vehicle test cases csv
        try
        {
            BufferedWriter testCases = new BufferedWriter(new FileWriter("Vehicle Test Cases.csv"));
            testCases.write("model,type,number,location_longitude,location_latitude,city,lastLocation_longitude,lastLocation_latitude,numOfSeats");
            testCases.newLine();
            testCases.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //driver test cases csv
        try
        {
            BufferedWriter testCases = new BufferedWriter(new FileWriter("Driver Test Cases.csv"));
            testCases.write("name,phone,email,license,longitude,latitude");
            testCases.newLine();
            testCases.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String city[] = {"Delhi","Pilani","Jaipur","Noida","Ghaziabad","Sikar","Chandigarh"};
        String[] location = {"28.644800,77.216721", "28.380200,75.609200", "26.912400,75.787300", "28.535500,77.391000", "28.669200,77.453800","27.609400,75.139900", "30.733300,76.779400"};
        String vehicleCode[] = {"DL05", "RJ18", "RJ14", "UP16", "UP14", "RJ23", "CH01"};

        int ownerNameCount = 1;
        int ownerPhoneCount = 1;

        int vehicleNumCount = 1;

        int driverCount = 1;
        int phoneNumCount = 1;
        int licenseCount = 1;

        for(int i=0; i<7; i++)
        {
            for(int j=1; j<=10; j++)
            {
                Owner owner = new Owner();
                owner.setName("owner" + Integer.toString(ownerNameCount++));

                if(ownerPhoneCount < 10)
                {
                    owner.setPhoneNo("999995000" + Integer.toString(ownerPhoneCount));
                }
                else
                {
                    owner.setPhoneNo("99999500" + Integer.toString(ownerPhoneCount));
                }

                owner.setEmailId(owner.getName() + "@gmail.com");
                owner.setCity(city[i]);

                //Vehicle test cases, 10 vehicles per owner

                //name,cartype,numofseats
                String carCategory1[] = {"Hyundai Eon","micro","4"};
                String carCategory2[] = {"Swift DZire", "sedan", "4"};
                String carCategory3[] = {"Toyota Innova", "SUV", "7"};

                //list for 10 vehicles of an owner
                List<Vehicle> veh = new ArrayList<>();

                for(int k=1; k<=3; k++)
                {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setName(carCategory1[0]);
                    vehicle.setCarType(carCategory1[1]);

                    if (vehicleNumCount < 10)
                    {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB000" + Integer.toString(vehicleNumCount++));
                    }
                    else
                    {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB00" + Integer.toString(vehicleNumCount++));
                    }

                    //random vehicle current location
                    Random generator = new Random();
                    int randomIndex = generator.nextInt(location.length);
                    String longLat[] = location[randomIndex].split(",");
                    GeoPoint loc = new GeoPoint(Double.parseDouble(longLat[0]), Double.parseDouble(longLat[1]));
                    vehicle.setLocation(loc);

                    vehicle.setOwnerCity(city[i]);
                    vehicle.setLastLocation(location[i]);
                    vehicle.setNoOfSeats(Integer.parseInt(carCategory1[2]));

                    //adding to owner list
                    veh.add(vehicle);

                    String writeToCSV = vehicle.getName() + "," + vehicle.getCarType() + "," + vehicle.getVehicleNo() + "," + vehicle.getLocation() +  "," + vehicle.getOwnerCity() + "," + vehicle.getLastLocationName() + "," + vehicle.getNoOfSeats();
                    //System.out.println(writeToCSV);
                    try
                    {
                        BufferedWriter append = new BufferedWriter(new FileWriter("Vehicle Test Cases.csv", true));
                        append.write(writeToCSV);
                        append.newLine();
                        append.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                for(int k=4; k<=6; k++)
                {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setName(carCategory2[0]);
                    vehicle.setCarType(carCategory2[1]);

                    if (vehicleNumCount < 10)
                    {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB000" + Integer.toString(vehicleNumCount++));
                    }
                    else
                    {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB00" + Integer.toString(vehicleNumCount++));
                    }

                    Random generator = new Random();
                    int randomIndex = generator.nextInt(location.length);
                    String longLat[] = location[randomIndex].split(",");
                    GeoPoint loc = new GeoPoint(Double.parseDouble(longLat[0]), Double.parseDouble(longLat[1]));
                    vehicle.setLocation(loc);

                    vehicle.setOwnerCity(city[i]);
                    vehicle.setLastLocation(location[i]);
                    vehicle.setNoOfSeats(Integer.parseInt(carCategory2[2]));

                    veh.add(vehicle);

                    String writeToCSV = vehicle.getName() + "," + vehicle.getCarType() + "," + vehicle.getVehicleNo() + "," + vehicle.getLocation() +  "," + vehicle.getOwnerCity() + "," + vehicle.getLastLocationName() + "," + vehicle.getNoOfSeats();
                    //System.out.println(writeToCSV);
                    try
                    {
                        BufferedWriter append = new BufferedWriter(new FileWriter("Vehicle Test Cases.csv", true));
                        append.write(writeToCSV);
                        append.newLine();
                        append.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                for(int k=7; k<=10; k++)
                {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setName(carCategory3[0]);
                    vehicle.setCarType(carCategory3[1]);

                    if (vehicleNumCount < 10)
                    {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB000" + Integer.toString(vehicleNumCount++));
                    }
                    else
                    {
                        vehicle.setVehicleNo(vehicleCode[i] + "PB00" + Integer.toString(vehicleNumCount++));
                    }

                    Random generator = new Random();
                    int randomIndex = generator.nextInt(location.length);
                    String longLat[] = location[randomIndex].split(",");
                    GeoPoint loc = new GeoPoint(Double.parseDouble(longLat[0]), Double.parseDouble(longLat[1]));
                    vehicle.setLocation(loc);

                    vehicle.setOwnerCity(city[i]);
                    vehicle.setLastLocation(location[i]);
                    vehicle.setNoOfSeats(Integer.parseInt(carCategory3[2]));

                    veh.add(vehicle);

                    String writeToCSV = vehicle.getName() + "," + vehicle.getCarType() + "," + vehicle.getVehicleNo() + "," + vehicle.getLocation() +  "," + vehicle.getOwnerCity() + "," + vehicle.getLastLocationName() + "," + vehicle.getNoOfSeats();
                    //System.out.println(writeToCSV);
                    try
                    {
                        BufferedWriter append = new BufferedWriter(new FileWriter("Vehicle Test Cases.csv", true));
                        append.write(writeToCSV);
                        append.newLine();
                        append.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                owner.setListOfVehicle(veh);

                //creating 10 drivers for each owner
                List<Driver> dri = new ArrayList<>();

                for (int k = 1; k <= 10; k++)
                {
                    Driver driver = new Driver();

                    driver.setName("driver" + Integer.toString(driverCount++));

                    if (phoneNumCount < 10) {
                        driver.setPhoneNo("900005000" + Integer.toString(phoneNumCount++));
                    } else if (phoneNumCount > 9 && phoneNumCount < 100) {
                        driver.setPhoneNo("90000500" + Integer.toString(phoneNumCount++));
                    } else {
                        driver.setPhoneNo("9000050" + Integer.toString(phoneNumCount++));
                    }

                    driver.setEmailId(driver.getName() + "@gmail.com");

                    if (licenseCount < 10) {
                        driver.setLicenseNo(vehicleCode[i] + "2014000000" + Integer.toString(licenseCount++));
                    } else if (licenseCount > 9 && licenseCount < 100) {
                        driver.setLicenseNo(vehicleCode[i] + "201400000" + Integer.toString(licenseCount++));
                    } else {
                        driver.setLicenseNo(vehicleCode[i] + "20140000" + Integer.toString(licenseCount++));
                    }

                    String longLat[] = location[i].split(",");
                    GeoPoint loc = new GeoPoint(Double.parseDouble(longLat[0]), Double.parseDouble(longLat[1]));
                    driver.setLocation(loc);

                    dri.add(driver);

                    String writeToCSV = driver.getName() + "," + driver.getPhoneNo() + "," + driver.getEmailId() + "," + driver.getLicenseNo() + "," + driver.getLocation();
                    //System.out.println(writeToCSV);

                    try {
                        BufferedWriter append = new BufferedWriter(new FileWriter("Driver Test Cases.csv", true));
                        append.write(writeToCSV);
                        append.newLine();
                        append.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                owner.setListOfDriver(dri);

                 dB.getFirestoreInstance().collection("OwnerDriverAkhilesh").document(owner.getId()).set(owner);

            }
        }
    }
}
