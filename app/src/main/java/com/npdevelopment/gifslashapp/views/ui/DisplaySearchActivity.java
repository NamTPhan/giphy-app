package com.npdevelopment.gifslashapp.views.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.models.SearchData;
import com.npdevelopment.gifslashapp.viewmodels.GiphyViewModel;
import com.npdevelopment.gifslashapp.views.adapters.SearchAdapter;

import java.util.List;

public class DisplaySearchActivity extends AppCompatActivity {

    private final int ITEMS_EACH_ROW = 3;

    private ImageView poweredByGiphy;
    private RecyclerView mRecyclerView;

    private GiphyViewModel mGiphyViewModel;
    private SearchAdapter mSearchAdapter;
    private MainActivity mMainActivity;
    private SearchData mSearchData;

    private List<Giphy> mGifsStickerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);

        poweredByGiphy = findViewById(R.id.powereByGiphy);
        mRecyclerView = findViewById(R.id.rv_trending_gifs);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                mMainActivity.calculateNumberOfColumns(ITEMS_EACH_ROW),
                LinearLayoutManager.VERTICAL);
        mSearchAdapter = new SearchAdapter(this, mGifsStickerList);
        mRecyclerView.setAdapter(mSearchAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSearchData = getIntent().getExtras().getParcelable(SearchActivity.SEARCH_DATA_KEY);

        mGiphyViewModel = ViewModelProviders.of(this).get(GiphyViewModel.class);
//        mGiphyViewModel.getTrendingGiphyGifs(DEFAULT_RECORD_LIMIT, DEFAULT_RATING);
//
//        // Dynamically update view
//        mGiphyViewModel.getAllTrendingGifs().observe(this, new Observer<List<Giphy>>() {
//            @Override
//            public void onChanged(@Nullable List<Giphy> gifsStickers) {
//                mGifsList = gifsStickers;
//                mSearchAdapter.refreshList(gifsStickers);
//            }
//        });
    }
}
