package com.example.toshiba.rickandmorty.Dialog;

import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.NumberPicker;

import com.example.toshiba.rickandmorty.CallBack.RickAndMortyCallBack;
import com.example.toshiba.rickandmorty.Database.Controller;
import com.example.toshiba.rickandmorty.R;

public class DialogSyncFragment extends DialogFragment{
    private NumberPicker numberPicker;
    private SharedPreferences prefs;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        prefs = getActivity().getSharedPreferences("Rick And Morty", Context.MODE_PRIVATE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_sync, null);
        numberPicker = (NumberPicker)view.findViewById(R.id.numberPicker2);
        int maxValue = 25;
        if (prefs.getBoolean("Succefull", false)) {
            maxValue = maxValue - prefs.getInt("cantMax", 0);
        }
        numberPicker.setMaxValue(maxValue);

        if (maxValue == 0)
            numberPicker.setMinValue(0);
        else
            numberPicker.setMinValue(1);

        builder.setView(view)
                .setPositiveButton(R.string.accept_button, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        download(numberPicker.getValue());
                        DialogSyncFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DialogSyncFragment.this.getDialog().cancel();
            }
        }).setTitle("Sync pages").setIcon(R.mipmap.ic_circle_rick_and_morty);

        return builder.create();
    }

    public void download(int page){
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        Controller controller = new Controller(getActivity(), recyclerView);
        controller.setCantCharacter(page);
        RickAndMortyCallBack postsCallBack = new RickAndMortyCallBack(getActivity(), controller);

        prefs = getActivity().getSharedPreferences("Rick And Morty", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("Rick And Morty", Context.MODE_PRIVATE);
        int cant = sharedPreferences.getInt("cantMax", 0);
        SharedPreferences.Editor editor = prefs.edit();

        int j = 1;

        if (cant > 0) {
            j = cant + 1;
            page = page + cant;
        }
        editor.putInt("cantMax", page);
        editor.commit();

        for(int i = j; i <= page; i++){
            postsCallBack.start(i);
        }
    }
}
