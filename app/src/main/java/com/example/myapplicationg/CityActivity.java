package com.example.myapplicationg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.myapplicationg.R.id.bb6;

public class CityActivity extends AppCompatActivity {
    Button goToMapBtnl;
    Button goToRestBtnl;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
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
    }
    public void openMapActivity(){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openRestActivity(){
        Intent intent = new Intent(this, ResturantActivity.class);
        startActivity(intent);
    }
}
