package com.npdevelopment.gifslashapp.database;

import com.npdevelopment.gifslashapp.BuildConfig;
import com.npdevelopment.gifslashapp.models.GiphyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApiService {

    String apiKey = BuildConfig.API_KEY;

    @GET("/v1/gifs/trending?api_key=" + apiKey)
    Call<GiphyResponse> getAllTrendingGifs(@Query("limit") int limit, @Query("rating") String rating);
}
