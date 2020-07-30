package com.example.todourmat.presentation.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.todourmat.App;
import com.example.todourmat.R;
import com.example.todourmat.data.remote.BoredApiClient;
import com.example.todourmat.model.BoredAction;
import com.like.LikeButton;
import com.like.OnLikeListener;

public class MainFragment extends Fragment {

    private TextView mainText, category, price, link;
    private Spinner spinnerType;
    private String valueOfSpinner = "random", nullType = null, mLink;
    private CrystalRangeSeekbar seekbar1, seekbar2;
    private ImageView participants, accessibility;
    private LikeButton favourite;
    private Float maxPrice, minPrice, maxAccessibility, minAccessibility;
    private boolean is_photo = true;
    BoredAction mBoredAction;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mainText = v.findViewById(R.id.main_text);
        category = v.findViewById(R.id.category);
        link = v.findViewById(R.id.link);
        spinnerType = v.findViewById(R.id.list);
        Button nextBtn = v.findViewById(R.id.btn_next_idea);
        seekbar1 = v.findViewById(R.id.seek_bar_1);
        seekbar2 = v.findViewById(R.id.seek_bar_2);
        participants = v.findViewById(R.id.person);
        accessibility = v.findViewById(R.id.access);
        favourite = v.findViewById(R.id.favourite);
        price = v.findViewById(R.id.price);

        question(false);

        favourite.setOnClickListener(v12 -> favouriteStatus());
        nextBtn.setOnClickListener(v1 -> {
            Log.d("ololo", "click должно работать");
            question(false);
        });
        link.setOnClickListener(v13 -> goToURI());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void question(Boolean fromCache) {
            App.boredRepository.getAction(fromCache,null, null, null, nullType,
                    minPrice, maxPrice, minAccessibility, maxAccessibility, new BoredApiClient.BoredActionCallback() {
                        @Override
                        public void onSuccess(BoredAction boredAction) {
                            renderAction(boredAction);
                            for (BoredAction action : App.boredRepository.getAllActions()) {
                                Log.d("ololo", action.toString());
                            }
                            Log.i("ololo", "Receive " + boredAction.toString());

                            categoryFilter(boredAction);
                            activityFilter(boredAction);
                            participantsFilter(boredAction);
                            accessFilter(boredAction);
                            priceFilter(boredAction);
                            linkFilter(boredAction);
                            mBoredAction = boredAction;
                        }

                        @Override
                        public void onFailure(Exception ex) {
                        }
                    });
    }

    private void activityFilter(BoredAction boredAction) {
        if (boredAction.getActivity() == null) {
            mainText.setText(R.string.activity_null_string);
        } else {
            mainText.setText(boredAction.getActivity());
        }
    }

    private void categoryFilter(BoredAction boredAction) {
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueOfSpinner = spinnerType.getSelectedItem().toString();
                category.setText(valueOfSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "No item is selected", Toast.LENGTH_SHORT).show();
            }
        });

        if (spinnerType.getSelectedItem().toString().equals("random")) {
            boredAction.setType(nullType);
            nullType = null;
        } else {
            boredAction.setType(valueOfSpinner);
            nullType = boredAction.getType();
        }
        nullType = boredAction.getType();
        category.setText(valueOfSpinner);
    }

    private void priceFilter(BoredAction boredAction) {
        seekbar1.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            minPrice = minValue.floatValue();
            maxPrice = maxValue.floatValue();
        });

        if (boredAction.getPrice() != null) {
            if (boredAction.getPrice() == 0.0f) {
                price.setText(R.string.lowest_cost_string);
            }
            if (boredAction.getPrice() == 0.1f) {
                price.setText("$");
            }
            if (boredAction.getPrice() >= 0.2f && boredAction.getPrice() <= 0.5f) {
                price.setText("$$");
            }
            if (boredAction.getPrice() >= 0.6f && boredAction.getPrice() <= 0.8f) {
                price.setText("$$$");
            }
        }
    }

    private void accessFilter(BoredAction boredAction) {
        seekbar2.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            minAccessibility = minValue.floatValue();
            maxAccessibility = maxValue.floatValue();
        });

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

    private void participantsFilter(BoredAction boredAction) {
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

    private void linkFilter(BoredAction boredAction) {
        if (boredAction.getLink() != null) {
            if (boredAction.getLink().equals("")) {
                link.setVisibility(View.GONE);
            } else {
                link.setVisibility(View.VISIBLE);
                mLink = boredAction.getLink();
            }
        }
    }

    public void goToURI() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(mLink));
        startActivity(intent);
    }

    private void favouriteStatus() {
        if (is_photo) {
            favourite.setLiked(true);
            App.boredRepository.saveBoredAction(mBoredAction);
        } else {
            favourite.setLiked(false);
            App.boredRepository.deleteBoredAction(mBoredAction);
        }
        is_photo = !is_photo;
    }

    private void renderAction(BoredAction boredAction){
        Boolean isSaved = App.boredRepository.getBoredAction(boredAction.getKey()) != null;
       if (isSaved){
           favourite.setLiked(true);
       }else{
           favourite.setLiked(false);
       }
    }

    @Override
    public void onResume() {
        super.onResume();
        question(true);
    }
}