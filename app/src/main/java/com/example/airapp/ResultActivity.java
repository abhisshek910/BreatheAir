package com.example.airapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {
    String longit,latit,aq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        TextView aqi,n02,c0,s02,o3,no,nh3,value;
        aqi = findViewById(R.id.humi);
        n02=findViewById(R.id.no2);
        c0=findViewById(R.id.co);
        s02 = findViewById(R.id.s02);
        o3=findViewById(R.id.o3);
        no = findViewById(R.id.no);
        nh3 = findViewById(R.id.nh3);
        value = findViewById(R.id.value);

        String la= getIntent().getStringExtra("latitude");
        String lo=getIntent().getStringExtra("longitutde");

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        String url = "http://api.openweathermap.org/data/2.5/air_pollution?lat="+la+"&lon="+lo+"&appid=979298ef2741a60753a9609df0b33c07";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("list");
                            JSONObject temp = list.getJSONObject(0);
                            JSONObject main = temp.getJSONObject("main");
                            JSONObject comp = temp.getJSONObject("components");
                            aqi.setText(main.getString("aqi"));
                            n02.setText(comp.getString("no2"));
                            c0.setText(comp.getString("co"));
                            s02.setText(comp.getString("so2"));
                            o3.setText(comp.getString("o3"));
                            no.setText(comp.getString("no"));
                            nh3.setText(comp.getString("nh3"));
                            aq = main.getString("aqi");
                            if(aq.equals("1"))
                            value.setText("Good Air Quality");
                            else if(aq.equals("2"))
                                value.setText("Fair Air Quality");
                            else if(aq.equals("3"))
                                value.setText("Moderate Air Quality");
                            else if(aq.equals("4"))
                                value.setText("Poor Air Quality");
                            else
                                value.setText("Very Poor Air Quality");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });

// Access the RequestQueue through your singleton class.
        requestQueue.add(jsonObjectRequest);
    }



}