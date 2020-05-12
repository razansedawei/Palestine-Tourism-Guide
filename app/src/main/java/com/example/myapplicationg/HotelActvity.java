package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelActvity extends AppCompatActivity {
    SliderView sliderView;
    private SliderAdapterExample adapter;
    RatingBar ratingBar;
    TextView tvName1;
    Button mcomment, mRating;
    FirebaseFirestore db;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_actvity);

        tvName1=findViewById(R.id.textnameforHotel);
        tvName1.setText(getIntent().getStringExtra("FirstHotelName"));

        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationBarClickListener(this));

        mcomment=findViewById(R.id.buttonhotel);
        mcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addcomment();
            }
        });

        //rating
        ratingBar = findViewById(R.id.rating_bar);
        mRating=findViewById(R.id.buttonRating1);

        db= FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            userID = user.getUid();
        }

        mRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(), s+"  Star.", Toast.LENGTH_SHORT).show();

                Map<String, Object> userRating = new HashMap<>();
                userRating.put("Rating", s);
                db.collection("HotelRating").document(userID)
                        .set(userRating);
            }
        });


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

    public void Addcomment(){
        Intent comment1 = new Intent(this, CommentActity.class);
        startActivity(comment1);
    }
}