package com.npdevelopment.gifslashapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.fragments.bottom_navigation.CategoriesFragment;
import com.npdevelopment.gifslashapp.fragments.bottom_navigation.FavoritesFragment;
import com.npdevelopment.gifslashapp.fragments.bottom_navigation.TrendingFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment;
    private FloatingActionButton fabPlus, fabSettings, fabRandom;
    private Animation fabOpen, fabClose, fabRotateClockwise, fabRotateAntiClockwise;
    private MainViewModel mainViewModel;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On launch activate default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrendingFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fabPlus = findViewById(R.id.fab_plus);
        fabSettings = findViewById(R.id.fab_settings);
        fabRandom = findViewById(R.id.fab_random);
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRotateAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fabActions();
        disableActionBarInLandScapeMode();

        // Link the correct ViewModel to the activity
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
            }
        });

        // Show error in toast if api call fails
        mainViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
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
        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    fabRandom.startAnimation(fabClose);
                    fabSettings.startAnimation(fabClose);
                    fabPlus.startAnimation(fabRotateAntiClockwise);

                    fabRandom.setClickable(false);
                    fabSettings.setClickable(false);
                    isOpen = false;
                } else {
                    fabRandom.startAnimation(fabOpen);
                    fabSettings.startAnimation(fabOpen);
                    fabPlus.startAnimation(fabRotateClockwise);

                    fabRandom.setClickable(true);
                    fabSettings.setClickable(true);
                    isOpen = true;
                }
            }
        });

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
            }
        });

        fabRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Random", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Custom method to calculate number of columns for grid type recycler view
     *
     * @param base default column selection
     * @return total columns based on screen size
     */
    public int calculateNumberOfColumns(int base) {
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

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columns = (int) (columns * 1.5);
        }

        return columns;
    }

    /**
     * Custom method to get screen size category
     *
     * @return current screen layout
     */
    private String getScreenSizeCategory() {
        int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

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
