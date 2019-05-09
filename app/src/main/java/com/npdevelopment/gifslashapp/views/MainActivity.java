package com.npdevelopment.gifslashapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
}
