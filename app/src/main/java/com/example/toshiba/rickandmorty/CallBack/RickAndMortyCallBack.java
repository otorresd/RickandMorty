package com.example.toshiba.rickandmorty.CallBack;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

import com.example.toshiba.rickandmorty.Database.Episode;
import com.example.toshiba.rickandmorty.Database.EpisodeDao;
import com.example.toshiba.rickandmorty.Database.JoinCharacterWithEpisodes;
import com.example.toshiba.rickandmorty.Database.JoinCharacterWithEpisodesDao;
import com.example.toshiba.rickandmorty.Database.Location;
import com.example.toshiba.rickandmorty.Database.Character;
import com.example.toshiba.rickandmorty.Class.Download;
import com.example.toshiba.rickandmorty.Database.CharacterDao;
import com.example.toshiba.rickandmorty.Database.DaoMaster;
import com.example.toshiba.rickandmorty.Database.DaoSession;
import com.example.toshiba.rickandmorty.Database.LocationDao;
import com.example.toshiba.rickandmorty.Database.Origin;
import com.example.toshiba.rickandmorty.Database.OriginDao;
import com.example.toshiba.rickandmorty.Interface.RickAndMortyAPI;
import com.example.toshiba.rickandmorty.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by toshiba on 6/2/2018.
 */

public class RickAndMortyCallBack implements Callback<Download> {

    private String BASE_URL = "https://www.rickandmortyapi.com/";
    private Context context;

    Gson gson = new GsonBuilder().serializeNulls().create();

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

    public Download getDownload() {
        return download;
    }

    private Download download;

    public RickAndMortyCallBack(Context context) {
        this.context = context;
    }

    public void start(){
        RickAndMortyAPI rickAndMortyAPI = retrofit.create(RickAndMortyAPI.class);

        Call<Download> call = rickAndMortyAPI.defaultPages();
        call.enqueue(this);
    }

    public void start(int page){
        RickAndMortyAPI rickAndMortyAPI = retrofit.create(RickAndMortyAPI.class);

        Call<Download> call = rickAndMortyAPI.getPages(page);
        call.enqueue(this);
    }
    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<Download> call, Response<Download> response) {
        if(response.isSuccessful()) {
            download = response.body();
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "rick-and-morty-db", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();

            JoinCharacterWithEpisodesDao joinCharacterWithEpisodesDao = daoSession.getJoinCharacterWithEpisodesDao();
            CharacterDao characterDao = daoSession.getCharacterDao();
            OriginDao originDao = daoSession.getOriginDao();
            LocationDao locationDao = daoSession.getLocationDao();
            EpisodeDao episodeDao = daoSession.getEpisodeDao();

            characterDao.deleteAll();
            originDao.deleteAll();
            locationDao.deleteAll();
            episodeDao.deleteAll();



            Origin origin = new Origin(download.getCharacters().get(0).getOrigin().getName(),
                                       download.getCharacters().get(0).getOrigin().getUrl());
            try
            {
                originDao.insert(origin);
            }
            catch (Exception exc)
            {

            }
            Location location = new Location(download.getCharacters().get(0).getLocation().getName(),
                                             download.getCharacters().get(0).getLocation().getUrl());
            try {
                locationDao.insert(location);
            }
            catch (Exception exc)
            {

            }

            List<Episode> episodes = new ArrayList<>();

            for (String s : download.getCharacters().get(0).getEpisode()) {
                Episode episode = new Episode(s);
                episodes.add(episode);
                try
                {
                    Long episodeId = episodeDao.insert(episode);
                    JoinCharacterWithEpisodes join = new JoinCharacterWithEpisodes(download.getCharacters().get(0).getId(), episodeId);
                    joinCharacterWithEpisodesDao.insert(join);
                }
                catch (Exception exc)
                {

                }
            }

            Character character = new Character(download.getCharacters().get(0).getId(),
                    download.getCharacters().get(0).getName(),download.getCharacters().get(0).getStatus(),
                    download.getCharacters().get(0).getSpecies(),download.getCharacters().get(0).getType(),
                    download.getCharacters().get(0).getGender(), origin,
                    location, download.getCharacters().get(0).getImage(),
                    episodes,download.getCharacters().get(0).getUrl(),
                    download.getCharacters().get(0).getCreated());

            characterDao.insert(character);

            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("Exito")
                            .setContentText(download.getCharacters().get(0).getName())
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
            System.out.println(response.errorBody());
        } else {
            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("Error")
                            .setContentText(response.message())
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
            System.out.println(response.errorBody());
        }
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<Download> call, Throwable t) {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Error durante la descarga")
                        .setContentText(t.getMessage())
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
