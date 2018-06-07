package com.example.toshiba.rickandmorty.Database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.toshiba.rickandmorty.Class.CharacterAPI;
import com.example.toshiba.rickandmorty.Database.DaoMaster.DevOpenHelper;
import com.example.toshiba.rickandmorty.Interface.RickAndMortyAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

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

    /*public void insertCharacters(List<CharacterAPI> charactersList){

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
                Long episodeId = insertOrGetEpisode(episode);
                JoinCharacterWithEpisodes joinCharacterWithEpisodes =
                        new JoinCharacterWithEpisodes(characterAPI.getId(), episodeId);
                daoSession.insert(joinCharacterWithEpisodes);
            }

        }
    }*/

    public void insertCharacter(CharacterAPI characterAPI, Image image){
        Location location = new Location(characterAPI.getLocation().getName(), characterAPI.getLocation().getUrl());
        Long locationId = insertOrGetLocation(location);

        Location origin = new Location(characterAPI.getOrigin().getName(), characterAPI.getOrigin().getUrl());
        Long originId = insertOrGetLocation(origin);

        Long imageId = insertOrGetImage(image);

        Character character = new Character(characterAPI.getId(),characterAPI.getName(),
                characterAPI.getStatus(),characterAPI.getSpecies(),characterAPI.getType(),
                characterAPI.getGender(), originId, locationId, imageId,
                characterAPI.getUrl(), characterAPI.getCreated());
        daoSession.insert(character);

        for (String episodeAPI : characterAPI.getEpisode()) {
            Episode episode = new Episode(episodeAPI);
            Long episodeId = insertOrGetEpisode(episode);
            JoinCharacterWithEpisodes joinCharacterWithEpisodes =
                    new JoinCharacterWithEpisodes(characterAPI.getId(), episodeId);
            daoSession.insert(joinCharacterWithEpisodes);
        }
    }

    public Long insertOrGetLocation(Location location)
    {
        Long id;
        Location oldLocation;
        LocationDao locationDao = daoSession.getLocationDao();

        try{
            oldLocation = locationDao.queryBuilder().where(LocationDao.Properties.Url.eq(location.getUrl())).unique();
            id = oldLocation.getId();
        }catch(Exception exc)
        {
            id = daoSession.insert(location);
        }

        return id;
    }

    public Long insertOrGetEpisode(Episode episode)
    {
        Long id;
        Episode oldEpisode;
        EpisodeDao episodeDao = daoSession.getEpisodeDao();

        try{
            oldEpisode = episodeDao.queryBuilder().where(EpisodeDao.Properties.Url.eq(episode.getUrl())).unique();
            id = oldEpisode.getEpisodeId();
        }catch(Exception exc)
        {
            id = daoSession.insert(episode);
        }

        return id;
    }

    public Long insertOrGetImage(Image image)
    {
        Long id;
        Image oldLocation;
        ImageDao imageDao = daoSession.getImageDao();

        try{
            oldLocation = imageDao.queryBuilder().where(ImageDao.Properties.Url.eq(image.getUrl())).unique();
            id = oldLocation.getId();
        }catch(Exception exc)
        {
            id = daoSession.insert(image);
        }

        return id;
    }

    public void closeDB(){
        devOpenHelper.close();
    }

    public ArrayList<Character> getCharacters() {
        ArrayList<Character> characters = new ArrayList<>(daoSession.getCharacterDao().loadAll());
        return characters;
    }

    public byte[] getImage(String url)
    {
        byte[] image = new byte[10];
        Retrofit retrofit = new Retrofit.Builder().build();

        RickAndMortyAPI downloadService = retrofit.create(RickAndMortyAPI.class);

        Call<ResponseBody> call = downloadService.downloadImageDynamicUrlSync(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");
                    try {
                        response.body().bytes();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });

        return image;
    }
}
