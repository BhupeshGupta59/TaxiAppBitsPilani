package com.taxiapp.bitspilani.taxiapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.taxiapp.bitspilani.pojo.Admin;
import com.taxiapp.bitspilani.pojo.Booking;
import com.taxiapp.bitspilani.pojo.Owner;
import com.taxiapp.bitspilani.pojo.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TaskInSync  {
    public List<Station> getStationList() {
        return stationList;
    }

    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    private List<Station> stationList = new ArrayList<>();
    private List<Owner> ownerList = new ArrayList<>();
   private List<Booking> bookingList = new ArrayList<>();

    public TaskInSync() {
    }
    public void getList()  {

        Task<QuerySnapshot> stations = FirebaseFirestore.getInstance().collection("stations").get();
        Task<QuerySnapshot> owners = FirebaseFirestore.getInstance().collection("owners").get();
        Task<QuerySnapshot> bookings = FirebaseFirestore.getInstance().collection("bookings").get();
      /*  Task<QuerySnapshot> t1= stations.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        stationList.add(documentSnapshot.toObject(Station.class));
                        Log.i("abc",documentSnapshot.toObject(Station.class).getName());
                    }


                }

            }
        });*/

        //Log.i("abc","Something");

        Task allTasks =Tasks.whenAllSuccess(stations, owners, bookings);



      allTasks.addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {
               QuerySnapshot q1 = (QuerySnapshot)objects.get(0);
                QuerySnapshot q2 = (QuerySnapshot)objects.get(1);
                QuerySnapshot q3 = (QuerySnapshot)objects.get(2);
                for(QueryDocumentSnapshot d1: q1)
                {

                    stationList.add(d1.toObject(Station.class));
                }
                for(QueryDocumentSnapshot d2: q2)
                {
                    ownerList.add(d2.toObject(Owner.class));
                }
                for(QueryDocumentSnapshot d3: q3)
                {
                    bookingList.add(d3.toObject(Booking.class));
                }
                Log.i("abc",stationList.get(0).getName());
                Log.i("abc",ownerList.get(0).getName());
                Log.i("abc",bookingList.get(0).getSource());
                Admin admin = new Admin();
               // admin.bookCab(bookingList,ownerList,stationList);

            }


        });



        // Log.i("abc",stationList.get(0).getName());
      //  Log.i("abc","SomethingforSomething");
    }








}