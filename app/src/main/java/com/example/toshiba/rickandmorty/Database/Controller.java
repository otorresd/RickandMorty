package com.example.toshiba.rickandmorty.Database;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;

import com.example.toshiba.rickandmorty.Adapter.MainRecyclerAdapter;
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
   private ProgressDialog progressDialog;
   private RecyclerView recyclerView;
   private SharedPreferences prefs;

    public void setCantCharacter(int cantCharacter) {
        if (cantCharacter == 25)
            this.cantCharacter = 493;
        else
            this.cantCharacter = cantCharacter * 20;
    }

    private int cantCharacter;

    public Controller(Context context) {
        this.context = context;
        devOpenHelper = new DaoMaster.DevOpenHelper(context, "R-db", null);
        db = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        prefs = context.getSharedPreferences("Rick And Morty", Context.MODE_PRIVATE);
    }

    public Controller(Context context, RecyclerView recyclerView) {
        this.context = context;
        devOpenHelper = new DaoMaster.DevOpenHelper(context, "R-db", null);
        db = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        this.recyclerView = recyclerView;
        prefs = context.getSharedPreferences("Rick And Morty", Context.MODE_PRIVATE);
    }

    public void insertCharacter(CharacterAPI characterAPI, Image image){

        if(progressDialog == null)
            showProgress();

        Location location = new Location(characterAPI.getLocation().getName(), characterAPI.getLocation().getUrl());
        Long locationId = insertOrGetLocation(location);

        Location origin = new Location(characterAPI.getOrigin().getName(), characterAPI.getOrigin().getUrl());
        Long originId = insertOrGetLocation(origin);

        Long imageId = insertOrGetImage(image);

        Character character = new Character(characterAPI.getId(),characterAPI.getName(),
                characterAPI.getStatus(),characterAPI.getSpecies(),characterAPI.getType(),
                characterAPI.getGender(), originId, locationId, imageId,
                characterAPI.getUrl(), characterAPI.getCreated());
        try {
            daoSession.insert(character);
        }
        catch (Exception exc)
        {
            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_circle_rick_and_morty)
                            .setContentTitle("Error en guardar")
                            .setContentText("Ya existe un personaje con ese id")
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }
        for (String episodeAPI : characterAPI.getEpisode()) {
            Episode episode = new Episode(episodeAPI);
            Long episodeId = insertOrGetEpisode(episode);
            JoinCharacterWithEpisodes joinCharacterWithEpisodes =
                    new JoinCharacterWithEpisodes(characterAPI.getId(), episodeId);
            try{
                daoSession.insert(joinCharacterWithEpisodes);
            }
            catch(Exception ex)
            {
                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.ic_circle_rick_and_morty)
                                .setContentTitle("Error en guardar")
                                .setContentText("En el join")
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
            }

        }

        progressDialog.incrementProgressBy(1);
        if(progressDialog.getProgress() == progressDialog.getMax()) {
            progressDialog.dismiss();
            MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(new ArrayList<>(daoSession.getCharacterDao().loadAll()));
            recyclerView.setAdapter(mainRecyclerAdapter);
            mainRecyclerAdapter.notifyDataSetChanged();
            SharedPreferences prefs = context.getSharedPreferences("Rick And Morty", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("Succefull", true);
            editor.putInt("lastCount", prefs.getInt("cantMax", 0));
            editor.commit();
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

    public void showProgress(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Saving characters");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(cantCharacter);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

}
