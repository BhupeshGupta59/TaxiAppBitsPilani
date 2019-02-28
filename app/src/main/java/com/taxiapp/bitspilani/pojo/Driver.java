package com.taxiapp.bitspilani.pojo;

import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.util.*;

public class Driver extends PersonDetails
{

    private String LicenseNo;

    private String location;
    public Driver()
    {

    }

    public Driver(String name, String phoneNo, String emailId, String licenseNo) {
        super(name, phoneNo, emailId);
        Database dB = new Database();
        setId(dB.getFirestoreInstance().collection("drivers").document().getId());
        LicenseNo = licenseNo;


    }

    public String getLicenseNo() {
        return LicenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        LicenseNo = licenseNo;
    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
