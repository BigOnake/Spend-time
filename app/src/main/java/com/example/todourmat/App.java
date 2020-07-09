package com.example.todourmat;

import android.app.Application;

import com.example.todourmat.data.AppPreferences;
import com.example.todourmat.data.BoredApiClient;

public class App extends Application {

    public static AppPreferences appPreferences;
    public static BoredApiClient boredApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        appPreferences = new AppPreferences(this);
        boredApiClient = new BoredApiClient();
    }
}
