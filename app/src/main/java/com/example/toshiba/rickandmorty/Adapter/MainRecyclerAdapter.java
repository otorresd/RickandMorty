package com.example.toshiba.rickandmorty.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.rickandmorty.R;
import com.example.toshiba.rickandmorty.Database.Character;

import java.util.ArrayList;

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

        byte[] img = characters.get(position).getImage().getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        holder.imageView.setImageBitmap(bitmap);
        //holder.statusTextView.setText(characters.get(position).getStatus());
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
