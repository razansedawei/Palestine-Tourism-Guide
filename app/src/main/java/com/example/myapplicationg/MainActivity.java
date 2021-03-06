package com.example.myapplicationg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    SliderView sliderView;
    private SliderAdapterExample adapter;
    Button goToMapBtn;
    Button goToCityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationBarClickListener(this));


        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        renewItems(null);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        // location code
        goToMapBtn = findViewById(R.id.map_activity_button);
        goToMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapActivity();
            }

        });
        //code go to activity city

        goToCityBtn = findViewById(R.id.b1);
        goToCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCityActivity();
            }
        });
        goToCityBtn=findViewById(R.id.b1);
        goToCityBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog();
                return false;
            }
        });

    }
    public void showDialog(){
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_design);
        Button btn=dialog.findViewById(R.id.dialog_btn);
        btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.hide();
           }
        });
        dialog.show();
    }

    public void openMapActivity(){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openCityActivity(){
        Intent intent = new Intent(this, CityActivity.class);
        startActivity(intent);
    }

    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data

        for (int i = 0; i < 5; i++) {
            sliderItemList.add(new SliderItem());
        }
        sliderItemList.get(0).setImageUrl("https://i.ibb.co/yBNtrYd/1.jpg");
        sliderItemList.get(1).setImageUrl("https://i.ibb.co/TvtGhPq/2.jpg");
        sliderItemList.get(2).setImageUrl("https://i.ibb.co/TB09Pp4/3.jpg");
        sliderItemList.get(3).setImageUrl("https://i.ibb.co/M9hVKTt/4.jpg");
        sliderItemList.get(4).setImageUrl("https://i.ibb.co/GRVHmjL/5.jpg");
        adapter.renewItems(sliderItemList);
    }


}

