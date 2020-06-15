package com.example.todourmat.viewPager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.todourmat.MainActivity;
import com.example.todourmat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment {

    private TextView textTitle;
    private Button button;

    public IntroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final IntroActivity introActivity = (IntroActivity) getActivity();
        textTitle = view.findViewById(R.id.intr_text);
        /*button = view.findViewById(R.id.btn_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(2);
                }else{
                    viewPager.setCurrentItem(3);
                }
            }
        });*/

        int pos = getArguments().getInt("pos");
        switch (pos) {
            case 0:
                textTitle.setText("Page 1");
                //button.setVisibility(View.VISIBLE);
                break;
            case 1:
                textTitle.setText("Page 2");
                //button.setVisibility(View.VISIBLE);
                break;
            case 2:
                textTitle.setText("Page 3");
                //button.setVisibility(View.GONE);
                break;
        }
    }

}
