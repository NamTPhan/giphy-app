package com.npdevelopment.giphyslash.database;

import com.npdevelopment.giphyslash.models.GiphyWrapper;

import retrofit2.Call;

public class GiphyRepository {

    private GiphyApiService giphyApiService = GiphyApi.create();

//    public Call<GiphyWrapper> getAllGifs() {
//        return giphyApiService.ge(page, year);
//    }
}
