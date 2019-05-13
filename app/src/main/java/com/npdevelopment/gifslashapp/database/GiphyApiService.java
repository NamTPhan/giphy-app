package com.npdevelopment.gifslashapp.database;

import com.npdevelopment.gifslashapp.BuildConfig;
import com.npdevelopment.gifslashapp.models.GiphyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApiService {

    String apiKey = BuildConfig.API_KEY;

    // GIF API Calls
    @GET("/v1/gifs/trending?api_key=" + apiKey)
    Call<GiphyResponse> getAllTrendingGifs(@Query("limit") int limit, @Query("rating") String rating);

    @GET("/v1/gifs/random?api_key=" + apiKey)
    Call<GiphyResponse> getRandomGif(@Query("rating") String rating);

    @GET("/v1/gifs/search?api_key=" + apiKey)
    Call<GiphyResponse> getSelectedCategoryGif(@Query("q") String searchQuery,
                                               @Query("limit") int limit,
                                               @Query("rating") String rating,
                                               @Query("lang") String language);

    // Stickers API Calls
    @GET("/v1/stickers/trending?api_key=" + apiKey)
    Call<GiphyResponse> getAllTrendingStickers(@Query("limit") int limit, @Query("rating") String rating);

    @GET("/v1/stickers/search?api_key=" + apiKey)
    Call<GiphyResponse> getSelectedCategorySticker(@Query("q") String searchQuery,
                                               @Query("limit") int limit,
                                               @Query("rating") String rating,
                                               @Query("lang") String language);
}
