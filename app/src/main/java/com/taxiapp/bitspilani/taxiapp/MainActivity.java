package com.taxiapp.bitspilani.taxiapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.taxiapp.bitspilani.CommonDBOperation.Database;
import com.taxiapp.bitspilani.UIComponent.OwnerDriverVehicleTestCases;
import com.taxiapp.bitspilani.pojo.Admin;
import com.taxiapp.bitspilani.pojo.Booking;
import com.taxiapp.bitspilani.pojo.Driver;
//import com.taxiapp.bitspilani.pojo.Example;
import com.taxiapp.bitspilani.pojo.Owner;
import com.taxiapp.bitspilani.pojo.PersonDetails;

import com.taxiapp.bitspilani.pojo.ReportBooking;
import com.taxiapp.bitspilani.pojo.User;
import com.taxiapp.bitspilani.pojo.Vehicle;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    private Database dB = new Database();
    private DatabaseReference ref;
    private PersonDetails person;
    private DrawerLayout nDrawerLayout;
    private ActionBarDrawerToggle nToggle;
    private ProgressDialog PD;
    private ListView listView1;
    private List<Booking> bookingList = new ArrayList<>();
    private Map<Booking,User> bookingDetails = new HashMap<>();
    private Button btnSchedule;
    private Button btnDownload;
    private Button testCase;
    private String fileName;

    private StorageReference storageRef;
    SwipeRefreshLayout mySwipeRefreshLayout;
    Admin a  = new Admin();
    ListView listView;
    //private Admin scheduleBooking = new Admin();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        nDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open, R.string.close);
        nDrawerLayout.addDrawerListener(nToggle);
        nToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        btnSchedule = (Button) findViewById(R.id.schedule);
        btnDownload = (Button) findViewById(R.id.downloadButton);
       // testCase = (Button) findViewById(R.id.button3);
        btnDownload.setEnabled(false);
        storageRef = FirebaseStorage.getInstance().getReference();

        Log.i("abc","csdvd");

        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        listView = (ListView) findViewById(R.id.listview) ;

        final List<Booking> bList = new ArrayList<>();

        Log.i("async","Complete");
        new ScheduleTask().execute();
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("abc", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                       new ScheduleTask().execute();
                    }
                }
        );


       //a.bookCab();
      // dB.otherTestCases(MainActivity.this);
       //dB.bookingTestCases(MainActivity.this);
     /*   new ScheduleTask().execute();
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("abc","schedule");

              a.bookCab(MainActivity.this);
                new ScheduleTask().execute();
               //Uncomment  a.bookCab(MainActivity.this);
               // a.testBookCab(MainActivity.this);
                    //dB.bookingTestCases();
               // dB.otherTestCases(MainActivity.this);
              // dB.otherTestCases(MainActivity.this);
              // dB.otherTestCases(MainActivity.this);
                //a.testBookCab(MainActivity.this);



              // Log.i("abcde",fileName);

            }
        });
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task getUrl = storageRef.child("csv/"+fileName).getDownloadUrl();

                getUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                        // Got the download URL for 'users/me/profile.png'
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

            }
        });
*/

     /*   btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageRef.child("csv/profile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        });*/

        /*
        PD.show();
        dB.getFirestoreInstance().collection("bookings").orderBy("timestamp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    ListView listView;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           // WriteBatch batch = dB.getFirestoreInstance().batch();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Booking b = document.toObject(Booking.class);
                                //DocumentReference d = dB.getFirestoreInstance().collection("bookings").document(b.getId());
                                //batch.update(d,"status","pending");
                                bookingList.add(b);
                                Log.i("abc",bookingList.get(0).getSource());
                                // Log.i("abc",u.getDepartment());
                            }
                           /* batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // ...
                                }
                            });*/
       /*
                            final CustomAdapter customAdapter = new com.taxiapp.bitspilani.taxiapp.CustomAdapter(MainActivity.this,bookingList);
                            ImageView imageView = (ImageView)findViewById(R.id.imageView);
                            listView = (ListView) findViewById(R.id.listview) ;
                            listView.setAdapter(customAdapter);
                            PD.dismiss();

                            customAdapter.notifyDataSetChanged();
                        } else {
                           PD.dismiss();
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });*/
        // Log.i("abc",bookingList.get(0).getId());
       /* Task<QuerySnapshot> bookingTask = dB.getFirestoreInstance().collection("bookings").orderBy("timestamp").get();
       bookingTask.continueWithTask(new Continuation<QuerySnapshot, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<QuerySnapshot> task) throws Exception {

                for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult())
                {
                    Booking b = queryDocumentSnapshot.toObject(Booking.class);
                    bList.add(b);
                }


                // Log.i("abc",stationList.get(0).getId());
                //Log.i("abc",ownerList.get(0).getId());
                return doSomething(bList);
            }
        });*/
      /*  dB.getFirestoreInstance().collection("bookings")
                .orderBy("timestamp")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    ListView listView;


                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("Error", "Listen failed.", e);
                            return;
                        }


                        for (QueryDocumentSnapshot doc : value) {
                            Booking b = doc.toObject(Booking.class);
                            bookingList.add(b);
                        }
                        final CustomAdapter customAdapter = new com.taxiapp.bitspilani.taxiapp.CustomAdapter(MainActivity.this,bookingList);
                        ImageView imageView = (ImageView)findViewById(R.id.imageView);
                        listView = (ListView) findViewById(R.id.listview) ;
                        listView.setAdapter(customAdapter);
                       progressDialog1.dismiss();
                        //customAdapter.notifyDataSetChanged();
                       // Log.d(TAG, "Current cites in CA: " + cities);
                    }
                });
     /* PD.show();
      dB.getFirestoreInstance().collection("bookings").orderBy("timestamp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    ListView listView;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {



                               final Booking b = document.toObject(Booking.class);
                                //dB.getDatabaseRef().child("users").child(b.getUserId()).child("name").getKey();
                                //Log.i("abc","-------------------------------"+dB.getDatabaseRef().child("users").child(b.getUserId()).child("name").);
                                //Log.i("abc","-------------------------------"+b.getUserId()+", "+ b.getId() );
                                /*dB.getFirestoreInstance().collection("users")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for(QueryDocumentSnapshot document1 : task.getResult())
                                                    {
                                                        final User b1 = document1.toObject(User.class);
                                                        if(b.getUserId().equals(b1.getId()))
                                                        {

                                                            bookingDetails.put(b,b1);
                                                            break;

                                                        }
                                                    }
                                                }
                                            }
                                        });*/
                                //break;
                              /*  bookingList.add(b);
                                Log.i("abc",bookingList.get(0).getSource());
                                Log.i("abc",""+bookingDetails.size());
                            }


                            /*Set<Map.Entry<Booking,User>> hashSet=bookingDetails.entrySet();
                            for(Map.Entry entry:hashSet ) {

                                Booking book = (Booking)entry.getKey();
                                User user = (User) entry.getValue();
                                Log.i("abc","Key="+book.getId());
                                Log.i("abc"," Value="+user.getId());
                            }*/

                          /*  CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,bookingList);

                            listView = (ListView) findViewById(R.id.listview) ;
                            listView.setAdapter(customAdapter);

                            PD.dismiss();

                            customAdapter.notifyDataSetChanged();
                        } else {
                            PD.dismiss();
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ScheduleTask()
                        .execute();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //Map<Booking,User> bookingDetails  = (HashMap)adapter.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this,BookedCabDetails.class);
                //based on item add info to intent
                //intent.putExtra("bookingDetails", bookingDetails);
                startActivity(intent);

            }
        });*/


    }
    public Task<Void> doSomething(final List<Booking> bList)
    {
       for(int i=0;i<bList.size();i++)
       {
           final Booking b = bList.get(i);
           String userId = bList.get(i).getUserId() ;
           dB.getFirestoreInstance().collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
               @Override
               public void onSuccess(DocumentSnapshot documentSnapshot) {
                  User u = documentSnapshot.toObject(User.class);
                   bookingDetails.put(b,u);
               }
           });
       }
       return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(nToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu., menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();



    }
    public void downloadFile(String name)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(name));
        startActivity(intent);
    }
    private class ScheduleTask extends AsyncTask<String,Integer,List<Booking>>{
        @Override
        protected List<Booking> doInBackground(String... strings) {

           // a.bookCab();
            Timestamp currentTimestamp = new Timestamp(new Date());
            CollectionReference bookingRef = dB.getFirestoreInstance().collection("bookings");
           // bookingRef.whereGreaterThanOrEqualTo("timestamp",currentTimestamp);

            Task t1 = bookingRef.whereGreaterThan("timestamp",currentTimestamp).orderBy("timestamp").get();
            //Task t2 = a.bookCab();
            try {

                Tasks.await(t1);


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                    t1.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                bookingList.clear();
                                // WriteBatch batch = dB.getFirestoreInstance().batch();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Booking b = document.toObject(Booking.class);
                                    //DocumentReference d = dB.getFirestoreInstance().collection("bookings").document(b.getId());
                                    //batch.update(d,"status","pending");
                                    bookingList.add(b);
                                   // Log.i("abc",bookingList.get(0).getSource());
                                    // Log.i("abc",u.getDepartment());
                                }
                           /* batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // ...
                                }
                            });*/





                        } else {

                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
            //Log.i("abc","Between");
            Database dB = new Database();
            //Uncomment
                //return bookingList;
            return bookingList; //Comment
        }

        // Before the tasks execution
        protected void onPreExecute(){
            // Display the progress dialog on async task start
            PD.show();
           // Log.i("abc","Pre");
            //PD.show();
        }


        // After each task done
        protected void onProgressUpdate(Integer... progress){
            // Update the progress bar on dialog

        }

        // When all async task done
        protected void onPostExecute(List<Booking> result){
            // Hide the progress dialog
            ListView listView;
            final CustomAdapter customAdapter = new com.taxiapp.bitspilani.taxiapp.CustomAdapter(MainActivity.this,result);
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            listView = (ListView) findViewById(R.id.listview) ;
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    final Intent i = new Intent(getApplicationContext(), BookingInformation.class);

                    Booking b = (Booking) parent.getItemAtPosition(position);

                    i.putExtra("Source", b.getSource());
                    i.putExtra("Destination", b.getDestination());
                    i.putExtra("carType", b.getCarType());
                    i.putExtra("status", b.getStatus());

                    Timestamp dateTime= b.getTimestamp();
                    Date date = dateTime.toDate();
                    String strDateFormat = "hh:mm a";
                    String dFormat = "dd/MM/yyyy";
                    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                    DateFormat dFormat1 =new SimpleDateFormat(dFormat);
                    String dateFormatted = dFormat1.format(date);
                    String timeFormat= dateFormat.format(date);

                    i.putExtra("dateFormatted", dateFormatted);
                    i.putExtra("timeFormat", timeFormat);

                    i.putExtra("journeyStartTime", b.getJourneyStartTime());
                    i.putExtra("journeyEndTIme", b.getJourneyEndTIme());

                    i.putExtra("ownerid", b.getOwnerId());
                    i.putExtra("driverid", b.getDriverId());
                    i.putExtra("vehicleid", b.getVehicleId());
                    i.putExtra("userid", b.getUserId());

                    startActivity(i);

                }
            });

            new DownloadTask().execute();

        }
    }
    private class DownloadTask extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... strings) {

            // a.bookCab();

            File csvFile = null;

            FileOutputStream fileout= null;
            Uri fileUri = null;
            try {
                Timestamp reportTime = new Timestamp(new Date());
                Date reportDate = reportTime.toDate();
                SimpleDateFormat ft =new SimpleDateFormat ("dd.MM.YYYY_hh:mm_a");
                fileName = "Report_"+ft.format(reportDate)+".csv";
                csvFile = new File(getCacheDir(),fileName);
                fileout = new FileOutputStream(csvFile);
                OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

                outputWriter.write("BookingId,Source,Destination,CarType,BookingStatus,OwnerName,OwnerCity,VehicleName,VehicleCity,BookingTime,Reason");

                List<ReportBooking> aList = a.getPrintList();
                for(int i=0;i<aList.size();i++) {
                    outputWriter.write("\n");
                    outputWriter.write(aList.get(i).getId()+","+aList.get(i).getSource()+","+aList.get(i).getDestination()+","+aList.get(i).getCarType()+","+aList.get(i).getStatus()+","+aList.get(i).getOwnerName()+","+aList.get(i).getOwnerCity()+","+aList.get(i).getVehicleName()+","+aList.get(i).getVehicleLocation()+","+aList.get(i).getTimestamp().toDate().toString()+","+aList.get(i).getReason());
                }
                outputWriter.close();
                fileUri = Uri.fromFile(csvFile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StorageReference reportRef = FirebaseStorage.getInstance().getReference().child("csv/"+fileUri.getLastPathSegment());
            UploadTask uploadTask = reportRef.putFile(fileUri);
            try {
                Tasks.await(uploadTask);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });


            // bookingRef.whereGreaterThanOrEqualTo("timestamp",currentTimestamp);


            //Task t2 = a.bookCab();
            //fileName = csvFile.toString();
            return  fileName;

        }

        // Before the tasks execution
        protected void onPreExecute(){
            // Display the progress dialog on async task start
            PD.show();
           // Log.i("abc","Pre");
            //PD.show();
        }


        // After each task done
        protected void onProgressUpdate(Integer... progress){
            // Update the progress bar on dialog

        }

        // When all async task done
        protected void onPostExecute(String result){
            fileName = result;
           // Log.i("abcde",fileName);
            PD.dismiss();
           btnDownload.setEnabled(true);
            // Hide the progress dialog
           /* ListView listView;
            final CustomAdapter customAdapter = new com.taxiapp.bitspilani.taxiapp.CustomAdapter(MainActivity.this,result);
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            listView = (ListView) findViewById(R.id.listview) ;
            listView.setAdapter(customAdapter);
            PD.dismiss();
            Log.i("abc","Post");*/

        }
    }
    /*private class ScheduleTask extends AsyncTask<String,Integer,List<String>>{
        @Override
        protected List<String> doInBackground(String... strings) {
           a.bookCab();
            dB.getFirestoreInstance().collection("bookings").orderBy("timestamp")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        ListView listView;
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                bookingList.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {



                                    Booking b = document.toObject(Booking.class);
                                    //dB.getDatabaseRef().child("users").child(b.getUserId()).child("name").getKey();
                                    //Log.i("abc","-------------------------------"+dB.getDatabaseRef().child("users").child(b.getUserId()).child("name").);
                                    //Log.i("abc","-------------------------------"+b.getUserId()+", "+ b.getId() );
                                /*dB.getFirestoreInstance().collection("users")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for(QueryDocumentSnapshot document1 : task.getResult())
                                                    {
                                                        final User b1 = document1.toObject(User.class);
                                                        if(b.getUserId().equals(b1.getId()))
                                                        {

                                                            bookingDetails.put(b,b1);
                                                            break;

                                                        }
                                                    }
                                                }
                                            }
                                        });*/
                                    //break;
                                  /*  bookingList.add(b);
                                    Log.i("abc",bookingList.get(0).getSource());
                                    Log.i("abc",""+bookingDetails.size());
                                }


                            /*Set<Map.Entry<Booking,User>> hashSet=bookingDetails.entrySet();
                            for(Map.Entry entry:hashSet ) {

                                Booking book = (Booking)entry.getKey();
                                User user = (User) entry.getValue();
                                Log.i("abc","Key="+book.getId());
                                Log.i("abc"," Value="+user.getId());
                            }*/

                             /*   CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,bookingList);

                                listView = (ListView) findViewById(R.id.listview) ;
                                listView.setAdapter(customAdapter);

                                PD.dismiss();

                                customAdapter.notifyDataSetChanged();
                            } else {
                                PD.dismiss();
                                Log.d("Error", "Error getting documents: ", task.getException());
                            }
                        }
                    });

            return null;
        }

        // Before the tasks execution
        protected void onPreExecute(){
            // Display the progress dialog on async task start
            PD.show();
        }


        // After each task done
        protected void onProgressUpdate(Integer... progress){
            // Update the progress bar on dialog

        }

        // When all async task done
        protected void onPostExecute(List<String> result){
            // Hide the progress dialog
            PD.dismiss();
        }
    }*/
}
