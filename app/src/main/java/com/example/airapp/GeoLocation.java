package com.example.airapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GeoLocation {
    public static void getAddress(String locationAddress, Context context, Handler handler)
    {
        Thread thread = new Thread(){
            @Override
            public void run()
            {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String lati = null;
                String longi = null;
                try {
                    List addressList = geocoder.getFromLocationName(locationAddress,1);
                    if(addressList != null && addressList.size()>0)
                    {
                        Address address = (Address) addressList.get(0);
                        StringBuilder lan = new StringBuilder();
                        StringBuilder lon = new StringBuilder();
                        lan.append(address.getLatitude());
                        lon.append(address.getLongitude());
                        lati= lan.toString();
                        longi = lon.toString();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if(lati!=null && longi !=null)
                    {
                        message.what=1;
                        Bundle bundle = new Bundle();
                        bundle.putString("latitude",lati);
                        bundle.putString("longitude",longi);
                        message.setData(bundle);
                    }
                    message.sendToTarget();

                }
            }
    };
        thread.start();

    }
}
