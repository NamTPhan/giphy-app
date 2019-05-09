package com.npdevelopment.gifslashapp.fragments.top_navigation_tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.views.MainViewModel;
import com.npdevelopment.gifslashapp.views.adapters.TrendingGifsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrendingGifFragment extends Fragment {

    private final int ITEMS_EACH_ROW = 3;

    private View view;
    private MainViewModel sharedMainViewModel;
    private List<Giphy> mGifsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TrendingGifsAdapter trendingGifsAdapter;

    public TrendingGifFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trending_gifs, container, false);

        mRecyclerView = view.findViewById(R.id.rv_trending_gifs);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(calculateNumberOfColumns(ITEMS_EACH_ROW),
                LinearLayoutManager.VERTICAL);
        trendingGifsAdapter = new TrendingGifsAdapter(getContext(),mGifsList);
        mRecyclerView.setAdapter(trendingGifsAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedMainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        sharedMainViewModel.getTrendingGiphyGifs(25,"G");

        // Dynamically update view
        sharedMainViewModel.getAllTrendingGifs().observe(this, new Observer<List<Giphy>>() {
            @Override
            public void onChanged(@Nullable List<Giphy> gifs) {
                mGifsList = gifs;
                trendingGifsAdapter.refreshList(gifs);
            }
        });

    }

    // Custom method to calculate number of columns for grid type recycler view
    private int calculateNumberOfColumns(int base){
        int columns = base;
        String screenSize = getScreenSizeCategory();

        if(screenSize.equals("small")){
            if(base!=1){
                columns = columns-1;
            }
        }else if (screenSize.equals("normal")){
            // Do nothing
        }else if(screenSize.equals("large")){
            columns += 2;
        }else if (screenSize.equals("xlarge")){
            columns += 3;
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            columns = (int) (columns * 1.5);
        }

        return columns;
    }

    // Custom method to get screen size category
    private String getScreenSizeCategory(){
        int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenLayout){
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                // small screens are at least 426dp x 320dp
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                // normal screens are at least 470dp x 320dp
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                // large screens are at least 640dp x 480dp
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                // xlarge screens are at least 960dp x 720dp
                return "xlarge";
            default:
                return "undefined";
        }
    }
}
