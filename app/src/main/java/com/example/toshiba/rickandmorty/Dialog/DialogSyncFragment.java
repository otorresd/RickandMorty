package com.example.toshiba.rickandmorty.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.toshiba.rickandmorty.CallBack.RickAndMortyCallBack;
import com.example.toshiba.rickandmorty.Database.Controller;
import com.example.toshiba.rickandmorty.R;

public class DialogSyncFragment extends DialogFragment{
    private NumberPicker numberPicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_sync, null);
        numberPicker = (NumberPicker)view.findViewById(R.id.numberPicker2);
        numberPicker.setMaxValue(25);
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
        Controller controller = new Controller(getActivity());
        controller.setCantCharacter(page);
        RickAndMortyCallBack postsCallBack = new RickAndMortyCallBack(getActivity(), controller);
        for(int i = 1; i <= page; i++){
            postsCallBack.start(i);
        }
    }
}
