package com.npdevelopment.gifslashapp.database.repositories;

import com.npdevelopment.gifslashapp.database.GiphyApi;
import com.npdevelopment.gifslashapp.database.GiphyApiService;
import com.npdevelopment.gifslashapp.models.GiphyResponse;

import retrofit2.Call;

public class GiphyRepository {

    private GiphyApiService giphyApiService = GiphyApi.create();

    public Call<GiphyResponse> getAllTrendingGifs(int limit, String rating) {
        return giphyApiService.getAllTrendingGifs(limit, rating);
    }

    public Call<GiphyResponse> getAllTrendingStickers(int limit, String rating) {
        return giphyApiService.getAllTrendingStickers(limit, rating);
    }
}
