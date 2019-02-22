package com.taxiapp.bitspilani.pojo;

import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.util.*;

public class Driver extends PersonDetails
{

    private String LicenseNo;
    private List<String> listOfBooking;
    private String location;
    public Driver()
    {

    }

    public Driver(String name, String phoneNo, String emailId, String licenseNo, List<String> listOfBooking, String location) {
        super(name, phoneNo, emailId);
        Database dB = new Database();
        setId(dB.getFirestoreInstance().collection("drivers").document().getId());
        LicenseNo = licenseNo;
        this.listOfBooking = listOfBooking;
        this.location = location;
    }

    public String getLicenseNo() {
        return LicenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        LicenseNo = licenseNo;
    }

    public List<String> getListOfBooking() {
        return listOfBooking;
    }

    public void setListOfBooking(List<String> listOfBooking) {
        this.listOfBooking = listOfBooking;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
