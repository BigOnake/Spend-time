package com.example.todourmat.viewPager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.todourmat.MainActivity;
import com.example.todourmat.R;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btn_skip, btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        btn_skip = findViewById(R.id.btn_skip);
        btn_next = findViewById(R.id.btn_next);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (viewPager.getCurrentItem() == 2) {
                            btn_next.setVisibility(View.GONE);
                            btn_skip.setText("Start");
                        }else {
                            btn_skip.setText("skip");
                            btn_next.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        viewPager.setCurrentItem(1);
                        break;
                    case 1:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("pos", position);
            IntroFragment fragment = new IntroFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
