package com.npdevelopment.gifslashapp.fragments.top_navigation_tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.views.MainActivity;
import com.npdevelopment.gifslashapp.views.MainViewModel;
import com.npdevelopment.gifslashapp.views.adapters.TrendingGifsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrendingGifFragment extends Fragment {

    private final int ITEMS_EACH_ROW = 3;

    private View view;
    private ImageView poweredByGiphy;
    private MainViewModel sharedMainViewModel;
    private RecyclerView mRecyclerView;
    private TrendingGifsAdapter trendingGifsAdapter;

    private List<Giphy> mGifsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trending_gifs, container, false);

        poweredByGiphy = view.findViewById(R.id.powereByGiphy);
        mRecyclerView = view.findViewById(R.id.rv_trending_gifs);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                ((MainActivity) getActivity()).calculateNumberOfColumns(ITEMS_EACH_ROW),
                LinearLayoutManager.VERTICAL);
        trendingGifsAdapter = new TrendingGifsAdapter(getContext(), mGifsList);
        mRecyclerView.setAdapter(trendingGifsAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(getContext()).load(R.drawable.giphy_horizontal_light).into(poweredByGiphy);

        sharedMainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        sharedMainViewModel.getTrendingGiphyGifs(100, "G");

        // Dynamically update view
        sharedMainViewModel.getAllTrendingGifs().observe(this, new Observer<List<Giphy>>() {
            @Override
            public void onChanged(@Nullable List<Giphy> gifs) {
                mGifsList = gifs;
                trendingGifsAdapter.refreshList(gifs);
            }
        });
    }
}
