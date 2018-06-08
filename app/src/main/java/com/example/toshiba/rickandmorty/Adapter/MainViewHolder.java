package com.example.toshiba.rickandmorty.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.toshiba.rickandmorty.R;

/**
 * Created by toshiba on 6/5/2018.
 */

public class MainViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView nameTextView;
    public TextView statusTextView;
    public TextView speciesTextView;
    public TextView genderTextView;
    public TextView originTextView;
    public TextView locationTextView;
    public TextView episodeTextView;

    public MainViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.characterImageView);
        nameTextView = (TextView)itemView.findViewById(R.id.nameTextView);
        statusTextView = (TextView)itemView.findViewById(R.id.rstatusTextView);
        speciesTextView = (TextView)itemView.findViewById(R.id.rspeciesTextView);
        genderTextView = (TextView)itemView.findViewById(R.id.rgenderTextView);
        originTextView = (TextView)itemView.findViewById(R.id.roriginTextView);
        locationTextView = (TextView)itemView.findViewById(R.id.rlocationTextView);
        episodeTextView = (TextView)itemView.findViewById(R.id.repisodeTextView);
    }
}
