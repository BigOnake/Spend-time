package com.example.todourmat.presentation.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.todourmat.App;
import com.example.todourmat.R;
import com.example.todourmat.data.AppPreferences;
import com.example.todourmat.model.BoredAction;
import com.example.todourmat.data.BoredApiClient;
import com.example.todourmat.presentation.intro.IntroActivity;


public class MainActivity extends AppCompatActivity {

    TextView mainText, category, price;
    Spinner spinnerType;
    Button nextBtn;
    String valueOfSpinner;
    CrystalRangeSeekbar seekbar1, seekbar2;
    ImageView participants, accessibility;
    Float maxPrice, minPrice, maxAccessibility, minAccessibility;


    public void skipIntroIfShown() {
        AppPreferences appPreferences = new AppPreferences(this);
        boolean isFirstLaunched = appPreferences.isFirstLaunch();
        if (isFirstLaunched) {
            startActivity(new Intent(this, IntroActivity.class));
            appPreferences.setLaunched();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skipIntroIfShown();
        setContentView(R.layout.activity_main);

        mainText = findViewById(R.id.main_text);
        category = findViewById(R.id.category);
        spinnerType = findViewById(R.id.list);
        nextBtn = findViewById(R.id.btn_next_idea);
        seekbar1 = findViewById(R.id.seek_bar_1);
        seekbar2 = findViewById(R.id.seek_bar_2);
        participants = findViewById(R.id.person);
        accessibility = findViewById(R.id.access);
        price = findViewById(R.id.price);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueOfSpinner = spinnerType.getSelectedItem().toString();
                category.setText(valueOfSpinner); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "No item is selected", Toast.LENGTH_SHORT).show(); }
        });

        seekbar1.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            minPrice = minValue.floatValue();
            maxPrice = maxValue.floatValue();
        });

        seekbar2.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            minAccessibility = minValue.floatValue();
            maxAccessibility = maxValue.floatValue();
        });

        Log.d("ololo", "должно");
        question();

        nextBtn.setOnClickListener(v -> {
            Log.d("ololo", "click должно работать");
            question();});
    }

    public void question() {
        App.boredApiClient.getAction(null, null, valueOfSpinner,
                minPrice, maxPrice, minAccessibility, maxAccessibility,
                new BoredApiClient.BoredActionCallback() {
                    @Override
                    public void onSuccess(BoredAction boredAction) {
                        App.boredStorage.saveBoredAction(boredAction);
                        for (BoredAction action : App.boredStorage.getAllActions()) {
                            Log.d("ololo", action.toString());
                        }
                        Log.d("ololo", "Receive " + boredAction.toString());

                        boredAction.setType(spinnerType.getSelectedItem().toString());
                        category.setText(boredAction.getType());
                        mainText.setText(boredAction.getActivity());
                        participantsFilter(boredAction);
                        accessFilter(boredAction);
                        priceFilter(boredAction);
                    }

                    @Override
                    public void onFailure(Exception ex) {}
                });
    }

    public void priceFilter(BoredAction boredAction) {
        if (boredAction.getPrice() != null) {
            if (boredAction.getPrice() >= 0.0f && boredAction.getPrice() <= 0.1f) {
                price.setText("$");
            }
            if (boredAction.getPrice() >= 0.2f && boredAction.getPrice() <= 0.3f) {
                price.setText("$$");
            }
            if (boredAction.getPrice() >= 0.4f && boredAction.getPrice() <= 0.5f) {
                price.setText("$$$");
            }
        }
    }

    public void accessFilter(BoredAction boredAction) {
        if (boredAction.getAccessibility() != null) {
            if (boredAction.getAccessibility() >= 0.0f && boredAction.getAccessibility() <= 0.3f) {
                accessibility.setImageResource(R.drawable.ic_acc_empty);
            }
            if (boredAction.getAccessibility() >= 0.4f && boredAction.getAccessibility() <= 0.7f) {
                accessibility.setImageResource(R.drawable.ic_acc_half);
            }
            if (boredAction.getAccessibility() >= 0.8f && boredAction.getAccessibility() <= 1.0f) {
                accessibility.setImageResource(R.drawable.ic_acc_full);
            }
        }
    }

    public void participantsFilter(BoredAction boredAction) {
        if (boredAction.getParticipants() != null) {
            if (boredAction.getParticipants() == 1) {
                participants.setImageResource(R.drawable.ic_person);
            } else if (boredAction.getParticipants() == 2) {
                participants.setImageResource(R.drawable.ic_group);
            } else if (boredAction.getParticipants() >= 3) {
                participants.setImageResource(R.drawable.ic_group_add);
            }
        }
    }
}