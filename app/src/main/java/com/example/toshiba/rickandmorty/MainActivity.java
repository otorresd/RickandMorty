package com.example.toshiba.rickandmorty;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.example.toshiba.rickandmorty.CallBack.RickAndMortyCallBack;
import com.example.toshiba.rickandmorty.Database.CharacterDao;
import com.example.toshiba.rickandmorty.Database.DaoMaster;
import com.example.toshiba.rickandmorty.Database.DaoSession;
import com.example.toshiba.rickandmorty.Database.Episode;
import com.example.toshiba.rickandmorty.Database.EpisodeDao;
import com.example.toshiba.rickandmorty.Database.JoinCharacterWithEpisodes;
import com.example.toshiba.rickandmorty.Database.JoinCharacterWithEpisodesDao;
import com.example.toshiba.rickandmorty.Database.Location;
import com.example.toshiba.rickandmorty.Database.LocationDao;
import com.example.toshiba.rickandmorty.Database.Character;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "RickAndMorty-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        JoinCharacterWithEpisodesDao joinCharacterWithEpisodesDao = daoSession.getJoinCharacterWithEpisodesDao();
        CharacterDao characterDao = daoSession.getCharacterDao();
        LocationDao locationDao = daoSession.getLocationDao();
        EpisodeDao episodeDao = daoSession.getEpisodeDao();


        characterDao.deleteAll();
        locationDao.deleteAll();
        episodeDao.deleteAll();
        joinCharacterWithEpisodesDao.deleteAll();*/
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "RickAndMorty-db", null);
        SQLiteDatabase db = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        CharacterDao characterDao = daoSession.getCharacterDao();
        EpisodeDao episodeDao = daoSession.getEpisodeDao();
        LocationDao locationDao = daoSession.getLocationDao();
        JoinCharacterWithEpisodesDao join = daoSession.getJoinCharacterWithEpisodesDao();

       /* List<JoinCharacterWithEpisodes> listj = join.loadAll();
        JoinCharacterWithEpisodes jj = listj.get(0);
        jj.getEpisodeId();*/

        List<Location> listee = locationDao.loadAll();
        Location ee = listee.get(0);
        ee.getId();

       /* List<Episode> list = episodeDao.loadAll();
        Episode mm = list.get(0);
        mm.getEpisodeId();*/

        List<Character> characters = characterDao.loadAll();
        Character character = characters.get(0);
        character.getEpisode();
        prueba();
    }

    public void prueba(){
        RickAndMortyCallBack postsCallBack = new RickAndMortyCallBack(this);
        postsCallBack.start(1);

        if(postsCallBack.getDownload() != null) {
            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("Personaje")
                            .setContentText(postsCallBack.getDownload().getCharacters().get(0).getName())
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }
    }
}
