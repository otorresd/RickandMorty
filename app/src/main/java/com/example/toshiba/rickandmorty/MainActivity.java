package com.example.toshiba.rickandmorty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.toshiba.rickandmorty.Adapter.MainRecyclerAdapter;
import com.example.toshiba.rickandmorty.Database.Character;
import com.example.toshiba.rickandmorty.Database.Controller;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Controller controller = new Controller(this);
        ArrayList<Character> list = controller.getCharacters();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainRecyclerAdapter(list);
        mRecyclerView.setAdapter(mAdapter);

    }

}
