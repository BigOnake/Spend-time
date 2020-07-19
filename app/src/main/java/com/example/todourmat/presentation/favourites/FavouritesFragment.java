package com.example.todourmat.presentation.favourites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todourmat.App;
import com.example.todourmat.R;
import com.example.todourmat.model.BoredAction;

import java.util.ArrayList;


public class FavouritesFragment extends Fragment {

    private FavAdapter favAdapter;
    private ArrayList<BoredAction> list = new ArrayList<>();


    public static Fragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.fav_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        favAdapter = new FavAdapter(list);
        recyclerView.setAdapter(favAdapter);
        loadData();
        //favAdapter.setListener(cardID -> { });
    }

    @Override
    public void onPause() {
        super.onPause();
        loadData();
    }

    private void loadData() {
        //App.boredStorage.getAllActions();
        list.clear();
        list.addAll(App.boredStorage.getAllActions());
        favAdapter.notifyDataSetChanged();
    }


}