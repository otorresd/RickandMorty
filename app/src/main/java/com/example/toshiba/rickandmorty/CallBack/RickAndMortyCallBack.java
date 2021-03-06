package com.example.toshiba.rickandmorty.CallBack;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

import com.example.toshiba.rickandmorty.Class.CharacterAPI;
import com.example.toshiba.rickandmorty.Class.Download;
import com.example.toshiba.rickandmorty.Database.Controller;
import com.example.toshiba.rickandmorty.Interface.RickAndMortyAPI;
import com.example.toshiba.rickandmorty.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
    private Controller controller;

    Gson gson = new GsonBuilder().serializeNulls().create();

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder().connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS).build();

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                            .client(okHttpClient)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

    RickAndMortyAPI rickAndMortyAPI = retrofit.create(RickAndMortyAPI.class);

    public Download getDownload() {
        return download;
    }

    private Download download;

    public RickAndMortyCallBack(Context context, Controller controller) {
        this.context = context;
        this.controller = controller;
    }

    public void start(){
        Call<Download> call = rickAndMortyAPI.defaultPages();
        call.enqueue(this);
    }

    public void start(int page){
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
            //for (CharacterAPI characterAPI : download.getCharacters()) {
                ImageCallBack imageCallBack = new ImageCallBack(context, download.getCharacters(), controller);
                imageCallBack.start(0);
            //}
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
                        .setSmallIcon(R.mipmap.ic_circle_rick_and_morty)
                        .setContentTitle("Error durante la descarga")
                        .setContentText(t.getMessage())
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

        SharedPreferences prefs = context.getSharedPreferences("Rick And Morty", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Succefull", false);
        editor.putInt("cantMax", prefs.getInt("lastCount", 0));
        editor.commit();
    }
}
