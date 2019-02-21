package com.taxiapp.bitspilani.UIComponent;

import com.taxiapp.bitspilani.pojo.*;
import com.taxiapp.bitspilani.enums.*;

public class RegisterPage
{
    private PersonDetails person;
    private UserType userType;
    private String password;

    public RegisterPage(PersonDetails person, UserType userType, String password) {
        super();
        this.person = person;
        this.userType = userType;
        this.password = password;
    }

    public PersonDetails getPerson() {
        return person;
    }

    public void setPerson(PersonDetails person) {
        this.person = person;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
