package com.npdevelopment.gifslashapp.database;

import com.npdevelopment.gifslashapp.BuildConfig;
import com.npdevelopment.gifslashapp.models.GiphyResponseList;
import com.npdevelopment.gifslashapp.models.GiphyResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApiService {

    String apiKey = BuildConfig.API_KEY;

    // GIF API Calls
    @GET("/v1/gifs/trending?api_key=" + apiKey)
    Call<GiphyResponseList> getAllTrendingGifs(@Query("limit") int limit, @Query("rating") String rating);

    @GET("/v1/gifs/random?api_key=" + apiKey)
    Call<GiphyResponseObject> getRandomGif(@Query("rating") String rating);

    @GET("/v1/gifs/search?api_key=" + apiKey)
    Call<GiphyResponseList> getSelectedCategoryGif(@Query("q") String searchQuery,
                                                   @Query("limit") int limit,
                                                   @Query("rating") String rating,
                                                   @Query("lang") String language);

    // Stickers API Calls
    @GET("/v1/stickers/trending?api_key=" + apiKey)
    Call<GiphyResponseList> getAllTrendingStickers(@Query("limit") int limit, @Query("rating") String rating);

    @GET("/v1/stickers/random?api_key=" + apiKey)
    Call<GiphyResponseObject> getRandomSticker(@Query("rating") String rating);

    @GET("/v1/stickers/search?api_key=" + apiKey)
    Call<GiphyResponseList> getSelectedCategorySticker(@Query("q") String searchQuery,
                                                       @Query("limit") int limit,
                                                       @Query("rating") String rating,
                                                       @Query("lang") String language);
}
