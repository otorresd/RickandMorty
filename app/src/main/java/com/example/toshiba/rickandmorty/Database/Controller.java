package com.example.toshiba.rickandmorty.Database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.toshiba.rickandmorty.Class.CharacterAPI;
import com.example.toshiba.rickandmorty.Database.DaoMaster.DevOpenHelper;

import java.util.List;

/**
 * Created by toshiba on 6/4/2018.
 */

public class Controller {

   private DevOpenHelper devOpenHelper;
   private SQLiteDatabase db;
   private DaoMaster daoMaster;
   private DaoSession daoSession;

    public Controller(Context context) {
        devOpenHelper = new DaoMaster.DevOpenHelper(context, "RickAndMorty-db", null);
        db = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insertCharacters(List<CharacterAPI> charactersList){

        for (CharacterAPI characterAPI : charactersList) {

            Location location = new Location(characterAPI.getLocation().getName(), characterAPI.getLocation().getUrl());
            Long locationId = insertOrGetLocation(location);

            Location origin = new Location(characterAPI.getOrigin().getName(), characterAPI.getOrigin().getUrl());
            Long originId = insertOrGetLocation(origin);

            Character character = new Character(characterAPI.getId(),characterAPI.getName(),
                    characterAPI.getStatus(),characterAPI.getSpecies(),characterAPI.getType(),
                    characterAPI.getGender(), originId, locationId, characterAPI.getImage(),
                    characterAPI.getUrl(), characterAPI.getCreated());
            daoSession.insert(character);

            for (String episodeAPI : characterAPI.getEpisode()) {
                Episode episode = new Episode(episodeAPI);
                Long episodeId = daoSession.insert(episode);
                JoinCharacterWithEpisodes joinCharacterWithEpisodes =
                        new JoinCharacterWithEpisodes(characterAPI.getId(), episodeId);
                daoSession.insert(joinCharacterWithEpisodes);
            }
        }
    }

    public Long insertOrGetLocation(Location location)
    {
        Long id;
        LocationDao locationDao = daoSession.getLocationDao();

        try{
            location = locationDao.queryBuilder().where(LocationDao.Properties.Url.eq(location.getUrl())).unique();
            id = location.getId();
        }catch(Exception exc)
        {
            id = daoSession.insert(location);
        }

        return id;
    }

    public void closeDB(){
        devOpenHelper.close();
    }
}
