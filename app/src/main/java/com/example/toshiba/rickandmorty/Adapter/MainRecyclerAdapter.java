package com.example.toshiba.rickandmorty.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toshiba.rickandmorty.R;
import com.example.toshiba.rickandmorty.Database.Character;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toshiba on 6/5/2018.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainViewHolder>{

    private ArrayList<Character> characters;

    public MainRecyclerAdapter(ArrayList<Character> characters) {
        this.characters = characters;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        MainViewHolder viewHolder = new MainViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.genderTextView.setText(characters.get(position).getGender());
        holder.locationTextView.setText(String.valueOf(characters.get(position).getLocationId()));
        holder.nameTextView.setText(characters.get(position).getName());
        holder.originTextView.setText(String.valueOf(characters.get(position).getOriginId()));
        holder.statusTextView.setText(characters.get(position).getStatus());
        holder.speciesTextView.setText(characters.get(position).getSpecies());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return characters.size();
    }
}
