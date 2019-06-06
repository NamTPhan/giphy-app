package com.npdevelopment.gifslashapp.database.repositories;

import com.npdevelopment.gifslashapp.database.GiphyApi;
import com.npdevelopment.gifslashapp.database.GiphyApiService;
import com.npdevelopment.gifslashapp.models.GiphyResponseList;
import com.npdevelopment.gifslashapp.models.GiphyResponseObject;

import retrofit2.Call;

public class GiphyRepository {

    private GiphyApiService giphyApiService = GiphyApi.create();

    public Call<GiphyResponseList> getAllTrendingGifs(int limit, String rating) {
        return giphyApiService.getAllTrendingGifs(limit, rating);
    }

    public Call<GiphyResponseList> getAllTrendingStickers(int limit, String rating) {
        return giphyApiService.getAllTrendingStickers(limit, rating);
    }

    public Call<GiphyResponseObject> getRandomGif(String rating) {
        return giphyApiService.getRandomGif(rating);
    }

    public Call<GiphyResponseObject> getRandomSticker(String rating) {
        return giphyApiService.getRandomSticker(rating);
    }

    public Call<GiphyResponseList> getSearchGif(String searchQuery, int limit, String rating, String language) {
        return giphyApiService.searchForGifs(searchQuery, limit, rating, language);
    }

    public Call<GiphyResponseList> getSearchSticker(String searchQuery, int limit, String rating, String language) {
        return giphyApiService.searchForStickers(searchQuery, limit, rating, language);
    }
}
