package com.taxiapp.bitspilani.CommonDBOperation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.taxiapp.bitspilani.pojo.Booking;
import com.taxiapp.bitspilani.pojo.Owner;
import com.taxiapp.bitspilani.pojo.Station;
import com.taxiapp.bitspilani.pojo.User;
import com.taxiapp.bitspilani.pojo.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Database


{
    //private DatabaseReference databaseReference;
    //private FirebaseDatabase databaseInstance;
    private List<Owner> ownerList=new ArrayList<>();
    private FirebaseFirestore firestoreInstance;

    public void setOwnerList() {

        firestoreInstance = FirebaseFirestore.getInstance();

        firestoreInstance.collection("owners")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    // List<Vehicle> vehicleList = new ArrayList<>();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Owner owner = document.toObject(Owner.class);
                                ownerList.add(owner);
                               // Log.i("abcdef",ownerList.get(0).getCity());



                            }




                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }


                });



    }

    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public List<Vehicle> getVehicleList(Owner owner) {
        return null;
    }

    public List<Station> getStationList() {
        return null;
    }

    public List<User> getUserList() {
        return null;
    }

    public List<Booking> getBookingList() {
        return null;
    }

    public FirebaseFirestore getFirestoreInstance()
    {
        firestoreInstance = FirebaseFirestore.getInstance();
        return firestoreInstance;
    }
    public void addOwner(Owner owner)
    {

    }
}
