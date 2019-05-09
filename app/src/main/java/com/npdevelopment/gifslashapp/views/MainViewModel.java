package com.npdevelopment.gifslashapp.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.npdevelopment.gifslashapp.database.GiphyRepository;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.models.GiphyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private GiphyRepository mGiphyRepository = new GiphyRepository();

    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<Giphy>> mTrendingGifs = new MutableLiveData<>();
    private MutableLiveData<List<Giphy>> mTrendingStickers = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<List<Giphy>> getAllTrendingGifs() {
        return mTrendingGifs;

    }public MutableLiveData<List<Giphy>> getAllTrendingStickers() {
        return mTrendingStickers;
    }

    public void getTrendingGiphyGifs(int limit, String rating) {
        mGiphyRepository.getAllTrendingGifs(limit, rating).enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(Call<GiphyResponse> call, Response<GiphyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mTrendingGifs.setValue(response.body().getData());
                } else {
                    error.setValue("Api Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GiphyResponse> call, Throwable t) {
                error.setValue("Api Error: " + t.getMessage());
            }
        });
    }

    public void getTrendingGiphyStickers(int limit, String rating) {
        mGiphyRepository.getAllTrendingStickers(limit, rating).enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(Call<GiphyResponse> call, Response<GiphyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mTrendingStickers.setValue(response.body().getData());
                } else {
                    error.setValue("Api Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GiphyResponse> call, Throwable t) {
                error.setValue("Api Error: " + t.getMessage());
            }
        });
    }
}
