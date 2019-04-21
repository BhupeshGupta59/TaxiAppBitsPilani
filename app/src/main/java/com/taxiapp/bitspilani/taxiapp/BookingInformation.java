package com.taxiapp.bitspilani.taxiapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taxiapp.bitspilani.pojo.Owner;
import com.taxiapp.bitspilani.pojo.User;

public class BookingInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_information);

        Bundle extras = getIntent().getExtras();

        String source = extras.getString("Source");
        String dest = extras.getString("Destination");
        String carType = extras.getString("carType");
        String status = extras.getString("status");

        String dateFormatted = extras.getString("dateFormatted");
        String timeFormat = extras.getString("timeFormat");
        final String journeyStartTime = extras.getString("journeyStartTime");
        final String journeyEndTIme = extras.getString("journeyEndTIme");

        String ownerid = extras.getString("ownerid");
        String driverid = extras.getString("driverid");
        String vehicleid = extras.getString("vehicleid");
        String userid = extras.getString("userid");

        class Details {
            String ownername, ownercity, ownerphone;
            String drivername, driverphone;
            String vehiclename, vehicletype, vehicleNo;
            String username, userdept, userphone;
        }

        final Details details = new Details();


        final TextView textViewToChange = (TextView) findViewById(R.id.textView2);
        textViewToChange.setText(
                "Source: " + source + "\n" +
                        "Destination: " + dest + "\n" +
                        "Car Type Required: " + carType + "\n" +
                        "Booking date: " + dateFormatted + "\n" +
                        "Booking Time: " + timeFormat + "\n" +
                        "Booking Status: " + status + "\n"
        );

        if (status.equalsIgnoreCase("approved")) {
            FirebaseFirestore.getInstance().collection("owners").document(ownerid.trim()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                             Owner o =document.toObject(Owner.class);

                             details.ownername = (String) o.getName();
                             details.ownercity = (String) o.getCity();
                            details.ownerphone = (String) o.getPhoneNo();
                            //Log.i("abc",details.ownername+" "+o.getName());
                    textViewToChange.append(
                            "Journey Start Time: " + journeyStartTime + "\n" +
                                    "Journey End TIme: " + journeyEndTIme + "\n"+
                                    "Owner Name: " + details.ownername + "\n" +
                            "Owner City: " + details.ownercity + "\n" +
                            "Owner Phone Number: " + details.ownerphone + "\n");
                        }}
                     else {
                       // Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

            FirebaseFirestore.getInstance().collection("drivers").document(driverid.trim()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    details.drivername = (String) documentSnapshot.get("name");
                    details.driverphone = (String) documentSnapshot.get("phoneNo");

                    textViewToChange.append("Driver Name: " + details.drivername + "\n" +
                            "Driver Phone Number: " + details.driverphone + "\n");

                }
            });

            FirebaseFirestore.getInstance().collection("vehicles").document(vehicleid.trim()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    details.vehiclename = (String) documentSnapshot.get("name");
                    details.vehicletype = (String) documentSnapshot.get("carType");
                    details.vehicleNo = (String) documentSnapshot.get("vehicleNo");

                    textViewToChange.append("Vehicle Model: " + details.vehiclename + "\n" +
                            "Vehicle Type: " + details.vehicletype + "\n" +
                            "Vehicle Number: " + details.vehicleNo + "\n");

                }
            });

            FirebaseFirestore.getInstance().collection("users").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    details.username = (String) documentSnapshot.get("name");
                    details.userdept = (String) documentSnapshot.get("department");
                    details.userphone = (String) documentSnapshot.get("phoneNo");

                    textViewToChange.append("User Name: " + details.username + "\n" +
                            "User Department: " + details.userdept + "\n" +
                            "User Phone Number: " + details.userphone + "\n");

                }
            });

        }

    }
}

