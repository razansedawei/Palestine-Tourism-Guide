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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CityActivity extends AppCompatActivity {
    Button goToMapBtnl;
    AppCompatImageButton goToRestBtnl;
    AppCompatImageButton goToHotelbtn;
    AppCompatImageButton goToPlacesbtn;
    AppCompatImageButton goToWeatherbtn;
    AppCompatImageButton goToTaxibtn;
    AppCompatImageButton goToThingToDo;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationBarClickListener(this));



        goToMapBtnl = findViewById(R.id.map_activity_button);
        goToMapBtnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapActivity(null, null);
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
        goToPlacesbtn = findViewById(R.id.bb5);
        goToPlacesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openThingtodoActivity();
            }
        });
        goToHotelbtn = findViewById(R.id.bb3);
        goToHotelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeatherActivity();
            }
        });
        goToTaxibtn = findViewById(R.id.bb2);
        goToTaxibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MarkerOptions city = new MarkerOptions().position(new LatLng(32.2264821,35.2685216)).title("Nablus");

                ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
                markers.add(new MarkerOptions().position(new LatLng(32.2243059, 35.2301697)).title("Etimad Taxi").snippet("Tel: +970 1700 100 200"));
                markers.add( new MarkerOptions().position(new LatLng(32.2264684, 35.2685215)).title("Jawwal Taxi").snippet("Tel: +970 9 234 3880"));
                markers.add(  new MarkerOptions().position(new LatLng(32.2243896, 35.2562793)).title("Western Transport Service"));
                markers.add(  new MarkerOptions().position(new LatLng(32.2271493, 35.2487309)).title("Al-Jazeera Taxi").snippet("Tel: +970 9 233 3702"));

                openMapActivity(city, markers);
            }
        });


    }

    private void openThingtodoActivity() {
        Intent intent = new Intent(this, ThingToDo.class);
        startActivity(intent);
    }

    public void openMapActivity(MarkerOptions center, ArrayList markers){

        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("city", center);
        intent.putExtra("markers", markers);
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
    public void openWeatherActivity(){
        Intent intent = new Intent(this, WeatherActvity.class);
        startActivity(intent);
    }
}
