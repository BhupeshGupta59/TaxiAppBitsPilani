package com.taxiapp.bitspilani.taxiapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.taxiapp.bitspilani.pojo.Booking;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class CustomAdapter extends ArrayAdapter<Booking>
{
    int[]  IMAGES = {R.drawable.user,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.b1,R.drawable.c1,R.drawable.d1,R.drawable.e1};

    public CustomAdapter(Context context, List<Booking> object){
        super(context,0, object);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.da_item,null);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        TextView textView_user  = (TextView) convertView.findViewById(R.id.textView_user);
        TextView textView_phone  = (TextView) convertView.findViewById(R.id.textView_phone);
        TextView textView_source  = (TextView) convertView.findViewById(R.id.textView_source);
        TextView textView_dest  = (TextView) convertView.findViewById(R.id.textView_dest);
        TextView textView_date  = (TextView) convertView.findViewById(R.id.textView_date);
        TextView textView_time  = (TextView) convertView.findViewById(R.id.textView_time);
        TextView textView_status  = (TextView) convertView.findViewById(R.id.textView_status);

        Booking b = getItem(position);
        Timestamp dateTime= b.getTimestamp();
        Date date = dateTime.toDate();
        String strDateFormat = "hh:mm a";
        String dFormat = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        DateFormat dFormat1 =new SimpleDateFormat(dFormat);
        String formattedDate= dateFormat.format(date);
        String formatted= dFormat1.format(date);

        imageView.setImageResource(IMAGES[0]);
        textView_source.setText("Source : "+b.getSource());
        textView_dest.setText("Destination: "+b.getDestination());
        textView_time.setText("Time: "+formattedDate);
        textView_date.setText("Date: "+formatted);
        textView_status.setText(b.getStatus().toUpperCase());
        textView_status.setTextColor(Color.parseColor("#ff0000"));
       // textView_source.setTypeface(textView_source.getTypeface(), Typeface.BOLD);
        //textView_source.append("abc");


        return convertView;
    }







}

