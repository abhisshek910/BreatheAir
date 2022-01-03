package com.example.airapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class address extends AppCompatActivity {
    Button weather,air;
    EditText city;
    String longit, latit,address;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        weather = findViewById(R.id.weather);
        air = findViewById(R.id.air);
        city = findViewById(R.id.city);
        air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                address = city.getText().toString();
                if(address.matches(""))
                    Toast.makeText(address.this, "PLEASE ENTER ALL THE FIELDS", Toast.LENGTH_SHORT).show();
                else
                {
                GeoLocation geoLocation = new GeoLocation();
                geoLocation.getAddress(address, getApplicationContext(), new GeoHandler());}
            }

        });
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                address = city.getText().toString();
                if(address.matches(""))
                    Toast.makeText(address.this, "PLEASE ENTER ALL THE FIELDS", Toast.LENGTH_SHORT).show();
                else
                {
                GeoLocation geoLocation = new GeoLocation();
                geoLocation.getAddress(address, getApplicationContext(), new GeoHandler());}

            }
        });

    }


    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    longit = bundle.getString("longitude");
                    latit = bundle.getString("latitude");
                    break;
                default:
                    longit = null;
                    latit = null;
            }
            if(flag==0)
           openResult1();
            else
                openResult2();


        }


    }


    public void openResult1() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("longitutde", longit);
      intent.putExtra("latitude", latit);
       // intent.putExtra("city",address);
        startActivity(intent);


    }
    public void openResult2() {
        Intent intent = new Intent(this, ResultActivity2.class);
        intent.putExtra("longitutde", longit);
        intent.putExtra("latitude", latit);
        startActivity(intent);


    }
};


