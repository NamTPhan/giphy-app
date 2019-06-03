package com.npdevelopment.gifslashapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.npdevelopment.gifslashapp.database.repositories.HistoryRespository;
import com.npdevelopment.gifslashapp.models.History;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private HistoryRespository mHistoryRespository;
    private LiveData<List<History>> mHistory;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        this.mHistoryRespository = new HistoryRespository(application.getApplicationContext());
        this.mHistory = mHistoryRespository.getHistory();
    }

    public LiveData<List<History>> getHistory() {
        return mHistory;
    }

    public void insert(History historyGifSticker) {
        mHistoryRespository.insert(historyGifSticker);
    }

    public void insertAll(List<History> historyList) {
        mHistoryRespository.insertAll(historyList);
    }

    public void update(History historyGifSticker) {
        mHistoryRespository.update(historyGifSticker);
    }

    public void delete(History historyGifSticker) {
        mHistoryRespository.delete(historyGifSticker);
    }

    public void deleteAll(List<History> historyList) {
        mHistoryRespository.deleteAll(historyList);
    }
}
