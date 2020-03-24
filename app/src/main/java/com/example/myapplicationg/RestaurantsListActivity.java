package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    SliderView sliderView;
    private SliderAdapterExample adapter;
    Button goToRestBtn;
    ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
    BounceInterpolator bounceInterpolator = new BounceInterpolator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);

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


        scaleAnimation.setDuration(500);
        scaleAnimation.setInterpolator(bounceInterpolator);


        ToggleButton[] favoriteButtons = {
                findViewById(R.id.button_favorite_b1),
                findViewById(R.id.button_favorite_b2),
                findViewById(R.id.button_favorite_b3),
                findViewById(R.id.button_favorite_b4),
                findViewById(R.id.button_favorite_b5),
                findViewById(R.id.button_favorite_b6)
        };
        for(ToggleButton button : favoriteButtons){
            button.setOnCheckedChangeListener(this);
        }


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


        goToRestBtn = findViewById(R.id.b1);
        goToRestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openResturentActivity();
            }

        });

    }
    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            sliderItemList.add(new SliderItem());
        }
        sliderItemList.get(0).setImageUrl("https://i.ibb.co/BjLKwkW/1.jpg");
        sliderItemList.get(1).setImageUrl("https://i.ibb.co/0c2jfsS/2.jpg");
        sliderItemList.get(2).setImageUrl("https://i.ibb.co/26jqGYx/3.jpg");
        sliderItemList.get(3).setImageUrl("https://i.ibb.co/y6m8zQM/4.jpg");
        sliderItemList.get(4).setImageUrl("https://i.ibb.co/C2jdzfy/5.jpg");
        adapter.renewItems(sliderItemList);
    }

    public void openResturentActivity(){
        Intent intent = new Intent(this, RestaurantActivity.class);
        startActivity(intent);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        buttonView.startAnimation(scaleAnimation);
        switch(buttonView.getId()){
            case R.id.button_favorite_b1:
                break;
            case R.id.button_favorite_b2:
                break;
            case R.id.button_favorite_b3:
                break;
            case R.id.button_favorite_b4:
                break;
            case R.id.button_favorite_b5:
                break;
            case R.id.button_favorite_b6:
                break;
            default:
        }
    }
}
