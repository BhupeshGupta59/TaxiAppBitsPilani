package com.taxiapp.bitspilani.taxiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BookedCabDetails extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_cab_details);

        textView = findViewById(R.id.textView);
        textView.setText("in next page");

    }
}
