package com.example.todourmat.presentation.favourites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todourmat.R;
import com.example.todourmat.model.BoredAction;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<ViewHolder> {

    public ArrayList<BoredAction> arrayList;
    private OnItemClickListener onItemClickListener;

    public FavAdapter(ArrayList<BoredAction> list) {
        this.arrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_view_holder,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    BoredAction boredAction;
    LinearLayout linearLayout;
    ArrayList<FavCard> list;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        list = new ArrayList<>();
        textView = itemView.findViewById(R.id.text_title);
        linearLayout = itemView.findViewById(R.id.layout);
    }

    public void onBind(BoredAction boredAction) {
        this.boredAction = boredAction;
    }
}
