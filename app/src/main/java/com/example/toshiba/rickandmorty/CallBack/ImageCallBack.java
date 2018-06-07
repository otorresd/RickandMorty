package com.example.toshiba.rickandmorty.CallBack;

import android.content.Context;

import com.example.toshiba.rickandmorty.Class.CharacterAPI;
import com.example.toshiba.rickandmorty.Class.Download;
import com.example.toshiba.rickandmorty.Database.Controller;
import com.example.toshiba.rickandmorty.Database.Image;
import com.example.toshiba.rickandmorty.Interface.RickAndMortyAPI;
import com.example.toshiba.rickandmorty.Database.Character;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by toshiba on 6/6/2018.
 */

public class ImageCallBack implements Callback<ResponseBody> {

    private Controller controller;
    private CharacterAPI character;
    private Context context;

    Retrofit retrofit = new Retrofit.Builder().build();

    RickAndMortyAPI rickAndMortyAPI = retrofit.create(RickAndMortyAPI.class);

    public ImageCallBack(Context context, CharacterAPI character) {
        this.context = context;
        this.character = character;
        controller = new Controller(context);
    }

    public void start(){
        Call<ResponseBody> call = rickAndMortyAPI.downloadImageDynamicUrlSync(character.getUrl());
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

            try {
                byte[] img = responseBody.bytes();
                Image image = new Image(character.getUrl(), img);
                controller.insertCharacter(character, image);
            } catch (IOException e) {
                e.printStackTrace();
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

    }
}
