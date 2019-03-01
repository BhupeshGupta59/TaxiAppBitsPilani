package com.taxiapp.bitspilani.pojo;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.taxiapp.bitspilani.CommonDBOperation.Database;

import java.util.*;
import java.util.concurrent.ExecutionException;

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
    private class ScheduleTask extends AsyncTask<String,Integer,List<String>> {
        @Override
        protected List<String> doInBackground(String... strings) {
            Database dB = new Database();
            Task<QuerySnapshot> bookingTask = dB.getFirestoreInstance().collection("bookings").whereEqualTo("status","pending").orderBy("timestamp").get();
            Task<QuerySnapshot> ownerTask = dB.getFirestoreInstance().collection("owners").get();
            Task<QuerySnapshot> stationTask = dB.getFirestoreInstance().collection("stations").get();
            Task<List<Object>> allTasks = Tasks.whenAllSuccess(stationTask, ownerTask, bookingTask);
            try {
                Tasks.await(allTasks);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            allTasks.continueWithTask(new Continuation<List<Object>, Task<Void>>() {
                @Override
                public Task<Void> then(@NonNull Task<List<Object>> task) throws Exception {
                    List<Object> objects = task.getResult();
                    List<Booking> bookingList = new ArrayList<>();
                    List<Station> stationList = new ArrayList<>();
                    List<Owner> ownerList = new ArrayList<>();

                    QuerySnapshot q1 = (QuerySnapshot)objects.get(0);
                    QuerySnapshot q2 = (QuerySnapshot)objects.get(1);
                    QuerySnapshot q3 = (QuerySnapshot)objects.get(2);
                    for(QueryDocumentSnapshot d1: q1)
                    {

                        stationList.add(d1.toObject(Station.class));
                        // Log.i("abc",d1.toObject(Station.class).getName());
                    }
                    for(QueryDocumentSnapshot d2: q2)
                    {
                        ownerList.add(d2.toObject(Owner.class));
                    }
                    int i=0;
                    for(QueryDocumentSnapshot d3: q3)
                    {
                        Booking b = d3.toObject(Booking.class);
                        Log.i("booking",b.getId());
                        bookingList.add(d3.toObject(Booking.class));

                    }


                    // Log.i("abc",stationList.get(0).getId());
                    //Log.i("abc",ownerList.get(0).getId());
                    return bookCab(bookingList,stationList,ownerList);
                }
            });
            return null;
        }

        // Before the tasks execution
        protected void onPreExecute(){
            // Display the progress dialog on async task start
           Log.i("abc","Pre");
        }


        // After each task done
        protected void onProgressUpdate(Integer... progress){
            // Update the progress bar on dialog

        }

        // When all async task done
        protected void onPostExecute(List<String> result){
            // Hide the progress dialog

            Log.i("abc","Post");

        }
    }

    public void bookCab()  {
       new ScheduleTask().execute();

    }
    public Task<Void> bookCab(List<Booking> bookingList, List<Station> stationList,List<Owner >ownerList) {
        Database dB = new Database();
       // WriteBatch batch = dB.getFirestoreInstance().batch();
        Iterator bookingIterator = bookingList.iterator();
        Map<String,Vehicle> bookedVehicle = new HashMap<>();
        int i=1;
        Log.i("bookingsize",Integer.toString(bookingList.size()));
        while (bookingIterator.hasNext()) {

            Booking currentBooking = (Booking) bookingIterator.next();


           // Log.i("bookingId",currentBooking.getId());
         //   Log.i("currentBooking",currentBooking.getId());
          if(getCab(currentBooking.getDestination(), currentBooking.getSource(), currentBooking, ownerList, stationList))
            {
               // Log.i("currentBooking1",currentBooking.getId());
            }
            else if(getCab(currentBooking.getDestination(), currentBooking.getDestination(), currentBooking, ownerList, stationList))
            {
                //Log.i("currentBooking2",currentBooking.getId());

            }
           else if(getCab(currentBooking.getSource(), currentBooking.getSource(), currentBooking, ownerList, stationList))
            {

                //Log.i("currentBooking3",currentBooking.getId());
            }
           else if( getCab(currentBooking.getSource(), currentBooking.getDestination(), currentBooking, ownerList, stationList))
           {

               //Log.i("currentBooking4",currentBooking.getId());
           }

        }
        Log.i("abc","Between");
        return null;
    }
    public boolean getCab(String ownerCity,String vehicleCity,Booking currentBooking,List<Owner> ownerList,List<Station> stationList)
    {  // boolean flag = false;
        Database dB = new Database();
        String currentSource = currentBooking.getSource();
        String currentDestination = currentBooking.getDestination();
        String carType = currentBooking.getCarType();
        DocumentReference currentBookingRef = null;
        DocumentReference currentVehicleRef = null;
        DocumentReference currentOwnerRef = null;
        WriteBatch batch = dB.getFirestoreInstance().batch();
        Iterator ownerIterator = ownerList.iterator();
        while(ownerIterator.hasNext())
        {   Log.i("currentBooking ",currentBooking.getId());
            Owner currentOwner = (Owner) ownerIterator.next();
            if(currentOwner.getCity().equalsIgnoreCase(ownerCity))
            {
                Iterator vehicleIterator = currentOwner.getListOfVehicle().iterator();
                while(vehicleIterator.hasNext())
                {
                    Vehicle currentVehicle = (Vehicle) vehicleIterator.next();
                    if(currentVehicle.getCarType().equalsIgnoreCase(carType) &&  currentVehicle.getStatus().equalsIgnoreCase("idle"))
                    {

                        Iterator stationIterator = stationList.iterator();
                        while (stationIterator.hasNext())
                        {
                            Station currentStation = (Station) stationIterator.next();
                            if(currentStation.getName().equalsIgnoreCase(vehicleCity))
                            {
                                if(vehicleCity.equalsIgnoreCase(currentVehicle.getLastLocationName()))
                                {   Log.i("booked",currentBooking.getId()+" "+currentVehicle.getName());
                                    currentVehicle.setStatus("busy");
                                    currentVehicleRef = dB.getFirestoreInstance().collection("vehicle").document(currentVehicle.getId());
                                    currentOwnerRef = dB.getFirestoreInstance().collection("owners").document(currentOwner.getId());
                                    currentBookingRef = dB.getFirestoreInstance().collection("bookings").document(currentBooking.getId());
                                    batch.update(currentBookingRef,"status","approved");
                                    batch.update(currentBookingRef,"vehicleRef",currentVehicleRef);
                                    batch.update(currentBookingRef,"ownerRef",currentOwnerRef);
                                    batch.update(currentBookingRef,"vehicleId",currentVehicle.getId());
                                    batch.update(currentBookingRef,"ownerId",currentOwner.getId());
                                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // ...
                                        }
                                    });
                                    return true;
                                }
                                else
                                {
                                    Map<String,GeoPoint> substations = currentStation.getNearestSubstations();
                                    Iterator substationIterator = substations.entrySet().iterator();
                                    while (substationIterator.hasNext())
                                    {
                                        Map.Entry currentSubstation = (Map.Entry) substationIterator.next();
                                        String currentSubstationName = (String) currentSubstation.getKey();
                                        if(currentSubstationName.equalsIgnoreCase(currentVehicle.getLastLocationName()))
                                        {   Log.i("booked",currentBooking.getId()+" "+currentVehicle.getName());
                                            currentVehicle.setStatus("busy");
                                            currentVehicleRef = dB.getFirestoreInstance().collection("vehicle").document(currentVehicle.getId());
                                            currentOwnerRef = dB.getFirestoreInstance().collection("owners").document(currentOwner.getId());
                                            currentBookingRef = dB.getFirestoreInstance().collection("bookings").document(currentBooking.getId());
                                           batch.update(currentBookingRef,"status","approved");
                                            batch.update(currentBookingRef,"vehicleRef",currentVehicleRef);
                                            batch.update(currentBookingRef,"ownerRef",currentOwnerRef);
                                            batch.update(currentBookingRef,"vehicleId",currentVehicle.getId());
                                            batch.update(currentBookingRef,"ownerId",currentOwner.getId());
                                            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    // ...
                                                }
                                            });
                                            return true;
                                        }

                                    }
                                }
                            }

                        }
                    }

                }
            }
        }
       // Log.i("abc","1");
        ///Log.i("abc Booking",Integer.toString(i));
        //i++;
       return false;
    }


}
