package com.taxiapp.bitspilani.pojo;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
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
    List<ReportBooking> printList ;

    public Admin() {
            printList = new ArrayList<>();
    }
    public List<ReportBooking> getPrintList(){
        return printList;
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

        private Context context;

        public ScheduleTask (Context context){
            this.context = context;
        }
        @Override
        protected List<String> doInBackground(String... strings) {
            Database dB = new Database();
            Date d = new Date();
            Timestamp currentTimestamp = new Timestamp(new Date());
            // Log.i("abc",currentTimestamp.toString());

            CollectionReference bookingRef = dB.getFirestoreInstance().collection("bookings");
          //  bookingRef.whereEqualTo("status","pending");
           // bookingRef.whereEqualTo("status","unapproved");
          //  bookingRef.whereGreaterThanOrEqualTo("timestamp",currentTimestamp);
           // bookingRef.orderBy("timestamp").get();
            Task<QuerySnapshot> bookingTask = bookingRef.whereEqualTo("status","pending").whereGreaterThanOrEqualTo("timestamp",currentTimestamp).get();  //whereGreaterThanOrEqualTo("timestamp",currentTimestamp).orderBy("timestamp").get();

            Task<QuerySnapshot> ownerTask = dB.getFirestoreInstance().collection("owners").get();
            Task<QuerySnapshot> stationTask = dB.getFirestoreInstance().collection("stations").get();
            Task<QuerySnapshot> bookingTask1 = bookingRef.whereEqualTo("status","unapproved").whereGreaterThanOrEqualTo("timestamp",currentTimestamp).get();
            Task<List<Object>> allTasks = Tasks.whenAllSuccess(stationTask, ownerTask, bookingTask,bookingTask1);
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
                    QuerySnapshot q4 = (QuerySnapshot)objects.get(3);
                    for(QueryDocumentSnapshot d1: q1)
                    {

                        stationList.add(d1.toObject(Station.class));
                        // Log.i("abc",stationList.get(0).getName());
                        // // Log.i("abc",d1.toObject(Station.class).getName());
                    }
                    for(QueryDocumentSnapshot d2: q2)
                    {
                        ownerList.add(d2.toObject(Owner.class));
                        // Log.i("abc",ownerList.get(0).getName());
                    }
                    int i=0;
                    for(QueryDocumentSnapshot d3: q3)
                    {
                        Booking b = d3.toObject(Booking.class);
                        // Log.i("booking",b.getId());
                        bookingList.add(d3.toObject(Booking.class));
                        // Log.i("abc",bookingList.get(0).getCarType());

                    }
                    for(QueryDocumentSnapshot d4: q4)
                    {
                        Booking b = d4.toObject(Booking.class);
                        // Log.i("booking",b.getId());
                        bookingList.add(d4.toObject(Booking.class));
                        // Log.i("abc",b.getCarType());

                    }

                    Collections.sort(bookingList);
                    // // Log.i("abc",stationList.get(0).getId());
                    //// Log.i("abc",ownerList.get(0).getId());
                    return bookCab(context,bookingList,stationList,ownerList);
                }
            });
            return null;
        }

        // Before the tasks execution
        protected void onPreExecute(){
            // Display the progress dialog on async task start
          // // Log.i("abc","Pre");
        }


        // After each task done
        protected void onProgressUpdate(Integer... progress){
            // Update the progress bar on dialog

        }

        // When all async task done
        protected void onPostExecute(List<String> result){
            // Hide the progress dialog

          //  // Log.i("abc","Post");

        }
    }

    public void bookCab(Context context)  {
        ScheduleTask myScheduleTask = new ScheduleTask(context);
       myScheduleTask.execute();
       // Log.i("abc","bookCab");

    }
    public Task<Void> bookCab(Context context, List<Booking> bookingList, List<Station> stationList, List<Owner >ownerList) {
        Database dB = new Database();
        // WriteBatch batch = dB.getFirestoreInstance().batch();

        Iterator bookingIterator = bookingList.iterator();
        Map<String,Vehicle> bookedVehicle = new HashMap<>();
        int flag;
        DocumentReference currentBookingRef = null;
        // Log.i("bookingsize",Integer.toString(bookingList.size()));

       /* for(int i=0;i<bookingList.size();i++)

        {// Log.i("bookingList",bookingList.get(i).getTimestamp().toDate().toString()+" "+bookingList.get(i).getStatus());}*/

        WriteBatch batch = dB.getFirestoreInstance().batch();
        while (bookingIterator.hasNext()) {
            flag=0;
            ReportBooking print = new ReportBooking();
            Booking currentBooking = (Booking) bookingIterator.next();
            currentBookingRef = dB.getFirestoreInstance().collection("bookings").document(currentBooking.getId());
            String sourceStation = currentBooking.getNearestStationFromSource(context,stationList);
            String destinationStation = currentBooking.getNearestStationFromDestination(context,stationList);
            String source = currentBooking.getSource();
            String destination = currentBooking.getDestination();
            int flagCarType=0;
            int flagDistance=0;
            int flagVehicleStatus =0;
           //


                //// Log.i("bookcab", "BookingSource: "+source + " " + "BookingDestination:"+" "+destination);
                // // Log.i("bookingId",currentBooking.getId());
                //   // Log.i("currentBooking",currentBooking.getId());
            if (destinationStation !=null&& getCab(context, destinationStation, source, currentBooking, ownerList, stationList,print)) {
                    flag = 1;
                    // // Log.i("currentBooking1",currentBooking.getId());
                }
            else if (sourceStation!=null && getCab(context, sourceStation, source, currentBooking, ownerList, stationList,print)) {
                flag = 1;
                //// Log.i("currentBooking3",currentBooking.getId());
            }
            else if (destinationStation !=null&&getCab(context, destinationStation, destination, currentBooking, ownerList, stationList,print)) {
                    flag = 1;
                    //// Log.i("currentBooking2",currentBooking.getId());

                } else if (sourceStation!=null &&getCab(context, sourceStation, destination, currentBooking, ownerList, stationList,print)) {
                    flag = 1;
                    //// Log.i("currentBooking4",currentBooking.getId());
                }

            if(flag==0)
            {
               // // Log.i("bookcab",currentBooking.getId());

                print.setSource(currentBooking.getSource());
                print.setDestination(currentBooking.getDestination());

                print.setId(currentBooking.getId());


                print.setTimestamp(currentBooking.getTimestamp());
                print.setCarType(currentBooking.getCarType());
                print.setStatus("unapproved");
                printList.add(print);
               // batch.update(currentBookingRef,"status","unapproved");
            }
            else {
                // Log.i("bookcab", "***************BOOKING COMPLETE***********************");
            }

        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
            }
        });
       // // Log.i("abc","Between");
        return null;
    }
    public boolean getCab(Context context,String ownerCity,String vehicleCity,Booking currentBooking,List<Owner> ownerList,List<Station> stationList,ReportBooking print) {  // boolean flag = false;
        Database dB = new Database();
        int count=1;
      //  String currentSource = currentBooking.getSource();
       // String currentDestination = currentBooking.getDestination();
        String carType = currentBooking.getCarType();


        /* Uncomment
        DocumentReference currentBookingRef = null;
        DocumentReference currentVehicleRef = null;
        DocumentReference currentOwnerRef = null;
        WriteBatch batch = dB.getFirestoreInstance().batch();
        Uncomment */


        Iterator ownerIterator = ownerList.iterator();
        while (ownerIterator.hasNext()) {
           // // Log.i("currentBooking ", currentBooking.getId());
            Owner currentOwner = (Owner) ownerIterator.next();
           // // Log.i("bookcab","Owner "+Integer.toString(count));
            count++;
          //  // Log.i("bookedCab","owner"+" "+currentOwner.getId());
          //  // Log.i("bookcab","currentOwnercity:"+" "+currentOwner.getCity()+" ReqOwnerCity: "+ownerCity);
            if (currentOwner.getCity().equalsIgnoreCase(ownerCity)) {
                Iterator vehicleIterator = currentOwner.getListOfVehicle().iterator();
                int vehicleIndex = 0;
                int vcount=1;
                while (vehicleIterator.hasNext()) {
                  //  // Log.i("bookcab", "Vehicle " + Integer.toString(vcount));
                    vcount++;
                    Vehicle currentVehicle = (Vehicle) vehicleIterator.next();

                   /* String vehicleNearestStation = currentVehicle.getNearestStation(context,stationList);
                    if(vehicleNearestStation==null){
                        continue;
                    }*/
                   // // Log.i("bookCab", "vehicleCartype:" + " " + currentVehicle.getCarType() + " reqCarType: " + carType);
                    //// Log.i("bookCab", "vehicleStatus:" + " " + currentVehicle.getStatus() + " reqStatus: " + "idle");


                    if (currentVehicle.getStatus().equalsIgnoreCase("idle")){
                        // vehicleCity.equalsIgnoreCase(vehicleNearestStation)
                        //// Log.i("bookcab", "reqVehicleCity" + " " + vehicleCity + " currentVehicleCity" + currentVehicle.toCity(context));
                    float distance = currentVehicle.distanceTo(currentBooking.toGeoPoint(context, vehicleCity));
                    if(!print.getReason().equalsIgnoreCase("CarType not available"))
                    {
                        print.setReason("No vehicle available in nearby stations");
                    }
                    if (distance <= 40000) {
                        print.setReason("CarType not available");
                        if (currentVehicle.getCarType().equalsIgnoreCase(carType)) {

                          //  // Log.i("bookcab", currentBooking.getId() + " " + currentVehicle.getName());
                            currentVehicle.setStatus("busy");
                          //  // Log.i("bookcab Booked", "Source: " + currentBooking.getSource() + " " + "Destination: " + currentBooking.getDestination() + " " + "OwnerCity: " + currentVehicle.getOwnerCity() + " " + "Vehicle Location: " + currentVehicle.toCity(context));

                            print.setSource(currentBooking.getSource());
                            print.setDestination(currentBooking.getDestination());
                            print.setOwnerCity(currentOwner.getCity());
                            print.setVehicleLocation(currentVehicle.toCity(context));
                            print.setId(currentBooking.getId());

                            print.setOwnerName(currentOwner.getName());
                            print.setVehicleName(currentVehicle.getName());
                            print.setTimestamp(currentBooking.getTimestamp());
                            print.setCarType(currentBooking.getCarType());
                            print.setStatus("approved");
                            print.setReason("");
                            printList.add(print);

                            return true;
                        }
                                    /* Uncomment
                                    currentVehicleRef = dB.getFirestoreInstance().collection("vehicles").document(currentVehicle.getId());
                                    currentOwnerRef = dB.getFirestoreInstance().collection("owners").document(currentOwner.getId());
                                    currentBookingRef = dB.getFirestoreInstance().collection("bookings").document(currentBooking.getId());
                                    batch.update(currentBookingRef, "status", "approved");
                                    batch.update(currentBookingRef, "vehicleRef", currentVehicleRef);
                                    batch.update(currentBookingRef, "vehicleRef", currentVehicleRef);
                                    batch.update(currentBookingRef, "ownerRef", currentOwnerRef);
                                    batch.update(currentBookingRef, "vehicleId", currentVehicle.getId());
                                    batch.update(currentBookingRef, "ownerId", currentOwner.getId());
                                    //batch.update(currentOwnerRef,"listOfVehicle."+Integer.toString(vehicleIndex)+".status","busy");
                                    batch.update(currentVehicleRef, "status", "busy");
                                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // ...
                                        }
                                    });
                                    Uncomment */

                    }
                }





                    vehicleIndex++;

                }
            }
        }
        return false;
    }

   /* public Task<Void> bookCab(List<Booking> bookingList, List<Station> stationList,List<Owner >ownerList) {
        Database dB = new Database();
       // WriteBatch batch = dB.getFirestoreInstance().batch();
        Iterator bookingIterator = bookingList.iterator();
        Map<String,Vehicle> bookedVehicle = new HashMap<>();
        int flag;
        DocumentReference currentBookingRef = null;
        // Log.i("bookingsize",Integer.toString(bookingList.size()));
        for(int i=0;i<bookingList.size();i++)

        {// Log.i("bookingList",bookingList.get(i).getTimestamp().toDate().toString()+" "+bookingList.get(i).getStatus());}

        WriteBatch batch = dB.getFirestoreInstance().batch();
        while (bookingIterator.hasNext()) {
            flag=0;
            Booking currentBooking = (Booking) bookingIterator.next();
            currentBookingRef = dB.getFirestoreInstance().collection("bookings").document(currentBooking.getId());

           // // Log.i("bookingId",currentBooking.getId());
         //   // Log.i("currentBooking",currentBooking.getId());
          if(getCab(currentBooking.getDestination(), currentBooking.getSource(), currentBooking, ownerList, stationList))
            {
                flag=1;
               // // Log.i("currentBooking1",currentBooking.getId());
            }
            else if(getCab(currentBooking.getDestination(), currentBooking.getDestination(), currentBooking, ownerList, stationList))
            {
                flag=1;
                //// Log.i("currentBooking2",currentBooking.getId());

            }
           else if(getCab(currentBooking.getSource(), currentBooking.getSource(), currentBooking, ownerList, stationList))
            {
                flag=1;
                //// Log.i("currentBooking3",currentBooking.getId());
            }
           else if( getCab(currentBooking.getSource(), currentBooking.getDestination(), currentBooking, ownerList, stationList))
           {
               flag=1;
               //// Log.i("currentBooking4",currentBooking.getId());
           }
           if(flag==0)
           {
               batch.update(currentBookingRef,"status","unapproved");
           }

        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
            }
        });
        // Log.i("abc","Between");
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
        {   // Log.i("currentBooking ",currentBooking.getId());
            Owner currentOwner = (Owner) ownerIterator.next();
            if(currentOwner.getCity().equalsIgnoreCase(ownerCity))
            {
                Iterator vehicleIterator = currentOwner.getListOfVehicle().iterator();
                int vehicleIndex =0;
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
                                {   // Log.i("booked",currentBooking.getId()+" "+currentVehicle.getName());
                                    currentVehicle.setStatus("busy");

                                    currentVehicleRef = dB.getFirestoreInstance().collection("vehicles").document(currentVehicle.getId());
                                    currentOwnerRef = dB.getFirestoreInstance().collection("owners").document(currentOwner.getId());
                                    currentBookingRef = dB.getFirestoreInstance().collection("bookings").document(currentBooking.getId());
                                    batch.update(currentBookingRef,"status","approved");
                                    batch.update(currentBookingRef,"vehicleRef",currentVehicleRef);
                                    batch.update(currentBookingRef,"vehicleRef",currentVehicleRef);
                                    batch.update(currentBookingRef,"ownerRef",currentOwnerRef);
                                    batch.update(currentBookingRef,"vehicleId",currentVehicle.getId());
                                    batch.update(currentBookingRef,"ownerId",currentOwner.getId());
                                    //batch.update(currentOwnerRef,"listOfVehicle."+Integer.toString(vehicleIndex)+".status","busy");
                                    batch.update(currentVehicleRef,"status","busy");
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
                                        {   // Log.i("booked",currentBooking.getId()+" "+currentVehicle.getName());
                                            currentVehicle.setStatus("busy");
                                            currentVehicleRef = dB.getFirestoreInstance().collection("vehicles").document(currentVehicle.getId());
                                            currentOwnerRef = dB.getFirestoreInstance().collection("owners").document(currentOwner.getId());
                                            currentBookingRef = dB.getFirestoreInstance().collection("bookings").document(currentBooking.getId());
                                           // batch.update(currentBookingRef,"status","approved");
                                            batch.update(currentBookingRef,"vehicleRef",currentVehicleRef);
                                            batch.update(currentBookingRef,"ownerRef",currentOwnerRef);
                                            batch.update(currentBookingRef,"vehicleId",currentVehicle.getId());
                                            batch.update(currentBookingRef,"ownerId",currentOwner.getId());
                                           // batch.update(currentOwnerRef,"listOfVehicle."+Integer.toString(vehicleIndex)+".status","busy");
                                            batch.update(currentVehicleRef,"status","busy");
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

                    vehicleIndex++;

                }
            }
        }
       // // Log.i("abc","1");
        ///// Log.i("abc Booking",Integer.toString(i));
        //i++;
       return false;
    }*/


    }
