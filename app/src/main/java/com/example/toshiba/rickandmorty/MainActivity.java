package com.example.toshiba.rickandmorty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.toshiba.rickandmorty.CallBack.RickAndMortyCallBack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RickAndMortyCallBack postsCallBack = new RickAndMortyCallBack(this);
        postsCallBack.start();
    }
}
