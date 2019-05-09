package com.npdevelopment.gifslashapp.database;

import com.npdevelopment.gifslashapp.models.GiphyResponse;

import retrofit2.Call;

public class GiphyRepository {

    private GiphyApiService giphyApiService = GiphyApi.create();

    public Call<GiphyResponse> getAllTrendingGifs(int limit, String rating) {
        return giphyApiService.getAllTrendingGifs(limit, rating);
    }
}
