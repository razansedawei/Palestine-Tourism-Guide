package com.example.myapplicationg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HotelActvity extends AppCompatActivity {
    SliderView sliderView;
    private SliderAdapterExample adapter;
    RatingBar ratingBar;
    ImageButton showHotelLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_actvity);
        //rating
        ratingBar = findViewById(R.id.rating_bar);
        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        renewItems(null);

        showHotelLocation = findViewById(R.id.map);
        showHotelLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarkerOptions city = new MarkerOptions().position(new LatLng(32.2264821,35.2685216)).title("Nablus");

                ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
                markers.add(new MarkerOptions().position(new LatLng(32.2262644,35.2100038)).title("Yildiz palace Hotel").snippet("+970 599 653 635"));


                openHotelMapActivity(city, markers);
            }
        });

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 6; i++) {
            sliderItemList.add(new SliderItem());
        }
        sliderItemList.get(0).setImageUrl("https://i.ibb.co/p1rjJH0/1.jpg[/img][/url]");
        sliderItemList.get(1).setImageUrl("https://i.ibb.co/bs0tRvs/2.jpg[/img][/url]");
        sliderItemList.get(2).setImageUrl("https://i.ibb.co/cyG7sjG/3.jpg[/img][/url]");
        sliderItemList.get(3).setImageUrl("https://i.ibb.co/HBf2sZB/4.jpg[/img][/url]");
        sliderItemList.get(4).setImageUrl("https://i.ibb.co/7jK8vWd/5.jpg[/img][/url]");
        sliderItemList.get(5).setImageUrl("https://i.ibb.co/dWsq1sN/6.jpg[/img][/url]");

        adapter.renewItems(sliderItemList);
    }

    public void openHotelMapActivity(MarkerOptions center, ArrayList markers){

        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("city", center);
        intent.putExtra("markers", markers);
        startActivity(intent);
    }


}