package com.npdevelopment.gifslashapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.npdevelopment.gifslashapp.database.repositories.HistoryRepository;
import com.npdevelopment.gifslashapp.models.History;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private HistoryRepository mHistoryRepository;
    private LiveData<List<History>> mHistory;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        this.mHistoryRepository = new HistoryRepository(application.getApplicationContext());
        this.mHistory = mHistoryRepository.getHistory();
    }

    public LiveData<List<History>> getHistory() {
        return mHistory;
    }

    public void insert(History historyGifSticker) {
        mHistoryRepository.insert(historyGifSticker);
    }

    public void insertAll(List<History> historyList) {
        mHistoryRepository.insertAll(historyList);
    }

    public void update(History historyGifSticker) {
        mHistoryRepository.update(historyGifSticker);
    }

    public void delete(History historyGifSticker) {
        mHistoryRepository.delete(historyGifSticker);
    }

    public void deleteAll(List<History> historyList) {
        mHistoryRepository.deleteAll(historyList);
    }
}
