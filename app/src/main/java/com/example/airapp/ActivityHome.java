package com.example.airapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityHome extends Activity {
    private Button map,add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        map = (Button) findViewById(R.id.map);
        add = (Button) findViewById(R.id.add);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapActivity();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddActivity();
            }
        });
    }
    public void openMapActivity()
    {
        Intent intent = new Intent(ActivityHome.this,map.class);
        startActivity(intent);
    }
    public void openAddActivity()
    {
        Intent intent = new Intent(this,address.class);
        startActivity(intent);
    }
}
