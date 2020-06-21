package com.example.todourmat.presentation.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.todourmat.R;
import com.example.todourmat.presentation.intro.IntroActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isShown()) {
            startActivity(new Intent(this, IntroActivity.class));
            finish();
            Log.d("ololo","check");
            return;
        }
        setContentView(R.layout.activity_main);



    }

    private boolean isShown() {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("isShown", false);
    }
}
