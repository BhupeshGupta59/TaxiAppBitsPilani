package com.taxiapp.bitspilani.pojo;

public class FeedBack
{
    private String id;
    private Booking bookingDetails;
    private Driver driverDetails;
    private User userDetails;
    private String feedback;

    public FeedBack(String id, Booking bookingDetails, Driver driverDetails, User userDetails, String feedback) {
        super();
        this.id = id;
        this.bookingDetails = bookingDetails;
        this.driverDetails = driverDetails;
        this.userDetails = userDetails;
        this.feedback = feedback;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Booking getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(Booking bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public Driver getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(Driver driverDetails) {
        this.driverDetails = driverDetails;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
