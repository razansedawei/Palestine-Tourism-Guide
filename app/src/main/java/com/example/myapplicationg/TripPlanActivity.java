package com.example.myapplicationg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TripPlanActivity extends AppCompatActivity {
    Button createtrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_plan);

        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.plan);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationBarClickListener(this));

        createtrip = findViewById(R.id.create_trip);
        createtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencreattrip();
            }

        });
    }


    public void opencreattrip(){
        Intent intent = new Intent(this, CreateTripActivity.class);
        startActivity(intent);
    }


}
