package com.npdevelopment.gifslashapp.views.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.database.GiphyRoomDatabase;
import com.npdevelopment.gifslashapp.viewmodels.GiphyViewModel;
import com.npdevelopment.gifslashapp.views.fragments.bottom_navigation.CategoriesFragment;
import com.npdevelopment.gifslashapp.views.fragments.bottom_navigation.FavoritesFragment;
import com.npdevelopment.gifslashapp.views.fragments.bottom_navigation.TrendingFragment;

public class MainActivity extends AppCompatActivity {

    public static final String GIPHY_ITEM_KEY = "giphyItemKey";
    public static final String GIPHY_CODE_KEY = "giphyRandomItemKey";
    public static final int RANDOM_GIF_CODE = 888;
    public static final int RANDOM_STICKER_CODE = 777;
    public static final int SHOW_FAVORITE_GIPHY_CODE = 666;
    public static final int SHOW_HISTORY_CARD_CODE = 555;
    public static final int CATEGORY_SEARCH_CODE = 444;
    public static final int DEFAULT_SEARCH_CODE = 333;
    private static Context sContext;

    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment;
    private FloatingActionButton fabPlus, fabSearch, fabRandomGif, fabRandomSticker;
    private Animation fabOpen, fabClose, fabRotateClockwise, fabRotateAntiClockwise;
    private TextView labelSearch, labelRandomGif, labelRandomSticker;

    private GiphyViewModel mGiphyViewModel;
    private GiphyRoomDatabase mDatabase;

    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On launch activate default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrendingFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fabPlus = findViewById(R.id.fab_plus);
        fabSearch = findViewById(R.id.fab_search);
        fabRandomGif = findViewById(R.id.fab_random_gif);
        fabRandomSticker = findViewById(R.id.fab_random_sticker);
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRotateAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        labelSearch = findViewById(R.id.label_search);
        labelRandomGif = findViewById(R.id.label_random_gif);
        labelRandomSticker = findViewById(R.id.label_random_sticker);

        mDatabase = GiphyRoomDatabase.getDatabase(this);
        sContext = this;

        fabActions();
        disableActionBarInLandScapeMode();

        // Link the correct ViewModel to the activity
        mGiphyViewModel = new ViewModelProvider(this).get(GiphyViewModel.class);

        // Bottom navigation switch
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.nav_trending:
                    selectedFragment = new TrendingFragment();
                    break;
                case R.id.nav_categories:
                    selectedFragment = new CategoriesFragment();
                    break;
                case R.id.nav_favorite:
                    selectedFragment = new FavoritesFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });

        // Show error in toast if api call fails
        mGiphyViewModel.getError().observe(this, message -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show());
    }

    /**
     * Disable actionbar when the orientation is in landscape
     */
    public void disableActionBarInLandScapeMode() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }

    /**
     * Contains all floating button actions
     */
    private void fabActions() {
        // Open and close child floating buttons
        fabPlus.setOnClickListener(v -> {
            if (isOpen) {
                fabPlus.startAnimation(fabRotateAntiClockwise);
                fabAnimationVisibilityClickable(fabClose, View.INVISIBLE, false);
            } else {
                fabPlus.startAnimation(fabRotateClockwise);
                fabAnimationVisibilityClickable(fabOpen, View.VISIBLE, true);
            }
        });

        // Open search window
        fabSearch.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));

        // Go to display Giphy activity and retrieve random gif
        fabRandomGif.setOnClickListener(v -> goToDisplayRandomGifAnimation(RANDOM_GIF_CODE));

        // Go to display Giphy activity and retrieve random sticker
        fabRandomSticker.setOnClickListener(v -> goToDisplayRandomGifAnimation(RANDOM_STICKER_CODE));
    }

    private void goToDisplayRandomGifAnimation(int statusCode) {
        Intent intent = new Intent(MainActivity.this, RandomGifAnimationActivity.class);
        intent.putExtra(GIPHY_CODE_KEY, statusCode);
        startActivity(intent);
    }

    private void fabAnimationVisibilityClickable(Animation animation, int visible, boolean status) {
        fabRandomSticker.startAnimation(animation);
        fabRandomGif.startAnimation(animation);
        fabSearch.startAnimation(animation);
        labelSearch.setVisibility(visible);
        labelRandomGif.setVisibility(visible);
        labelRandomSticker.setVisibility(visible);

        fabRandomSticker.setClickable(status);
        fabRandomGif.setClickable(status);
        fabSearch.setClickable(status);
        isOpen = status;
    }

    /**
     * Custom method to calculate number of columns for grid type recycler view
     *
     * @param base default column selection
     * @return total columns based on screen size
     */
    public static int calculateNumberOfColumns(int base) {
        int columns = base;
        String screenSize = getScreenSizeCategory();

        if (screenSize.equals("small")) {
            if (base != 1) {
                columns = columns - 1;
            }
        } else if (screenSize.equals("normal")) {
            // Do nothing
        } else if (screenSize.equals("large")) {
            columns += 2;
        } else if (screenSize.equals("xlarge")) {
            columns += 3;
        }

        if (sContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columns = (int) (columns * 1.5);
        }

        return columns;
    }

    /**
     * Custom method to get screen size category
     *
     * @return current screen layout
     */
    private static String getScreenSizeCategory() {
        int screenLayout = sContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
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
