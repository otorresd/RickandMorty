package com.example.toshiba.rickandmorty.CallBack;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;

import com.example.toshiba.rickandmorty.Class.CharacterAPI;
import com.example.toshiba.rickandmorty.Database.Controller;
import com.example.toshiba.rickandmorty.Database.Image;
import com.example.toshiba.rickandmorty.Interface.RickAndMortyAPI;
import com.example.toshiba.rickandmorty.R;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
    private List<CharacterAPI> list;
    private int pos;
    Retrofit retrofit;

    RickAndMortyAPI rickAndMortyAPI;

    public ImageCallBack(Context context, List<CharacterAPI> list/*CharacterAPI character*/, Controller controller) {
        this.context = context;
        //this.character = character;
        this.controller = controller;
        this.list = list;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().connectTimeout(1, TimeUnit.MINUTES)
                                        .readTimeout(30, TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).build();
        pos = 0;
        rickAndMortyAPI = retrofit.create(RickAndMortyAPI.class);
    }

    public void start(int pos) {
        if (pos < list.size()) {
            Call<ResponseBody> call = rickAndMortyAPI.downloadImageDynamicUrlSync(list.get(pos).getImage());
            call.enqueue(this);

        }
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
            if (pos < list.size()) {
                try {
                    ResponseBody responseBody = response.body();
                    Bitmap img = BitmapFactory.decodeStream(responseBody.byteStream());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] b = byteArrayOutputStream.toByteArray();
                    img.recycle();

                    Image image = new Image(list.get(pos).getImage(), b);
                    controller.insertCharacter(list.get(pos), image);

                    pos++;
                    start(pos);

                } catch (Exception exc) {
                    NotificationCompat.Builder mBuilder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.mipmap.ic_launcher_round)
                                    .setContentTitle("Error durante la descarga")
                                    .setContentText(list.get(pos).getName())
                                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder.build());
                }
            }
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
