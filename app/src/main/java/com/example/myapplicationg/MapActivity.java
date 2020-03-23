package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {


    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    MarkerOptions city;
    ArrayList<MarkerOptions> markers;
    private static final  int REQUEST_CODE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        city = getIntent().getParcelableExtra("city");
        markers =  getIntent().getParcelableArrayListExtra("markers");
        if(city != null) {
            SupportMapFragment supportMapFragment = (SupportMapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.google_map);
            supportMapFragment.getMapAsync(MapActivity.this);
        } else {
            fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
            fetchlastlocation();
        }
    }

    private void fetchlastlocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location>task=fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentlocation = location;
                    Toast.makeText(getApplicationContext(), currentlocation.getLatitude()
                    +""+currentlocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(MapActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(city != null) {

            googleMap.animateCamera(CameraUpdateFactory.newLatLng(city.getPosition()));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(city.getPosition(), 13));
            //Show City marker
            //googleMap.addMarker(city);

            for(MarkerOptions marker: markers){
                googleMap.addMarker(marker);
            }

        } else {
            LatLng latLng = new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I Am Here");
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
            googleMap.addMarker(markerOptions);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchlastlocation();
                }
                break;
        }
    }
}
