package com.taxiapp.bitspilani.taxiapp;


import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
import java.util.List;
import java.util.Map;

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
        nToggle = new ActionBarDrawerToggle(this,nDrawerLayout,R.string.open,R.string.close);
        nDrawerLayout.addDrawerListener(nToggle);
        nToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // t.getList();
        scheduleButton = (Button)findViewById(R.id.schedule);




        Booking b1 = new Booking("Delhi","Pilani", new Timestamp(new Date(2019-1900,2,01,7,00)),"pending","micro");
        Booking b2 = new Booking("Delhi","Pilani", new Timestamp(new Date(2019-1900,2,02,8,00)),"pending","micro");
        Booking b3 = new Booking("Delhi","Pilani", new Timestamp(new Date(2019-1900,2,03,9,00)),"pending","micro");
        Booking b4 = new Booking("Jaipur","Pilani", new Timestamp(new Date(2019-1900,2,04,10,00)),"pending","suv");
        Booking b5 = new Booking("Jaipur","Pilani", new Timestamp(new Date(2019-1900,2,05,11,00)),"pending","micro");
        Booking b6 = new Booking("Jaipur","Pilani", new Timestamp(new Date(2019-1900,2,05,12,00)),"pending","micro");
        Booking b7 = new Booking("Pilani","Delhi", new Timestamp(new Date(2019-1900,2,06,13,00)),"pending","suv");
        Booking b8 = new Booking("Pilani","Jaipur", new Timestamp(new Date(2019-1900,2,06,16,00)),"pending","micro");
        Booking b9 = new Booking("Pilani","Noida", new Timestamp(new Date(2019-1900,2,06,19,00)),"pending","micro");
        Booking b10 = new Booking("Pilani","Ghaziabad", new Timestamp(new Date(2019-1900,2,07,10,00)),"pending","suv");
        Booking b11 = new Booking("Pilani","Sikkar", new Timestamp(new Date(2019-1900,2,07,15,00)),"pending","suv");

        dB.getFirestoreInstance().collection("bookings").document(b1.getId()).set(b1);
        dB.getFirestoreInstance().collection("bookings").document(b2.getId()).set(b2);
        dB.getFirestoreInstance().collection("bookings").document(b3.getId()).set(b3);
        dB.getFirestoreInstance().collection("bookings").document(b4.getId()).set(b4);
        dB.getFirestoreInstance().collection("bookings").document(b5.getId()).set(b5);
        dB.getFirestoreInstance().collection("bookings").document(b6.getId()).set(b6);
        dB.getFirestoreInstance().collection("bookings").document(b7.getId()).set(b7);
        dB.getFirestoreInstance().collection("bookings").document(b8.getId()).set(b8);
        dB.getFirestoreInstance().collection("bookings").document(b9.getId()).set(b9);
        dB.getFirestoreInstance().collection("bookings").document(b10.getId()).set(b10);
        dB.getFirestoreInstance().collection("bookings").document(b11.getId()).set(b11);

        List<String> user1 = new ArrayList<>();
        List<String> user2 = new ArrayList<>();
        List<String> user3 = new ArrayList<>();
        List<String> user4 = new ArrayList<>();
        List<String> user5 = new ArrayList<>();
        List<String> user6 = new ArrayList<>();
        user1.add(b1.getId());
        user2.add(b2.getId());
        user3.add(b3.getId());
        user4.add(b4.getId());
        user5.add(b5.getId());
        user6.add(b6.getId());
        user1.add(b7.getId());
        user2.add(b8.getId());
        user3.add(b9.getId());
        user4.add(b10.getId());
        user5.add(b11.getId());


        User u1 = new User("FirstUser","1111111111","firstuser@gmail.com","FirstDepartment",user1,null);
        dB.getFirestoreInstance().collection("users").document(u1.getId()).set(u1);
        User u2 = new User("SecondUser","2222222222","seconduser@gmail.com","SecondDepartment",user2,null);
        dB.getFirestoreInstance().collection("users").document(u2.getId()).set(u2);
        User u3 = new User("ThirdUser","3333333333","thirduser@gmail.com","ThirdDepartment",user3,null);
        dB.getFirestoreInstance().collection("users").document(u3.getId()).set(u3);
        User u4 = new User("FourthUser","4444444444","fourthuser@gmail.com","FourthDepartment",user4,null);
        dB.getFirestoreInstance().collection("users").document(u4.getId()).set(u4);
        User u5 = new User("FifthUser","5555555555","fifthuser@gmail.com","FifthDepartment",user5,null);
        dB.getFirestoreInstance().collection("users").document(u5.getId()).set(u5);
        User u6 = new User("SixthUser","6666666666","sixthuser@gmail.com","SixthDepartment",user6,null);
        dB.getFirestoreInstance().collection("users").document(u6.getId()).set(u6);





        Driver d1 =new Driver("FirstDriver","1111111111","firstdriver@gmail.com","1111",null,null);
        Driver d2 =new Driver("SecondDriver","2222222222","seconddriver@gmail.com","2222",null,null);
        Driver d3 =new Driver("ThirdDriver","3333333333","thirddriver@gmail.com","3333",null,null);
        Driver d4 =new Driver("FourthDriver","4444444444","fourthdriver@gmail.com","4444",null,null);
        Driver d5 =new Driver("FifthDriver","5555555555","fifthdriver@gmail.com","5555",null,null);
        Driver d6 =new Driver("SixthDriver","6666666666","sixthdriver@gmail.com","6666",null,null);
        dB.getFirestoreInstance().collection("drivers").document(d1.getId()).set(d1);
        dB.getFirestoreInstance().collection("drivers").document(d2.getId()).set(d2);
        dB.getFirestoreInstance().collection("drivers").document(d3.getId()).set(d3);
        dB.getFirestoreInstance().collection("drivers").document(d4.getId()).set(d4);
        dB.getFirestoreInstance().collection("drivers").document(d5.getId()).set(d5);
        dB.getFirestoreInstance().collection("drivers").document(d6.getId()).set(d6);


        //Log.i("abc",t.getStationList().get(0).getName());
        //Date date = new Date();
       // Date d = new Date(2019,2,22);


        //Log.i("abc",d.toString());
        //Booking b1 = new Booking("7","Delhi","Pilani", new Timestamp(new Date(2019-1900,2,22)),"13:00",null,null,"pending","micro","6");
      // dB.getFirestoreInstance().collection("bookings").document("7").set(b1);
    /*
        dB.getFirestoreInstance().collection("bookings").orderBy("journeyDate")
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

                            CustomAdapter customAdapter = new com.taxiapp.bitspilani.taxiapp.CustomAdapter(MainActivity.this,bookingList);
                            ImageView imageView = (ImageView)findViewById(R.id.imageView);

                            listView = (ListView) findViewById(R.id.listview) ;
                            listView.setAdapter(customAdapter);
                            scheduleButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TaskInSync t = new TaskInSync();

                                    t.getList();
                                }
                            });

                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }



                });

        */


        //Log.i("abc",bookingList.get(0).getSource());


        //populateListView();

      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, NAMES[position],Toast.LENGTH_SHORT ).show();
            }
        });*/
        /*List<Booking> bList1 = new ArrayList<>();
        List<Booking> bList2 = new ArrayList<>();
        Booking b1 = new Booking("1","Delhi","Pilani", new Timestamp(new Date(2019,2,22,13,00)),"13:00",null,null,"pending","micro");
        Booking b2 = new Booking("2","Delhi","Pilani",new Timestamp(new Date(2019,2,23,9,00)),"09:00",null,null,"pendng","micro");
        Booking b3 = new Booking("3","Pilani","Delhi",new Timestamp(new Date(2019,2,24,11,00)),"11:00",null,null,"pending","suv");
        Booking b4 = new Booking("4","Pilani","Delhi",new Timestamp(new Date(2019,2,25,15,00)),"15:00",null,null,"pending","micro");
        Booking b5 = new Booking("5","Pilani","Delhi",new Timestamp(new Date(2019,2,22,6,00)),"15:00",null,null,"pending","micro");
        bList1.add(b1);
        bList1.add(b2);
        bList1.add(b3);
        bList2.add(b4);
        bList2.add(b5);

        User u1 = new User("1","Ismail","88474384","ismail@gmail.com","Computer Science",bList1,"2");
        User u2 = new User("2","Mayur","73458395","mayur@gmail.com","Computer Science",bList2,"2");*/
        //dB.getFirestoreInstance().collection("users").document(u1.getId()).set(u1);
        //dB.getFirestoreInstance().collection("users").document(u2.getId()).set(u2);

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
