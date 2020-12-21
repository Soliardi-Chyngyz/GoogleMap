package com.example.googlemap.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.googlemap.data.room.LatLngConverter;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

@Entity
public class LatLngRepresente implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters({LatLngConverter.class})
    private LatLng latLng;

    public LatLngRepresente(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
