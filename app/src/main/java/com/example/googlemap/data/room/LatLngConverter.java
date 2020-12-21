package com.example.googlemap.data.room;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LatLngConverter {

    @TypeConverter
    public static String toRaw(@Nullable LatLng latLngs) {
        if (latLngs == null) return null;

        Gson gson = new Gson();
        Type type = new TypeToken<LatLng>(){}.getType();

        return gson.toJson(latLngs, type);
    }

    @TypeConverter
    public static LatLng fromRaw(@Nullable String raw){
        if (raw == null) return null;

        Gson gson = new Gson();
        Type type = new TypeToken<LatLng>(){}.getType();

        return gson.fromJson(raw, type);
    }
}
