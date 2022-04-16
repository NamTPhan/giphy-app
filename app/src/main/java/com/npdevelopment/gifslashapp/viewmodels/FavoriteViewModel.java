package com.npdevelopment.gifslashapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.npdevelopment.gifslashapp.database.repositories.FavoriteRepository;
import com.npdevelopment.gifslashapp.models.Favorite;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private FavoriteRepository mFavoriteRepository;
    private LiveData<List<Favorite>> mFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        this.mFavoriteRepository = new FavoriteRepository(application.getApplicationContext());
        this.mFavorites = mFavoriteRepository.getFavorites();
    }

    public LiveData<List<Favorite>> getFavorites() {
        return mFavorites;
    }

    public void insert(Favorite favorite) {
        mFavoriteRepository.insert(favorite);
    }

    public void insertAll(List<Favorite> favorites) {
        mFavoriteRepository.insertAll(favorites);
    }

    public void update(Favorite favorite) {
        mFavoriteRepository.update(favorite);
    }

    public void delete(Favorite favorite) {
        mFavoriteRepository.delete(favorite);
    }

    public void deleteAll(List<Favorite> favorites) {
        mFavoriteRepository.deleteAll(favorites);
    }
}
