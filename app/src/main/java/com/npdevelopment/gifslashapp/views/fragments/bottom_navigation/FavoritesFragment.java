package com.npdevelopment.gifslashapp.views.fragments.bottom_navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Favorite;
import com.npdevelopment.gifslashapp.utils.UserFeedback;
import com.npdevelopment.gifslashapp.viewmodels.FavoriteViewModel;
import com.npdevelopment.gifslashapp.views.adapters.FavoritesAdapter;
import com.npdevelopment.gifslashapp.views.ui.DisplayGiphyActivity;
import com.npdevelopment.gifslashapp.views.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements FavoritesAdapter.FavoriteListener {

    private final int ITEMS_EACH_ROW = 1;

    private View view;
    private RecyclerView mRecyclerView;
    private Snackbar mSnackBar;

    private FavoriteViewModel mFavoriteViewModel;
    private FavoritesAdapter mFavoritesAdapter;
    private Favorite mFavorite;
    private UserFeedback mUserFeedback;

    private List<Favorite> mFavoriteList, tempFavoritesList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        mRecyclerView = view.findViewById(R.id.rv_favorites);
        mUserFeedback = new UserFeedback(getContext());
        mFavoriteList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                ((MainActivity) getActivity()).calculateNumberOfColumns(ITEMS_EACH_ROW),
                LinearLayoutManager.VERTICAL);

        mFavoritesAdapter = new FavoritesAdapter(getContext(), mFavoriteList, this);
        mRecyclerView.setAdapter(mFavoritesAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFavoriteViewModel = ViewModelProviders.of(getActivity()).get(FavoriteViewModel.class);

        // Dynamically update view
        mFavoriteViewModel.getFavorites().observe(this, favorites -> {
            mFavoriteList = favorites;
            mFavoritesAdapter.refreshList(favorites);
        });

        // Recognize swipe gesture of the user
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    // Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        // Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());

                        mFavorite = mFavoriteList.get(position);
                        mFavoriteViewModel.delete(mFavoriteList.get(position));
                        mFavoritesAdapter.notifyItemRemoved(position);

                        // Give the user the chance to restore the favorite
                        mSnackBar = Snackbar.make(getView(), "Deleted: " +
                                mFavoriteList.get(position).getTitle(), Snackbar.LENGTH_LONG).setAction(R.string.undo_string,
                                v -> {
                                    mUserFeedback.showToastShort(getString(R.string.restored));
                                    mFavoriteViewModel.insert(mFavorite);
                                    mFavoritesAdapter.refreshList(mFavoriteList);
                                });
                        mSnackBar.show();
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onCardClick(Favorite favorite) {
        Intent intent = new Intent(getActivity(), DisplayGiphyActivity.class);
        // Send request code of edit status
        intent.putExtra(MainActivity.GIPHY_CODE_KEY, MainActivity.SHOW_FAVORITE_GIPHY_CODE);
        // Send the object that has to be edited
        intent.putExtra(MainActivity.GIPHY_ITEM_KEY, favorite);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_trash_can, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Delete all
        if (id == R.id.action_delete_item) {
            // Save the favorites list in a temporary list for restoring the data
            tempFavoritesList = mFavoriteList;
            mFavoriteViewModel.deleteAll(mFavoriteList);

            mSnackBar = Snackbar.make(getView(), getString(R.string.deleted_message),
                    Snackbar.LENGTH_LONG).setAction(R.string.undo_string,
                    v -> {
                        mUserFeedback.showToastShort(getString(R.string.restored));
                        mFavoriteViewModel.insertAll(tempFavoritesList);
                        mFavoritesAdapter.refreshList(mFavoriteList);
                    });
            mSnackBar.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
