package com.taxiapp.bitspilani.taxiapp;


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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.taxiapp.bitspilani.CommonDBOperation.Database;
import com.taxiapp.bitspilani.pojo.Admin;
import com.taxiapp.bitspilani.pojo.Booking;
import com.taxiapp.bitspilani.pojo.Owner;
import com.taxiapp.bitspilani.pojo.PersonDetails;
import com.taxiapp.bitspilani.pojo.Station;
import com.taxiapp.bitspilani.pojo.Vehicle;


import java.util.ArrayList;
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
    //private ArrayList<String> list;
    private ArrayList<String> list1;
    //Intent i = new Intent(this, RegisterActivity.class);

    // Declare variables
    ListView listView;

    int[]  IMAGES = {R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.b1,R.drawable.c1,R.drawable.d1,R.drawable.e1};
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
        TaskInSync t = new TaskInSync();
        t.getList();
        //Log.i("abc",t.getStationList().get(0).getName());

        CustomAdapter customAdapter =  new CustomAdapter();
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        listView = (ListView) findViewById(R.id.listview) ;
        listView.setAdapter(customAdapter);
        //populateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, NAMES[position],Toast.LENGTH_SHORT ).show();
            }
        });


    }

   private void populateListView()
    {


        // Locate ListView in listview_main.xml
        listView = (ListView) findViewById(R.id.listview);

        // Bind array strings into an adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.da_item,
                NAMES);
        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
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

     class CustomAdapter extends BaseAdapter
     {

         @Override
         public int getCount() {
             return IMAGES.length;
         }

         @Override
         public Object getItem(int position) {
             return null;
         }

         @Override
         public long getItemId(int position) {
             return 0;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             convertView = getLayoutInflater().inflate(R.layout.da_item,null);
             ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
             TextView textView_name  = (TextView) convertView.findViewById(R.id.textView_name);
             TextView textView_desc  = (TextView) convertView.findViewById(R.id.textView_desc);

             imageView.setImageResource(IMAGES[position]);
             textView_name.setText(NAMES[position]);
             textView_desc.setText(DESCRIPTION[position]);
             return convertView;
         }
     }

}
