package com.example.todourmat.presentation.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;

import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.todourmat.R;
import com.example.todourmat.data.AppPreferences;

import com.example.todourmat.presentation.favourites.FavouritesFragment;
import com.example.todourmat.presentation.intro.IntroActivity;
import com.example.todourmat.presentation.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private MainPagerAdapter mainPagerAdapter;
    private ImageView changeTheme;
    private SwitchCompat switchCompat;
    private SharedPreferences sharedPreferences = null;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skipIntroIfShown();
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_main);

        changeTheme = findViewById(R.id.color_theme_btn);
        switchCompat = findViewById(R.id.switchCompat);
        sharedPreferences = getSharedPreferences("night", 0);
        Boolean themeValue = sharedPreferences.getBoolean("night_mode", true);
        if (themeValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchCompat.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", true);
                    editor.commit();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switchCompat.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", false);
                    editor.commit();
                }
            }
        });

        viewPager = findViewById(R.id.main_view_pager);
        bottomNavigationView = findViewById(R.id.main_bottom_navigation);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_main:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.nav_favourites:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.nav_settings:
                    viewPager.setCurrentItem(2);
                    //changeTheme.setVisibility(View.VISIBLE);
                    break;
            }
            return false;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == 2) {
                    changeTheme.setVisibility(View.VISIBLE);
                    switchCompat.setVisibility(View.VISIBLE);
                } else {
                    changeTheme.setVisibility(View.GONE);
                    switchCompat.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        }

        public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = MainFragment.newInstance();
                    break;
                case 1:
                    fragment = FavouritesFragment.newInstance();
                    break;
                default:
                    fragment = SettingsFragment.newInstance();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}