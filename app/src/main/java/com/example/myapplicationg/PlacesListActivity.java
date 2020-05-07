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

public class PlacesListActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    SliderView sliderView;
    private SliderAdapterExample adapter;
    Button toPlace;
    Button goToPlaceLocation;
    String firstPlaceName = "Jamal Abdul Nasser Park";
    ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
    BounceInterpolator bounceInterpolator = new BounceInterpolator();
    FirebaseFirestore db;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        db= FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            userID = user.getUid();
        }

        scaleAnimation.setDuration(500);
        scaleAnimation.setInterpolator(bounceInterpolator);

        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationBarClickListener(this));

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
                            ArrayList<String> list = (ArrayList<String>)document.get("places");
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


        toPlace = findViewById(R.id.place1);
        toPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openplacesActivity();
            }

        });

        goToPlaceLocation = findViewById(R.id.location);
        goToPlaceLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MarkerOptions city = new MarkerOptions().position(new LatLng(32.2264821,35.2685216)).title("Nablus");

                ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
                markers.add(new MarkerOptions().position(new LatLng(32.2248015,35.255863)).title("Jamal Abde_Alnasser Park").snippet("Open at:6 am"));
                markers.add( new MarkerOptions().position(new LatLng(32.2339747,35.2557228)).title("Sama Nablus"));
                markers.add(  new MarkerOptions().position(new LatLng(32.2566961,35.3333462)).title("Butterflies"));
                markers.add(  new MarkerOptions().position(new LatLng(32.2782402,35.2073878)).title("Sebastia"));
                markers.add(  new MarkerOptions().position(new LatLng(32.2270557,35.224414)).title("Al-Najah National University"));


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
        sliderItemList.get(0).setImageUrl("https://i.ibb.co/fS02YZB/1.jpg");
        sliderItemList.get(1).setImageUrl("https://i.ibb.co/n0ntmpG/2.jpg");
        sliderItemList.get(2).setImageUrl("https://i.ibb.co/F4gj5sj/3.jpg");
        sliderItemList.get(3).setImageUrl("https://i.ibb.co/NCjRgZ8/4.jpg");
        sliderItemList.get(4).setImageUrl("https://i.ibb.co/YQLWhYq/5.gif");
        sliderItemList.get(5).setImageUrl("https://i.ibb.co/B25vvTx/6.jpg");
        adapter.renewItems(sliderItemList);
    }

    public void openplacesActivity(){
        Intent intent = new Intent(this, PlaceActvity.class);
        intent.putExtra("FirstPlaceName",firstPlaceName);
        startActivity(intent);
    }


    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        buttonView.startAnimation(scaleAnimation);
        final String place = ((ToggleButton) buttonView).getTextOn().toString();
        db.collection("favoriteList").document(userID).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            ArrayList<String> list = (ArrayList<String>)document.get("places");
                            if(isChecked) {
                                if(!list.contains(place)) {
                                    list.add(place);
                                }
                            } else {
                                list.remove(list.indexOf(place));
                            }
                            db.collection("favoriteList").document(userID).update("places", list);
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
