package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FavoriteList extends AppCompatActivity {



    FirebaseFirestore db;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        db= FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            userID = user.getUid();
        }

        db.collection("favoriteList").document(userID).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            ArrayList<String> places = (ArrayList<String>) document.get("places");
                            if(places.size() > 0) {
                                ArrayAdapter adapter = new ArrayAdapter<String>(FavoriteList.this, R.layout.list_view, places);
                                ListView listView = (ListView) findViewById(R.id.places);
                                listView.setAdapter(adapter);
                            } else {
                                findViewById(R.id.places_area).setVisibility(View.GONE);
                            }

                            ArrayList<String> restaurants = (ArrayList<String>) document.get("restaurants");
                            if(restaurants.size() > 0) {
                                ArrayAdapter adapter1 = new ArrayAdapter<String>(FavoriteList.this, R.layout.list_view, restaurants);
                                ListView listView1 = (ListView) findViewById(R.id.restaurants);
                                listView1.setAdapter(adapter1);
                            } else {
                                findViewById(R.id.restaurants_area).setVisibility(View.GONE);
                            }

                            ArrayList<String> hotels = (ArrayList<String>) document.get("hotels");
                            if(hotels.size() > 0) {
                                ArrayAdapter adapter2 = new ArrayAdapter<String>(FavoriteList.this, R.layout.list_view, hotels);
                                ListView listView2 = (ListView) findViewById(R.id.hotels);
                                listView2.setAdapter(adapter2);
                            } else {
                                findViewById(R.id.hotels_area).setVisibility(View.GONE);
                            }

                        }
                    }
                }
        );









        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.favorite);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationBarClickListener(this));





                }
}
