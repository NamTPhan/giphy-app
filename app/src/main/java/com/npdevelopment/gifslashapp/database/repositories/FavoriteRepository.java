package com.npdevelopment.gifslashapp.database.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.npdevelopment.gifslashapp.database.dao.FavoriteDao;
import com.npdevelopment.gifslashapp.database.GiphyRoomDatabase;
import com.npdevelopment.gifslashapp.models.Favorite;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoriteRepository {

    private GiphyRoomDatabase mGiphyRoomDatabase;
    private FavoriteDao mFavoriteDao;
    private LiveData<List<Favorite>> mFavorites;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public FavoriteRepository(Context context) {
        mGiphyRoomDatabase = GiphyRoomDatabase.getDatabase(context);
        mFavoriteDao = mGiphyRoomDatabase.favoriteDao();
        mFavorites = mFavoriteDao.getAllFavorites();
    }

    public LiveData<List<Favorite>> getFavorites() {
        return mFavorites;
    }

    public void insert(final Favorite favorite) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mFavoriteDao.insert(favorite);
            }
        });
    }

    public void insertAll(final List<Favorite> favorites) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mFavoriteDao.insert(favorites);
            }
        });
    }

    public void update(final Favorite favorite) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mFavoriteDao.update(favorite);
            }
        });
    }

    public void delete(final Favorite favorite) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mFavoriteDao.delete(favorite);
            }
        });
    }

    public void deleteAll(final List<Favorite> favorites) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mFavoriteDao.delete(favorites);
            }
        });
    }
}
