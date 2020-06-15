package com.example.todourmat.presentation.intro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.todourmat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment {

    private TextView textTitle;
    public static final String ARG_POSITION = "position";

    public static Fragment newInstance(int position){
        Bundle bundle = new Bundle();  // bundle - это аргумент
        bundle.putInt(IntroFragment.ARG_POSITION, position);
        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

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

        int pos = getArguments().getInt(ARG_POSITION);
        switch (pos) {
            case 0:
                textTitle.setText("Page 1");
                break;
            case 1:
                textTitle.setText("Page 2");
                break;
            case 2:
                textTitle.setText("Page 3");
                break;
        }
    }

}
