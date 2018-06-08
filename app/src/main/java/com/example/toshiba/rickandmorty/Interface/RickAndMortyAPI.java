package com.example.toshiba.rickandmorty.Interface;
/**
 * Created by toshiba on 6/2/2018.
 */
import com.example.toshiba.rickandmorty.Class.Download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RickAndMortyAPI {

    @GET("api/character")
    Call<Download> defaultPages();

    @GET("api/character")
    Call<Download> getPages(@Query("page") Integer page);

    @GET
    Call<ResponseBody> downloadImageDynamicUrlSync(@Url String fileUrl);
}
