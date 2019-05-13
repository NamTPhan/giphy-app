package com.npdevelopment.gifslashapp.views.ui;

import android.content.res.Configuration;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.npdevelopment.gifslashapp.R;

public class SettingsActivity extends AppCompatActivity {

    private final double percentageWidth = 0.8;
    private final double percentageheight = 0.6;

    private TextInputEditText recordLimit;
    private Spinner ratingSpinner, languageSpinner;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        recordLimit = findViewById(R.id.tv_record_limit);
        ratingSpinner = findViewById(R.id.rating_spinner);
        languageSpinner = findViewById(R.id.language_spinner);
        saveBtn = findViewById(R.id.save_settings_btn);

        setSizePopupWindow();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Calculate window size based on screen size
     */
    private void setSizePopupWindow() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setLayout((int) (width * percentageWidth), height);
        } else {
            getWindow().setLayout((int) (width * percentageWidth), (int) (height * percentageheight));
        }
    }
}
