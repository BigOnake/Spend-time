package com.example.todourmat.data;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private final String PREF_IS_FIRST_LAUNCH = "is_first_launch";

    private SharedPreferences preferences;

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(
                "bored_app_prefs",
                Context.MODE_PRIVATE
        );
    }

    public void setLaunched() {
        preferences.edit().putBoolean(PREF_IS_FIRST_LAUNCH, false).apply();
    }

    public boolean isFirstLaunch() {
        return preferences.getBoolean(PREF_IS_FIRST_LAUNCH, false);
    }
}
