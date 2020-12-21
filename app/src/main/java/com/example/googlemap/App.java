package com.example.googlemap;

import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import com.example.googlemap.data.room.AppDataBase;

public class App extends MultiDexApplication {

    public static App instance;
    private AppDataBase appDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appDataBase = Room.databaseBuilder(this, AppDataBase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance(){
        return instance;
    }

    public AppDataBase getAppDataBase(){
        return appDataBase;
    }
}
