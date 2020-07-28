package com.example.todourmat.presentation.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.todourmat.presentation.main.MainActivity;
import com.example.todourmat.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private DotsIndicator dotsIndicator;
    private Button btnSkip, btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        dotsIndicator = findViewById(R.id.dots_indicator);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new IntroPagerAdapter(getSupportFragmentManager()));
        dotsIndicator.setViewPager(viewPager);

        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        btnSkip.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnNext.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == IntroPagerAdapter.PAGES_COUNT - 1) {
                    btnNext.setVisibility(View.GONE);
                    btnSkip.setText("Start");
                } else {
                    btnSkip.setText("Skip");
                    btnNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }


    public class IntroPagerAdapter extends FragmentPagerAdapter {

        public static final int PAGES_COUNT = 3;

        public IntroPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            IntroFragment.newInstance(position);
            return IntroFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGES_COUNT;
        }
    }

/*
    private void saveIsShown() {
        String PREF_IS_FIRST_LAUNCH = "is_first_launch";
        SharedPreferences preferences = getSharedPreferences(PREF_IS_FIRST_LAUNCH, Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShown", false).apply();
    }*/
}
