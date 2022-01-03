package com.example.airapp;

import androidx.appcompat.app.AppCompatActivity;

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

public class ResultActivity2 extends AppCompatActivity {
    String longit,latit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity_air);
        String la= getIntent().getStringExtra("latitude");
        String lo=getIntent().getStringExtra("longitutde");
        TextView wdsp,humd,tem,va;
        wdsp=findViewById(R.id.wdsp);
        humd= findViewById(R.id.humi);
        tem = findViewById(R.id.temp);
        va = findViewById(R.id.va);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+la+"&lon="+lo+"&appid=979298ef2741a60753a9609df0b33c07";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray we = response.getJSONArray("weather");
                            JSONObject te = we.getJSONObject(0);
                         JSONObject main = response.getJSONObject("main");
                         tem.setText(main.getString("temp"));
                         JSONObject wind = response.getJSONObject("wind");
                         wdsp.setText(wind.getString("speed"));
                         humd.setText(main.getString("humidity"));
                         String val=te.getString("description");

                            va.setText(val.toUpperCase());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        wdsp.setText("error");

                    }
                });
        requestQueue.add(jsonObjectRequest);

    }
}