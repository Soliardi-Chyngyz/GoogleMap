package com.example.googlemap.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.googlemap.data.model.LatLngRepresente;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

@Dao
public interface LatLngDao {

    @Insert
    void insert (LatLngRepresente latLngRepresente);

    @Query("SELECT * FROM LatLngRepresente")
    List<LatLngRepresente> getLatLng();

    @Query("DELETE FROM LatLngRepresente where id = :lngId")
    void deleteById(int lngId);

    @Delete
    void delete(LatLngRepresente latLngRepresente);

}
