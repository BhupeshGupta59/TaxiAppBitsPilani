package com.taxiapp.bitspilani.taxiapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.api.core.ApiFuture;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.taxiapp.bitspilani.CommonDBOperation.Database;
import com.taxiapp.bitspilani.pojo.Admin;
import com.taxiapp.bitspilani.pojo.Booking;
import com.taxiapp.bitspilani.pojo.Driver;
import com.taxiapp.bitspilani.pojo.Owner;
import com.taxiapp.bitspilani.pojo.PersonDetails;

import com.taxiapp.bitspilani.pojo.Station;
import com.taxiapp.bitspilani.pojo.User;
import com.taxiapp.bitspilani.pojo.Vehicle;




import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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


        btnSchedule = (Button) findViewById(R.id.schedule);


        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        listView = (ListView) findViewById(R.id.listview) ;

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
                });*/
      PD.show();
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
                                bookingList.add(b);
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

                            CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,bookingList);

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
        });

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


    private class ScheduleTask extends AsyncTask<String,Integer,List<String>>{
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
                                    bookingList.add(b);
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

                                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,bookingList);

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
    }
}
