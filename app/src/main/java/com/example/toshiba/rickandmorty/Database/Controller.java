package com.example.toshiba.rickandmorty.Database;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

import com.example.toshiba.rickandmorty.Class.CharacterAPI;
import com.example.toshiba.rickandmorty.Database.DaoMaster.DevOpenHelper;
import com.example.toshiba.rickandmorty.R;

import java.util.ArrayList;

/**
 * Created by toshiba on 6/4/2018.
 */

public class Controller {

   private DevOpenHelper devOpenHelper;
   private SQLiteDatabase db;
   private DaoMaster daoMaster;
   private DaoSession daoSession;
    private Context context;

    public Controller(Context context) {
        this.context = context;
        devOpenHelper = new DaoMaster.DevOpenHelper(context, "RAM-db", null);
        db = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insertCharacter(CharacterAPI characterAPI, Image image){
        Location location = new Location(characterAPI.getLocation().getName(), characterAPI.getLocation().getUrl());
        Long locationId = insertOrGetLocation(location);

        Location origin = new Location(characterAPI.getOrigin().getName(), characterAPI.getOrigin().getUrl());
        Long originId = insertOrGetLocation(origin);

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Insertar Imagen")
                        .setContentText("marca")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

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
        Image oldImage;
        ImageDao imageDao = daoSession.getImageDao();

        try{
            oldImage = imageDao.queryBuilder().where(ImageDao.Properties.Url.eq(image.getUrl())).unique();
            id = oldImage.getId();
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

}
