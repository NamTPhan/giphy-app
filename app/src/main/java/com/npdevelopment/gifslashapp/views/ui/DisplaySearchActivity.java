package com.npdevelopment.gifslashapp.views.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.models.SearchData;
import com.npdevelopment.gifslashapp.viewmodels.GiphyViewModel;
import com.npdevelopment.gifslashapp.views.adapters.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class DisplaySearchActivity extends AppCompatActivity {

    private final int ITEMS_EACH_ROW = 3;
    private final int DEFAULT_RECORD_LIMIT = 200;
    private final String DEFAULT_RATING = "PG-13";
    private final String DEFAULT_LANGUAGE = "en";

    private ImageView mPoweredByGiphy;
    private RecyclerView mRecyclerView;

    private GiphyViewModel mGiphyViewModel;
    private SearchAdapter mSearchAdapter;
    private MainActivity mMainActivity;
    private SearchData mSearchData;

    private List<Giphy> mGifsStickersList;
    private String mSelectedCheckBox, mCategorySearchQuery;
    private int mRetrievedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);

        mPoweredByGiphy = findViewById(R.id.powereByGiphy);
        mRecyclerView = findViewById(R.id.rv_search_selection);
        mGifsStickersList = new ArrayList<>();

        // Enable back arrow in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Load powered by Giphy gif on top
        Glide.with(this).load(R.drawable.giphy_horizontal_light).into(mPoweredByGiphy);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                mMainActivity.calculateNumberOfColumns(ITEMS_EACH_ROW),
                LinearLayoutManager.VERTICAL);

        mSearchAdapter = new SearchAdapter(this, mGifsStickersList);
        mRecyclerView.setAdapter(mSearchAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mGiphyViewModel = ViewModelProviders.of(this).get(GiphyViewModel.class);

        mRetrievedCode = getIntent().getExtras().getInt(MainActivity.GIPHY_CODE_KEY);

        switch (mRetrievedCode) {
            case MainActivity.DEFAULT_SEARCH_CODE:
                // Get search data passed from the search activity
                mSearchData = getIntent().getExtras().getParcelable(SearchActivity.SEARCH_DATA_KEY);
                mSelectedCheckBox = getIntent().getExtras().getString(SearchActivity.CHECKBOX_SELECTION);
                // Load search query in the action bar
                getSupportActionBar().setTitle(mSearchData.getSearchQuery());

                // Determine which api call based on the selected check box
                if (mSelectedCheckBox.equals(getString(R.string.gif_title))) {
                    getGifsFromApi();
                } else {
                    getStickersFromApi();
                }
                break;

            case MainActivity.CATEGORY_SEARCH_CODE:
                mCategorySearchQuery = getIntent().getExtras().getString(MainActivity.GIPHY_ITEM_KEY);

                mGiphyViewModel.getGifsBasedOnSearchQuery(mCategorySearchQuery, DEFAULT_RECORD_LIMIT,
                        DEFAULT_RATING, DEFAULT_LANGUAGE);
                mGiphyViewModel.getAllSearchedGifs().observe(this, new Observer<List<Giphy>>() {
                    @Override
                    public void onChanged(@Nullable List<Giphy> gifs) {
                        mGifsStickersList = gifs;
                        mSearchAdapter.refreshList(mGifsStickersList);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * Icons defined in the toolbar
     *
     * @param item the menu item
     * @return a boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back arrow
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void getGifsFromApi() {
        mGiphyViewModel.getGifsBasedOnSearchQuery(mSearchData.getSearchQuery(), mSearchData.getRecordLimit(),
                mSearchData.getRating(), mSearchData.getLanguage());
        mGiphyViewModel.getAllSearchedGifs().observe(this, new Observer<List<Giphy>>() {
            @Override
            public void onChanged(@Nullable List<Giphy> gifs) {
                mGifsStickersList = gifs;
                mSearchAdapter.refreshList(mGifsStickersList);
            }
        });
    }

    private void getStickersFromApi() {
        mGiphyViewModel.getStickersBasedOnSearchQuery(mSearchData.getSearchQuery(), mSearchData.getRecordLimit(),
                mSearchData.getRating(), mSearchData.getLanguage());
        mGiphyViewModel.getAllSearchedStickers().observe(this, new Observer<List<Giphy>>() {
            @Override
            public void onChanged(@Nullable List<Giphy> stickers) {
                mGifsStickersList = stickers;
                mSearchAdapter.refreshList(mGifsStickersList);
            }
        });
    }
}
