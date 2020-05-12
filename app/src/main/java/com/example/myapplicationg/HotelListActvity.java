package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HotelListActvity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    SliderView sliderView;
    private SliderAdapterExample adapter;
    Button HotelBtn ;
    Button  goToHotelLocation;
    String firstHotelName = "Villa sama nablus";

    ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
    BounceInterpolator bounceInterpolator = new BounceInterpolator();
    FirebaseFirestore db;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list_actvity);

        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationBarClickListener(this));

        db= FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            userID = user.getUid();
        }

        scaleAnimation.setDuration(500);
        scaleAnimation.setInterpolator(bounceInterpolator);



        final ToggleButton[] favoriteButtons = {
                findViewById(R.id.button_favorite_b1),
                findViewById(R.id.button_favorite_b2),
                findViewById(R.id.button_favorite_b3),
                findViewById(R.id.button_favorite_b4),
                findViewById(R.id.button_favorite_b5),
                findViewById(R.id.button_favorite_b6)
        };

        final CompoundButton.OnCheckedChangeListener self = this;

        db.collection("favoriteList").document(userID).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            ArrayList<String> list = (ArrayList<String>)document.get("hotels");
                            for(ToggleButton button : favoriteButtons){
                                if(list.contains(button.getTextOn().toString())){
                                    button.setChecked(true);
                                }
                            }

                            for(ToggleButton button : favoriteButtons){
                                button.setOnCheckedChangeListener(self);
                            }
                        }
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


       HotelBtn = findViewById(R.id.b2);
        HotelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHotelActivity();
            }

        });

        goToHotelLocation = findViewById(R.id.location2);
        goToHotelLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MarkerOptions city = new MarkerOptions().position(new LatLng(32.2264821,35.2685216)).title("Nablus");

                ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
                markers.add(new MarkerOptions().position(new LatLng(32.2339747,35.2557228)).title("Villa Sama Nablus"));
                markers.add( new MarkerOptions().position(new LatLng(32.2271977,35.2151301)).title("Yildis Palace Hotel"));
                markers.add(  new MarkerOptions().position(new LatLng(32.2271977,35.2151301)).title("Golden Tree Hotel"));
                markers.add(  new MarkerOptions().position(new LatLng(32.2199442,35.2447801)).title("Golden rose Hotel"));
                markers.add(  new MarkerOptions().position(new LatLng(32.2208025,35.2672881)).title("Saleam Effendi Hotel"));


                openMapActivity(city, markers);
            }
        });

    }
    public void openMapActivity(MarkerOptions center, ArrayList markers){

        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("city", center);
        intent.putExtra("markers", markers);
        startActivity(intent);
    }

    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 6; i++) {
            sliderItemList.add(new SliderItem());
        }
        sliderItemList.get(0).setImageUrl("https://i.ibb.co/LkCCcFm/1.jpg");
        sliderItemList.get(1).setImageUrl("https://i.ibb.co/28t31fK/2.jpg");
        sliderItemList.get(2).setImageUrl("https://i.ibb.co/B6hjNwB/3.jpg");
        sliderItemList.get(3).setImageUrl("https://i.ibb.co/2v9X7wz/4.jpg");
        sliderItemList.get(4).setImageUrl("https://i.ibb.co/q0WsLjC/5.jpg");
        sliderItemList.get(5).setImageUrl("https://i.ibb.co/N74Cy2q/6.jpg");
        adapter.renewItems(sliderItemList);
    }

    public void openHotelActivity(){
        Intent intent = new Intent(this, HotelActvity.class);
        intent.putExtra("FirstHotelName",firstHotelName);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        buttonView.startAnimation(scaleAnimation);
        final String hotel = ((ToggleButton) buttonView).getTextOn().toString();
        db.collection("favoriteList").document(userID).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            ArrayList<String> list = (ArrayList<String>)document.get("hotels");
                            if(isChecked) {
                                if(!list.contains(hotel)) {
                                    list.add(hotel);
                                }
                            } else {
                                list.remove(list.indexOf(hotel));
                            }
                            db.collection("favoriteList").document(userID).update("hotels", list);
                        }
                    }
                });

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
