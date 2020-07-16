package com.example.todourmat;

import android.app.Application;

import androidx.room.Room;

import com.example.todourmat.data.AppPreferences;
import com.example.todourmat.data.remote.BoredApiClient;
import com.example.todourmat.data.db.BoredDataBase;
import com.example.todourmat.data.local.BoredStorage;

public class App extends Application {

    public static AppPreferences appPreferences;
    public static BoredApiClient boredApiClient;
    public static BoredDataBase boredDataBase;
    public static BoredStorage boredStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        boredDataBase = Room.databaseBuilder(
                this,
                BoredDataBase.class,
                "bored.db"
        ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        boredStorage = new BoredStorage(boredDataBase.boredDao());
        appPreferences = new AppPreferences(this);
        boredApiClient = new BoredApiClient();
    }
}
