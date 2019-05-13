package com.npdevelopment.gifslashapp.database.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.npdevelopment.gifslashapp.database.GiphyRoomDatabase;
import com.npdevelopment.gifslashapp.database.dao.SettingsDao;
import com.npdevelopment.gifslashapp.models.Settings;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SettingsRepository {

    private GiphyRoomDatabase mGiphyRoomDatabase;
    private SettingsDao mSettingsDao;
    private LiveData<List<Settings>> mSettings;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public SettingsRepository(Context context) {
        mGiphyRoomDatabase = GiphyRoomDatabase.getDatabase(context);
        mSettingsDao = mGiphyRoomDatabase.settingsDao();
        mSettings = mSettingsDao.getSettings();
    }

    public LiveData<List<Settings>> getSettings() {
        return mSettings;
    }

    public void insert(final Settings settings) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mSettingsDao.insert(settings);
            }
        });
    }

    public void update(final Settings settings) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mSettingsDao.update(settings);
            }
        });
    }
}
