package com.example.toshiba.rickandmorty;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.example.toshiba.rickandmorty.CallBack.RickAndMortyCallBack;
import com.example.toshiba.rickandmorty.Class.Download;

public class MainActivity extends AppCompatActivity {

    private Download download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void prueba(){
        RickAndMortyCallBack postsCallBack = new RickAndMortyCallBack(this);
        postsCallBack.start(4);

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
