package com.example.myapplicationg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

public class PlaceActvity extends AppCompatActivity {
    SliderView sliderView;
    private SliderAdapterExample adapter;
    RatingBar ratingBar;
    ImageButton showPlaceLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        //rating
        ratingBar=findViewById(R.id.rating_bar);
        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        renewItems(null);

        showPlaceLocation = findViewById(R.id.map);
        showPlaceLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarkerOptions city = new MarkerOptions().position(new LatLng(32.2264821,35.2685216)).title("Nablus");

                ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
                markers.add(new MarkerOptions().position(new LatLng(32.2237739,35.2520702)).title("Jamal Abdul_Alnasser Park").snippet("Open at: 6AM–10PM"));


                openPlaceMapActivity(city, markers);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            sliderItemList.add(new SliderItem());
        }
        sliderItemList.get(0).setImageUrl("https://i.ibb.co/ggCSWjq/1.jpg");
        sliderItemList.get(1).setImageUrl("https://i.ibb.co/qnvRHS7/2.jpg");
        sliderItemList.get(2).setImageUrl("https://i.ibb.co/9rCc47q/3.jpg");
        sliderItemList.get(3).setImageUrl("https://i.ibb.co/ZzjbYmf/4.jpg");
        sliderItemList.get(4).setImageUrl("https://i.ibb.co/Yk7cxvf/5.jpg");

        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);
    }
    public void openPlaceMapActivity(MarkerOptions center, ArrayList markers){

        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("city", center);
        intent.putExtra("markers", markers);
        startActivity(intent);
    }




}
