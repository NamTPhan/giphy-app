package com.npdevelopment.gifslashapp.views.fragments.bottom_navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.views.ui.DisplaySearchActivity;
import com.npdevelopment.gifslashapp.views.ui.MainActivity;
import com.npdevelopment.gifslashapp.views.adapters.CategoriesAdapter;

import java.util.Arrays;

public class CategoriesFragment extends Fragment implements CategoriesAdapter.CategoryListener {

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
                Arrays.asList(getResources().getStringArray(R.array.giphyCategories)), this);
        mRecyclerView.setAdapter(mCategoriesAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onCardClick(String category) {
        Intent intent = new Intent(getActivity(), DisplaySearchActivity.class);
        // Send request code of edit status
        intent.putExtra(MainActivity.GIPHY_CODE_KEY, MainActivity.CATEGORY_SEARCH_CODE);
        // Send category name
        intent.putExtra(MainActivity.GIPHY_ITEM_KEY, category);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
