package com.taxiapp.bitspilani.pojo;

public class PersonDetails
{
    private String id;
    private String name;
    private String phoneNo;
    private String emailId;

    public PersonDetails()
    {

    }

    public PersonDetails(String id, String name, String phoneNo, String emailId) {
        super();
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPhoneNo() {
        return phoneNo;
    }


    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public String getEmailId() {
        return emailId;
    }


    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
