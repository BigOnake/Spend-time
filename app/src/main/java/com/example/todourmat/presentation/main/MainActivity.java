package com.example.todourmat.presentation.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
    Float maxPrice, minPrice;

    public void skipIntroIfShown() {
        Boolean isFirstLaunched = new AppPreferences(this).isFirstLaunch();
        if (isFirstLaunched) {
            startActivity(new Intent(this, IntroActivity.class));
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
                maxPrice = (Float) seekbar1.getSelectedMaxValue();
                minPrice = (Float) seekbar1.getSelectedMinValue();
            }
        });

        question();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question();
            }
        });
    }

    public void PriceFilter() {
        if (minPrice >= 0.1f && maxPrice <= 0.3f){
            price.setText("$");
        }
        if (minPrice >= 0.4f && maxPrice <= 0.6f){
            price.setText("$$");
        }
        if (minPrice >= 0.7f && maxPrice <= 0.8f){
            price.setText("$$$");
        }
    }

    public void question(){
        App.boredApiClient.getAction(valueOfSpinner, minPrice,
                maxPrice, null,
                null, null, new BoredApiClient.BoredActionCallback() {
                    @Override
                    public void onSuccess(BoredAction boredAction) {
                        Log.d("ololo", boredAction.toString());
                                /*boredAction.setType(spinnerType.getSelectedItem().toString());
                                category.setText(boredAction.getType());*/
                        mainText.setText(boredAction.getActivity());
                        PriceFilter();
                    }

                    @Override
                    public void onFailure(Exception ex) {
                        Log.d("ololo", ex.getMessage());
                    }
                });
    }
}