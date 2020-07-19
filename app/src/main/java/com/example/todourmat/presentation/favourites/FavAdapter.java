package com.example.todourmat.presentation.favourites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todourmat.App;
import com.example.todourmat.R;
import com.example.todourmat.model.BoredAction;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<ViewHolder> {

    public ArrayList<BoredAction> arrayList;
    OnItemClickListener listener;

    public FavAdapter(ArrayList<BoredAction> list) {
        this.arrayList = list;
        setListener(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout layout;
    ArrayList<BoredAction> list;
    OnItemClickListener listener;
    private boolean is_photo = true;

    private TextView favMainText, favCategory, favPrice;
    private ImageView favParticipants, favAccessibility, favFavourites;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        list = new ArrayList<>();
        list.addAll(App.boredStorage.getAllActions());
        layout = itemView.findViewById(R.id.layout);
        favMainText = itemView.findViewById(R.id.fav_main_text);

        favCategory = itemView.findViewById(R.id.fav_category);
        favPrice = itemView.findViewById(R.id.fav_price);
        favParticipants = itemView.findViewById(R.id.fav_person);
        favAccessibility = itemView.findViewById(R.id.fav_access);
        favCategory = itemView.findViewById(R.id.fav_category);

        favFavourites = itemView.findViewById(R.id.fav_favourite);
    }

    public void onBind(BoredAction boredAction) {
        favMainText.setText(boredAction.getActivity());
        favCategory.setText(boredAction.getType());
        priceFilter(boredAction);
        accessFilter(boredAction);
        participantsFilter(boredAction);
    }

    private void priceFilter(BoredAction boredAction) {
        if (boredAction.getPrice() != null) {
            if (boredAction.getPrice() == 0.0f) {
                favPrice.setText(R.string.lowest_cost_string);
            }
            if (boredAction.getPrice() == 0.1f) {
                favPrice.setText("$");
            }
            if (boredAction.getPrice() >= 0.2f && boredAction.getPrice() <= 0.3f) {
                favPrice.setText("$$");
            }
            if (boredAction.getPrice() >= 0.4f && boredAction.getPrice() <= 0.5f) {
                favPrice.setText("$$$");
            }
        }
    }

    private void accessFilter(BoredAction boredAction) {
       if (boredAction.getAccessibility() != null) {
            if (boredAction.getAccessibility() >= 0.0f && boredAction.getAccessibility() <= 0.3f) {
                favAccessibility.setImageResource(R.drawable.ic_acc_empty);
            }
            if (boredAction.getAccessibility() >= 0.4f && boredAction.getAccessibility() <= 0.7f) {
                favAccessibility.setImageResource(R.drawable.ic_acc_half);
            }
            if (boredAction.getAccessibility() >= 0.8f && boredAction.getAccessibility() <= 1.0f) {
                favAccessibility.setImageResource(R.drawable.ic_acc_full);
            }
        }
    }

    private void participantsFilter(BoredAction boredAction) {
        if (boredAction.getParticipants() != null) {
            if (boredAction.getParticipants() == 1) {
                favParticipants.setImageResource(R.drawable.ic_person);
            } else if (boredAction.getParticipants() == 2) {
                favParticipants.setImageResource(R.drawable.ic_group);
            } else if (boredAction.getParticipants() >= 3) {
                favParticipants.setImageResource(R.drawable.ic_group_add);
            }
        }
    }

    private void favouriteStatus(BoredAction boredAction) {
        if (is_photo){
            favFavourites.setImageResource(R.drawable.ic_hearth_full);
        }else{
            favFavourites.setImageResource(R.drawable.ic_hearth);
            App.boredStorage.deleteBoredAction(boredAction);
        }
    }
}
