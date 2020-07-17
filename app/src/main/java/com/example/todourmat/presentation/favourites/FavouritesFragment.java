package com.example.todourmat.presentation.favourites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.todourmat.R;
import com.example.todourmat.model.BoredAction;
import com.example.todourmat.presentation.main.MainFragment;

import java.util.ArrayList;


public class FavouritesFragment extends Fragment {

private FavAdapter favAdapter;
private ArrayList<BoredAction> list = new ArrayList<>();
BoredAction boredAction;


    public static  Fragment newInstance(){
        return new FavouritesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }
}