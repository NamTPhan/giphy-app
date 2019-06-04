package com.npdevelopment.gifslashapp.views.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.SearchData;

public class SearchActivity extends AppCompatActivity {

    private final double mPercentageWidth = 0.8;
    private final double mPercentageheight = 0.6;
    public final static String SEARCH_DATA_KEY = "searchDataKey";

    private TextInputEditText mRecordLimit, mSearhTerm;
    private Spinner mRatingSpinner, mLanguageSpinner;
    private Button mSearchBtn;

    private SearchData mSearchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearhTerm = findViewById(R.id.tv_search_query);
        mRecordLimit = findViewById(R.id.tv_record_limit);
        mRatingSpinner = findViewById(R.id.rating_spinner);
        mLanguageSpinner = findViewById(R.id.language_spinner);
        mSearchBtn = findViewById(R.id.submit_search_btn);

        setSizePopupWindow();

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchData = new SearchData(mSearhTerm.getText().toString(),
                        Integer.parseInt(mRecordLimit.getText().toString()),
                        mRatingSpinner.getSelectedItem().toString(),
                        mLanguageSpinner.getSelectedItem().toString());

                Intent intent = new Intent(SearchActivity.this, DisplaySearchActivity.class);
                intent.putExtra(SEARCH_DATA_KEY, mSearchData);
                startActivity(intent);
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
            getWindow().setLayout((int) (width * mPercentageWidth), height);
        } else {
            getWindow().setLayout((int) (width * mPercentageWidth), (int) (height * mPercentageheight));
        }
    }
}
