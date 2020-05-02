package com.example.myapplicationg;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBarClickListener implements BottomNavigationView.OnNavigationItemSelectedListener {
    Activity context;
    public NavigationBarClickListener(Activity context){
        this.context = context;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profile:
                context.startActivity(new Intent(context.getApplicationContext(), ProfileActivity.class));
                return true;

            case R.id.home:
                context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
                return true;

            case R.id.favorite:
                context.startActivity(new Intent(context.getApplicationContext(), FavoriteList.class));
                return true;

            case R.id.plan:
                context.startActivity(new Intent(context.getApplicationContext(), TripPlanActivity.class));
                return true;

        }
        return false;
    }
}
