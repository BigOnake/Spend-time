package com.example.todourmat;

import android.app.Application;

import androidx.room.Room;

import com.example.todourmat.data.AppPreferences;
import com.example.todourmat.data.BoredRepository;
import com.example.todourmat.data.remote.BoredApiClient;
import com.example.todourmat.data.db.BoredDataBase;
import com.example.todourmat.data.local.BoredStorage;

public class App extends Application {

    public static AppPreferences appPreferences;

    public static BoredRepository boredRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        BoredDataBase boredDataBase = Room.databaseBuilder(
                this,
                BoredDataBase.class,
                "bored.db"
        ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        appPreferences = new AppPreferences(this);
        BoredStorage boredStorage = new BoredStorage(boredDataBase.boredDao());
        BoredApiClient boredApiClient = new BoredApiClient();
        boredRepository = new BoredRepository(boredStorage, boredApiClient);
    }
}