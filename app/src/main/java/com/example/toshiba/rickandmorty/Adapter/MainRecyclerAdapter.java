package com.example.toshiba.rickandmorty.Adapter;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.rickandmorty.Database.Episode;
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
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        holder.genderTextView.setText(characters.get(position).getGender());
        holder.locationTextView.setText(String.valueOf(characters.get(position).getLocation().getName()));
        holder.nameTextView.setText(characters.get(position).getName());
        holder.originTextView.setText(String.valueOf(characters.get(position).getOrigin().getName()));

        byte[] img = characters.get(position).getImage().getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.statusTextView.setText(characters.get(position).getStatus());

        String type = characters.get(position).getType();
        final String species;
        if(type.equalsIgnoreCase(""))
            species = characters.get(position).getSpecies();
        else
            species = characters.get(position).getSpecies() + ", " + type;

        holder.speciesTextView.setText(species);

        characters.get(position).getEpisode();

        holder.episodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Episode> episodes = new ArrayList<Episode>(characters.get(position).getEpisode());
                Episode firstEpisode = episodes.remove(episodes.size() - 1);
                String striEpisode = "";
                String[] split;
                for (Episode episode : episodes) {
                    split = episode.getUrl().split("/");
                    striEpisode = striEpisode + split[split.length - 1] + ", ";
                }
                split = firstEpisode.getUrl().split("/");

                striEpisode = striEpisode + split[split.length - 1];

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext(), R.style.MyDialog).
                        setIcon(R.mipmap.ic_circle_rick_and_morty).
                        setTitle("Episodes in which "+ characters.get(position).getName() +" appears")
                        .setMessage(striEpisode)
                        .setCancelable(false)
                        .setPositiveButton(R.string.accept_button, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
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
