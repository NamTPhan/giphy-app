package com.npdevelopment.gifslashapp.database.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.npdevelopment.gifslashapp.database.GiphyRoomDatabase;
import com.npdevelopment.gifslashapp.database.dao.HistoryDao;
import com.npdevelopment.gifslashapp.models.History;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HistoryRepository {

    private GiphyRoomDatabase mGiphyRoomDatabase;
    private HistoryDao mHistoryDao;
    private LiveData<List<History>> mHistory;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public HistoryRepository(Context context) {
        mGiphyRoomDatabase = GiphyRoomDatabase.getDatabase(context);
        mHistoryDao = mGiphyRoomDatabase.historyDao();
        mHistory = mHistoryDao.getAllHistory();
    }

    public LiveData<List<History>> getHistory() {
        return mHistory;
    }

    public void insert(final History historyGifSticker) {
        mExecutor.execute(() -> mHistoryDao.insert(historyGifSticker));
    }

    public void insertAll(final List<History> historyList) {
        mExecutor.execute(() -> mHistoryDao.insert(historyList));
    }

    public void update(final History historyGifSticker) {
        mExecutor.execute(() -> mHistoryDao.update(historyGifSticker));
    }

    public void delete(final History historyGifSticker) {
        mExecutor.execute(() -> mHistoryDao.delete(historyGifSticker));
    }

    public void deleteAll(final List<History> historyList) {
        mExecutor.execute(() -> mHistoryDao.delete(historyList));
    }
}
