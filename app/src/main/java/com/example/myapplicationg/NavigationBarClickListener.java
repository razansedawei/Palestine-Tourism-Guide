package com.example.myapplicationg;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import com.example.myapplicationg.R;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBarClickListener implements BottomNavigationView.OnNavigationItemSelectedListener {
    Activity context;
    Class classs;
    public NavigationBarClickListener(Activity context){
        this.context = context;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profile:
                context.startActivity(new Intent(context.getApplicationContext(), ProfileActivity.class));
                return true;

            case R.id.homee:
                context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
                return true;

            case R.id.plan:
                context.startActivity(new Intent(context.getApplicationContext(), Tripplan.class));
                return true;

        }
        return false;
    }
}
