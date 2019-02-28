package com.taxiapp.bitspilani.taxiapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
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

    //private ListView listView;
  // private List<Vehicle> vehicleList;
  // private List<Owner> ownerList;
   //private  List<Booking> bookingList;
   //private List<Station> stationList;
    private ListView listView1;
    private List<Booking> bookingList = new ArrayList<>();
    //private ArrayList<String> list;
    private ArrayList<String> list1;
    private Button scheduleButton;
    //Intent i = new Intent(this, RegisterActivity.class);

    // Declare variables



    // Store string resources into an Array
    String[] NAMES = new String[] { "Galaxy S", "Galaxy S2",
            "Galaxy Note", "Galaxy Beam","Galaxy S", "Galaxy S2",
            "Galaxy Note", "Galaxy Beam"};

    String[] DESCRIPTION = new String[] { "b", "c",
            "d", "e","b", "c",
            "d", "e"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // button =findViewById(R.id.button);
        nDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open, R.string.close);
        nDrawerLayout.addDrawerListener(nToggle);
        nToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ProgressDialog progressDialog1 = new ProgressDialog(MainActivity.this);
        final ProgressDialog progressDialog2 = new ProgressDialog(MainActivity.this);
        // t.getList();
        scheduleButton = (Button) findViewById(R.id.schedule);


        final Admin a = new Admin();
        progressDialog1.setMessage("Its Loading...");
        progressDialog1.show();
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

      dB.getFirestoreInstance().collection("bookings").orderBy("timestamp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    ListView listView;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Booking b = document.toObject(Booking.class);
                                bookingList.add(b);
                                Log.i("abc",bookingList.get(0).getSource());
                                // Log.i("abc",u.getDepartment());
                            }
                            final CustomAdapter customAdapter = new com.taxiapp.bitspilani.taxiapp.CustomAdapter(MainActivity.this,bookingList);
                            ImageView imageView = (ImageView)findViewById(R.id.imageView);
                            listView = (ListView) findViewById(R.id.listview) ;
                            listView.setAdapter(customAdapter);
                            progressDialog1.dismiss();

                            customAdapter.notifyDataSetChanged();
                        } else {
                            progressDialog1.dismiss();
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

       // a.bookCab();



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




}
