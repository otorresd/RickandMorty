package com.example.toshiba.rickandmorty.CallBack;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

import com.example.toshiba.rickandmorty.Class.CharacterAPI;
import com.example.toshiba.rickandmorty.Database.Controller;
import com.example.toshiba.rickandmorty.Database.Image;
import com.example.toshiba.rickandmorty.Interface.RickAndMortyAPI;
import com.example.toshiba.rickandmorty.R;

import java.io.ByteArrayOutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by toshiba on 6/6/2018.
 */

public class ImageCallBack implements Callback<ResponseBody> {

    private String BASE_URL = "https://www.rickandmortyapi.com/";
    private Controller controller;
    private CharacterAPI character;
    private Context context;
    Retrofit retrofit;

    RickAndMortyAPI rickAndMortyAPI;

    public ImageCallBack(Context context, CharacterAPI character) {
        this.context = context;
        this.character = character;
        controller = new Controller(context);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        rickAndMortyAPI = retrofit.create(RickAndMortyAPI.class);
    }

    public void start(){
        Call<ResponseBody> call = rickAndMortyAPI.downloadImageDynamicUrlSync(character.getImage());
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
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            Bitmap img = BitmapFactory.decodeStream(responseBody.byteStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] b = byteArrayOutputStream.toByteArray();
            img.recycle();

            if (img == null)
            {
                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.ic_launcher_round)
                                .setContentTitle("Byte null")
                                .setContentText("null")
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
            }
            Image image = new Image(character.getImage(), b);
            controller.insertCharacter(character, image);
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
    public void onFailure(Call<ResponseBody> call, Throwable t) {

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
