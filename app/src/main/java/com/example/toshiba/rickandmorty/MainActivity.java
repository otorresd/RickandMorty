package com.example.toshiba.rickandmorty;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.toshiba.rickandmorty.Adapter.MainRecyclerAdapter;
import com.example.toshiba.rickandmorty.Database.Character;
import com.example.toshiba.rickandmorty.Database.CharacterDao;
import com.example.toshiba.rickandmorty.Database.Controller;
import com.example.toshiba.rickandmorty.Database.DaoMaster;
import com.example.toshiba.rickandmorty.Database.DaoSession;
import com.example.toshiba.rickandmorty.Database.EpisodeDao;
import com.example.toshiba.rickandmorty.Database.ImageDao;
import com.example.toshiba.rickandmorty.Database.JoinCharacterWithEpisodesDao;
import com.example.toshiba.rickandmorty.Database.LocationDao;
import com.example.toshiba.rickandmorty.Dialog.DialogSyncFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mAdapter;
    private DialogSyncFragment dialogSyncFragment;
    private FragmentManager fragmentManager;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setSubtitleTextAppearance(this, R.style.AppTheme_ActionBar);
        setSupportActionBar(myToolbar);

        fragmentManager = getSupportFragmentManager();
        dialogSyncFragment = new DialogSyncFragment();

        Controller controller = new Controller(this);

        ArrayList<Character> list = controller.getCharacters();

        if(list.size() == 0){
            dialogSyncFragment.show(fragmentManager, "Dialog Fragment");
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainRecyclerAdapter(list);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sync_Pages:
                dialogSyncFragment.show(fragmentManager, "Dialog Fragment");
                return true;
            case R.id.delete_DB:
                prefs = getSharedPreferences("Rick And Morty", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("Succefull", false);
                editor.putInt("cantMax",0);
                editor.commit();
                deletDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deletDB()
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "R-db", null);
        SQLiteDatabase db = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        CharacterDao characterDao = daoSession.getCharacterDao();
        EpisodeDao episodeDao = daoSession.getEpisodeDao();
        LocationDao locationDao = daoSession.getLocationDao();
        JoinCharacterWithEpisodesDao join = daoSession.getJoinCharacterWithEpisodesDao();
        ImageDao image = daoSession.getImageDao();

        characterDao.deleteAll();
        episodeDao.deleteAll();
        locationDao.deleteAll();
        join.deleteAll();
        image.deleteAll();

        updateRV(characterDao.loadAll());
    }

    public void updateRV(List<Character> list){
        ArrayList<Character> list1 = new ArrayList<>(list);
        mAdapter = new MainRecyclerAdapter(list1);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


}
