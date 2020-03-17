package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CityActivity extends AppCompatActivity {
    Button goToMapBtnl;
    AppCompatImageButton goToRestBtnl;
    AppCompatImageButton goToHotelbtn;
    AppCompatImageButton goToPlacesbtn;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.homee);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.homee:
                        return true;

                }

                return false;
            }
        });



        goToMapBtnl = findViewById(R.id.map_activity_button);
        goToMapBtnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapActivity();
            }
        });

        goToRestBtnl = findViewById(R.id.bb6);
        goToRestBtnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRestActivity();
            }
        });

        goToHotelbtn = findViewById(R.id.bb1);
        goToHotelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHotelActivity();
            }
        });
        goToPlacesbtn = findViewById(R.id.bb4);
        goToPlacesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlacesActivity();
            }
        });

    }
    public void openMapActivity(){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openRestActivity(){
        Intent intent = new Intent(this, RestaurantsListActivity.class);
        startActivity(intent);
    }
    public void openHotelActivity(){
        Intent intent = new Intent(this, HotelListActvity.class);
        startActivity(intent);
    }
    public void openPlacesActivity(){
        Intent intent = new Intent(this, PlacesListActivity.class);
        startActivity(intent);
    }
}
