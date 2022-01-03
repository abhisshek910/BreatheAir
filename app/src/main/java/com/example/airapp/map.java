package com.example.airapp;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class map extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gmap;
    CheckBox checkBox;
    Button weather,air;
    FusedLocationProviderClient client;
    String longit,latit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        checkBox = findViewById(R.id.checkBox);
        weather = findViewById(R.id.button6);
        air = findViewById(R.id.button5);
        client = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(map.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(map.this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    getlocation();
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }

            }
        });
        air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                {
                    openResult1();
                }
                else
                {
                    Toast.makeText(map.this, "PLEASE SELECT YOUR LOCATION", Toast.LENGTH_SHORT).show();
                }
            }
        });
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                {
                    openResult2();
                }
                else
                {
                    Toast.makeText(map.this, "PLEASE SELECT YOUR LOCATION", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void openResult2() {
        Intent intent = new Intent(this, ResultActivity2.class);
        intent.putExtra("longitutde", longit);
        intent.putExtra("latitude", latit);
        startActivity(intent);
    }

    private void openResult1() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("longitutde", longit);
        intent.putExtra("latitude", latit);
        // intent.putExtra("city",address);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && (grantResults.length>0)&&(grantResults[0] + grantResults[1]== PackageManager.PERMISSION_GRANTED)){
            getlocation();
        }
        else
        {
            Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getlocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location!=null)
                    {
                        longit = String.valueOf(location.getLongitude());
                        latit = String.valueOf(location.getLatitude());
                        LatLng pos = new LatLng(location.getLatitude(),location.getLongitude());
                        gmap.addMarker(new MarkerOptions().position(pos));
                        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,15f));



                    }
                    else
                    {
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(10000).setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                longit = String.valueOf(location1.getLongitude());
                                latit = String.valueOf(location1.getLatitude());
                                LatLng pos = new LatLng(location1.getLatitude(),location1.getLongitude());

                                gmap.addMarker(new MarkerOptions().position(pos));
                                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,15f));

                            }
                        };
                        client.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                    }

                }
            });
        }else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap=googleMap;


    }
}