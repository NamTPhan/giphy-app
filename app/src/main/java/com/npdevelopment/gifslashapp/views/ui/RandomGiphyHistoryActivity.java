package com.npdevelopment.gifslashapp.views.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Favorite;
import com.npdevelopment.gifslashapp.models.History;
import com.npdevelopment.gifslashapp.utils.UserFeedback;
import com.npdevelopment.gifslashapp.viewmodels.HistoryViewModel;
import com.npdevelopment.gifslashapp.views.adapters.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class RandomGiphyHistoryActivity extends AppCompatActivity implements HistoryAdapter.HistoryCardListener {

    private final int ITEMS_EACH_ROW = 1;

    private RecyclerView mRecyclerView;
    private Snackbar mSnackBar;

    private HistoryViewModel mHistoryViewModel;
    private HistoryAdapter mHistoryAdapter;
    private History mHistoryGifSticker;
    private UserFeedback mUserFeedback;

    private List<History> mHistoryList, mTempHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_giphy_history);

        // Enable back arrow in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.giphy_random_history_title));

        mRecyclerView = findViewById(R.id.rv_history);
        mUserFeedback = new UserFeedback(getApplicationContext());
        mHistoryList = new ArrayList<>();

        final MainActivity mainActivity = new MainActivity();

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                mainActivity.calculateNumberOfColumns(ITEMS_EACH_ROW),
                LinearLayoutManager.VERTICAL);

        mHistoryAdapter = new HistoryAdapter(this, mHistoryList, this);
        mRecyclerView.setAdapter(mHistoryAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mHistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        // Dynamically update view
        mHistoryViewModel.getHistory().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(@Nullable List<History> historyList) {
                mHistoryList = historyList;
                mHistoryAdapter.refreshList(historyList);
            }
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

                        mHistoryGifSticker = mHistoryList.get(position);
                        mHistoryViewModel.delete(mHistoryList.get(position));
                        mHistoryAdapter.notifyItemRemoved(position);

                        // Give the user the chance to restore the deleted history card
                        mSnackBar = Snackbar.make(findViewById(R.id.rv_history), "Deleted: " +
                                mHistoryList.get(position).getTitle(), Snackbar.LENGTH_LONG).setAction(R.string.undo_string,
                                new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        mUserFeedback.showToastShort(getString(R.string.restored));
                                        mHistoryViewModel.insert(mHistoryGifSticker);
                                        mHistoryAdapter.refreshList(mHistoryList);
                                    }
                                });
                        mSnackBar.show();
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trash_can, menu);
        return true;
    }

    /**
     * Icons defined in the toolbar
     *
     * @param item the menu item
     * @return a boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        // Delete all history
        if (id == R.id.action_delete_item) {
            // Save the history items list in a temporary list for restoring the data
            mTempHistoryList = mHistoryList;
            mHistoryViewModel.deleteAll(mHistoryList);

            mSnackBar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.deleted_message),
                    Snackbar.LENGTH_LONG).setAction(R.string.undo_string,
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            mUserFeedback.showToastLong(getString(R.string.restored));
                            mHistoryViewModel.insertAll(mTempHistoryList);
                            mHistoryAdapter.refreshList(mHistoryList);
                        }
                    });
            mSnackBar.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCardClick(History historyGifSticker) {
        Intent intent = new Intent(getApplicationContext(), DisplayGiphyActivity.class);
        // Send request code of edit status
        intent.putExtra(MainActivity.GIPHY_CODE_KEY, MainActivity.SHOW_HISTORY_CARD);
        // Send the object that has to be edited
        intent.putExtra(MainActivity.GIPHY_ITEM_KEY, historyGifSticker);
        startActivity(intent);
    }
}
