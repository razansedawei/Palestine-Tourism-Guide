package com.example.myapplicationg;

import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class MapExtraData implements Serializable {

    MarkerOptions[] markers;
    MarkerOptions center;

    MapExtraData(MarkerOptions[] markers, MarkerOptions center){
        this.markers = markers;
        this.center = center;
    }

    public MarkerOptions[] getMarkers() {
        return markers;
    }

    public void setMarkers(MarkerOptions[] markers) {
        this.markers = markers;
    }

    public MarkerOptions getCenter() {
        return center;
    }

    public void setCenter(MarkerOptions center) {
        this.center = center;
    }
}
