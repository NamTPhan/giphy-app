package com.npdevelopment.gifslashapp.fragments.bottom_navigation;

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
import com.npdevelopment.gifslashapp.views.MainActivity;
import com.npdevelopment.gifslashapp.views.adapters.CategoriesAdapter;

import java.util.Arrays;

public class CategoriesFragment extends Fragment {

    private final int ITEMS_EACH_ROW = 2;

    private View view;
    private RecyclerView mRecyclerView;
    private CategoriesAdapter mCategoriesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categories, container, false);

        mRecyclerView = view.findViewById(R.id.rv_categories);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                ((MainActivity) getActivity()).calculateNumberOfColumns(ITEMS_EACH_ROW),
                LinearLayoutManager.VERTICAL);
        mCategoriesAdapter = new CategoriesAdapter(getContext(),
                Arrays.asList(getResources().getStringArray(R.array.giphyCategories)));
        mRecyclerView.setAdapter(mCategoriesAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
