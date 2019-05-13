package com.npdevelopment.gifslashapp.fragments.bottom_navigation;

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

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Favorite;
import com.npdevelopment.gifslashapp.viewmodels.FavoriteViewModel;
import com.npdevelopment.gifslashapp.views.adapters.CategoriesAdapter;
import com.npdevelopment.gifslashapp.views.adapters.FavoritesAdapter;
import com.npdevelopment.gifslashapp.views.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private final int ITEMS_EACH_ROW = 1;

    private View view;
    private RecyclerView mRecyclerView;
    private FavoriteViewModel mFavoriteViewModel;
    private FavoritesAdapter mFavoritesAdapter;

    private List<Favorite> mFavoriteList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        mRecyclerView = view.findViewById(R.id.rv_favorites);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                ((MainActivity) getActivity()).calculateNumberOfColumns(ITEMS_EACH_ROW),
                LinearLayoutManager.VERTICAL);
        mFavoritesAdapter = new FavoritesAdapter(getContext(), mFavoriteList);
        mRecyclerView.setAdapter(mFavoritesAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFavoriteViewModel = ViewModelProviders.of(getActivity()).get(FavoriteViewModel.class);

        // Dynamically update view
        mFavoriteViewModel.getFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                mFavoriteList = favorites;
                mFavoritesAdapter.refreshList(favorites);
            }
        });
    }
}
