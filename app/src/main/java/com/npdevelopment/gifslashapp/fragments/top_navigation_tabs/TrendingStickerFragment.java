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
import com.npdevelopment.gifslashapp.views.ui.MainActivity;
import com.npdevelopment.gifslashapp.viewmodels.MainViewModel;
import com.npdevelopment.gifslashapp.views.adapters.TrendingStickersAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrendingStickerFragment extends Fragment {

    private final int ITEMS_EACH_ROW = 3;

    private View view;
    private ImageView poweredByGiphy;
    private MainViewModel sharedMainViewModel;
    private RecyclerView mRecyclerView;
    private TrendingStickersAdapter trendingStickersAdapter;

    private List<Giphy> mStickersList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trending_stickers, container, false);

        poweredByGiphy = view.findViewById(R.id.powereByGiphy);
        mRecyclerView = view.findViewById(R.id.rv_trending_stickers);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                ((MainActivity) getActivity()).calculateNumberOfColumns(ITEMS_EACH_ROW),
                LinearLayoutManager.VERTICAL);
        trendingStickersAdapter = new TrendingStickersAdapter(getContext(), mStickersList);
        mRecyclerView.setAdapter(trendingStickersAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(getContext()).load(R.drawable.giphy_horizontal_light).into(poweredByGiphy);

        sharedMainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        sharedMainViewModel.getTrendingGiphyStickers(100, "G");

        // Dynamically update view
        sharedMainViewModel.getAllTrendingStickers().observe(this, new Observer<List<Giphy>>() {
            @Override
            public void onChanged(@Nullable List<Giphy> stickers) {
                mStickersList = stickers;
                trendingStickersAdapter.refreshList(stickers);
            }
        });

    }
}
