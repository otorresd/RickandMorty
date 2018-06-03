package com.example.toshiba.rickandmorty.Interface;
/**
 * Created by toshiba on 6/2/2018.
 */
import com.example.toshiba.rickandmorty.Class.Download;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RickAndMortyAPI {

    @GET("api/character")
    Call<Download> defaultPages();

    @GET("api/character")
    Call<Download> getPages(@Query("page") Integer page);
}
