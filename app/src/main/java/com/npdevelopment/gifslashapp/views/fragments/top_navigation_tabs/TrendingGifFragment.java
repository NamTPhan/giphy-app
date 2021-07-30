package com.npdevelopment.gifslashapp.views.fragments.top_navigation_tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.viewmodels.GiphyViewModel;
import com.npdevelopment.gifslashapp.views.ui.MainActivity;
import com.npdevelopment.gifslashapp.views.adapters.TrendingGifsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrendingGifFragment extends Fragment {

    private final int ITEMS_EACH_ROW = 3;
    private final int DEFAULT_RECORD_LIMIT = 500;
    private final String DEFAULT_RATING = "PG-13";

    private View view;
    private ImageView poweredByGiphy;
    private GiphyViewModel sharedGiphyViewModel;
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

        sharedGiphyViewModel = ViewModelProviders.of(getActivity()).get(GiphyViewModel.class);
        sharedGiphyViewModel.getTrendingGiphyGifs(DEFAULT_RECORD_LIMIT, DEFAULT_RATING);

        // Dynamically update view
        sharedGiphyViewModel.getAllTrendingGifs().observe(this, gifs -> {
            mGifsList = gifs;
            trendingGifsAdapter.refreshList(gifs);
        });
    }
}
