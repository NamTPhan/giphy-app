package com.npdevelopment.gifslashapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.npdevelopment.gifslashapp.database.repositories.GiphyRepository;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.models.GiphyResponseList;
import com.npdevelopment.gifslashapp.models.GiphyResponseObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiphyViewModel extends AndroidViewModel {

    private GiphyRepository mGiphyRepository = new GiphyRepository();

    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<Giphy>> mTrendingGifs = new MutableLiveData<>();
    private MutableLiveData<List<Giphy>> mTrendingStickers = new MutableLiveData<>();
    private MutableLiveData<Giphy> mRandomGif = new MutableLiveData<>();
    private MutableLiveData<Giphy> mRandomSticker = new MutableLiveData<>();

    public GiphyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<List<Giphy>> getAllTrendingGifs() {
        return mTrendingGifs;
    }

    public MutableLiveData<List<Giphy>> getAllTrendingStickers() {
        return mTrendingStickers;
    }

    public MutableLiveData<Giphy> getOneRandomGif() {
        return mRandomGif;
    }

    public MutableLiveData<Giphy> getOneRandomSticker() {
        return mRandomSticker;
    }

    public void getTrendingGiphyGifs(int limit, String rating) {
        mGiphyRepository.getAllTrendingGifs(limit, rating).enqueue(new Callback<GiphyResponseList>() {
            @Override
            public void onResponse(Call<GiphyResponseList> call, Response<GiphyResponseList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mTrendingGifs.setValue(response.body().getData());
                } else {
                    error.setValue(response.toString());
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseList> call, Throwable throwable) {
                error.setValue(throwable.toString());
            }
        });
    }

    public void getTrendingGiphyStickers(int limit, String rating) {
        mGiphyRepository.getAllTrendingStickers(limit, rating).enqueue(new Callback<GiphyResponseList>() {
            @Override
            public void onResponse(Call<GiphyResponseList> call, Response<GiphyResponseList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mTrendingStickers.setValue(response.body().getData());
                } else {
                    error.setValue(response.toString());
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseList> call, Throwable throwable) {
                error.setValue(throwable.toString());
            }
        });
    }

    public void getRandomGif(String rating) {
        mGiphyRepository.getRandomGif(rating).enqueue(new Callback<GiphyResponseObject>() {
            @Override
            public void onResponse(Call<GiphyResponseObject> call, Response<GiphyResponseObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mRandomGif.setValue(response.body().getData());
                } else {
                    error.setValue(response.toString());
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseObject> call, Throwable throwable) {
                error.setValue(throwable.toString());
            }
        });
    }

    public void getRandomSticker(String rating) {
        mGiphyRepository.getRandomSticker(rating).enqueue(new Callback<GiphyResponseObject>() {
            @Override
            public void onResponse(Call<GiphyResponseObject> call, Response<GiphyResponseObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mRandomSticker.setValue(response.body().getData());
                } else {
                    error.setValue(response.toString());
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseObject> call, Throwable throwable) {
                error.setValue(throwable.toString());
            }
        });
    }
}
