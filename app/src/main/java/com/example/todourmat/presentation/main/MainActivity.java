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
    ImageView participants;
    Float maxPrice, minPrice, maxAcc, minAcc;
    ProgressBar accessibility;


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
        price = findViewById(R.id.price);
        accessibility = findViewById(R.id.access);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueOfSpinner = spinnerType.getSelectedItem().toString();
                category.setText(valueOfSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "No item is selected", Toast.LENGTH_SHORT).show();
            }
        });

        seekbar1.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minPrice = minValue.floatValue();
                maxPrice = maxValue.floatValue();
            }
        });

        seekbar2.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minAcc = minValue.floatValue();
                maxAcc = maxValue.floatValue();
            }
        });

        Log.d("ololo", "должно");

        question();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ololo", "click должно работать");
                question();
            }
        });
    }

    public void question() {
        App.boredApiClient.getAction(null, null, valueOfSpinner,
                null, null, null, null,
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
                        //priceFilter(boredAction);
                        //accessFilter(boredAction);
                    }

                    @Override
                    public void onFailure(Exception ex) {
                    }
                });
    }

    public void priceFilter(BoredAction boredAction) {
        if (boredAction.getPrice() != null) {
            if (minPrice >= 0.0f && maxPrice <= 0.3f) {
                price.setText("$");
            } else if (minPrice >= 0.4f && maxPrice <= 0.6f) {
                price.setText("$$");
            } else if (minPrice >= 0.7f && maxPrice <= 0.8f) {
                price.setText("$$$");
            }
        }
    }

    public void accessFilter(BoredAction boredAction) {
        if (boredAction.getAccessibility() != null) {
            if (minAcc >= 0.0f && maxAcc <= 0.4f) {
                accessibility.drawableHotspotChanged(0.0f, 0.4f);
            } else if (minAcc >= 0.5f && maxAcc <= 0.9f) {
                accessibility.drawableHotspotChanged(0.5f, 0.9f);
            } else if (minAcc >= 1.0f && maxAcc <= 1.5f) {
                accessibility.drawableHotspotChanged(1.0f, 1.5f);
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