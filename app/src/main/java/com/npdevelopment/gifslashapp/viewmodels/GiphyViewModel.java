package com.npdevelopment.gifslashapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

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

    private MutableLiveData<String> mError = new MutableLiveData<>();

    private MutableLiveData<List<Giphy>> mTrendingGifs = new MutableLiveData<>();
    private MutableLiveData<List<Giphy>> mTrendingStickers = new MutableLiveData<>();
    private MutableLiveData<List<Giphy>> mSearchListGifs = new MutableLiveData<>();
    private MutableLiveData<List<Giphy>> mSearchListStickers = new MutableLiveData<>();

    private MutableLiveData<Giphy> mRandomGif = new MutableLiveData<>();
    private MutableLiveData<Giphy> mRandomSticker = new MutableLiveData<>();

    public GiphyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getError() {
        return mError;
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

    public MutableLiveData<List<Giphy>> getAllSearchedGifs() {
        return mSearchListGifs;
    }

    public MutableLiveData<List<Giphy>> getAllSearchedStickers() {
        return mSearchListStickers;
    }

    public void getTrendingGiphyGifs(int limit, String rating) {
        mGiphyRepository.getAllTrendingGifs(limit, rating).enqueue(new Callback<GiphyResponseList>() {
            @Override
            public void onResponse(Call<GiphyResponseList> call, Response<GiphyResponseList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mTrendingGifs.setValue(response.body().getData());
                } else {
                    mError.setValue("Retrieving data failed!");
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseList> call, Throwable throwable) {
                mError.setValue("Retrieving data failed!");
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
                    mError.setValue("Retrieving data failed!");
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseList> call, Throwable throwable) {
                mError.setValue("Retrieving data failed!");
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
                    mError.setValue("Retrieving data failed!");
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseObject> call, Throwable throwable) {
                mError.setValue("Retrieving data failed!");
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
                    mError.setValue("Retrieving data failed!");
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseObject> call, Throwable throwable) {
                mError.setValue("Retrieving data failed!");
            }
        });
    }

    public void getGifsBasedOnSearchQuery(String searchQuery, int limit, String rating, String language) {
        mGiphyRepository.getSearchGif(searchQuery, limit, rating, language).enqueue(new Callback<GiphyResponseList>() {
            @Override
            public void onResponse(Call<GiphyResponseList> call, Response<GiphyResponseList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mSearchListGifs.setValue(response.body().getData());
                } else {
                    mError.setValue("Retrieving data failed!");
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseList> call, Throwable throwable) {
                mError.setValue("Retrieving data failed!");
            }
        });
    }

    public void getStickersBasedOnSearchQuery(String searchQuery, int limit, String rating, String language) {
        mGiphyRepository.getSearchSticker(searchQuery, limit, rating, language).enqueue(new Callback<GiphyResponseList>() {
            @Override
            public void onResponse(Call<GiphyResponseList> call, Response<GiphyResponseList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mSearchListStickers.setValue(response.body().getData());
                } else {
                    mError.setValue("Retrieving data failed!");
                }
            }

            @Override
            public void onFailure(Call<GiphyResponseList> call, Throwable throwable) {
                mError.setValue("Retrieving data failed!");
            }
        });
    }
}
