package com.example.googlemap.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.googlemap.data.model.LatLngRepresente;
import com.google.android.gms.maps.model.LatLng;

@Database(entities = {LatLngRepresente.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract LatLngDao latLngDao();
}

