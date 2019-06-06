package com.npdevelopment.gifslashapp.views.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.SearchData;
import com.npdevelopment.gifslashapp.utils.UserFeedback;

public class SearchActivity extends AppCompatActivity {

    private final double mPercentageWidth = 0.8;
    private final double mPercentageheight = 0.6;
    public final static String SEARCH_DATA_KEY = "searchDataKey";
    public final static String CHECKBOX_SELECTION = "checkBoxSelectionKey";

    private TextInputEditText mRecordLimit, mSearhTerm;
    private Spinner mRatingSpinner, mLanguageSpinner;
    private Button mSearchBtn;
    private CheckBox mCheckBoxGif, mCheckBoxSticker;

    private SearchData mSearchData;
    private UserFeedback mUserFeedback;

    private String checkBoxSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearhTerm = findViewById(R.id.tv_search_query);
        mRecordLimit = findViewById(R.id.tv_record_limit);
        mRatingSpinner = findViewById(R.id.rating_spinner);
        mLanguageSpinner = findViewById(R.id.language_spinner);
        mSearchBtn = findViewById(R.id.submit_search_btn);
        mCheckBoxGif = findViewById(R.id.checkBoxGifs);
        mCheckBoxSticker = findViewById(R.id.checkBoxStickers);

        mUserFeedback = new UserFeedback(getApplicationContext());
        setSizePopupWindow();

        // If checkbox gif is checked, checkbox sticker disabled
        mCheckBoxGif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckBoxSticker.setEnabled(isChecked ? false : true);
            }
        });

        // If checkbox sticker is checked, checkbox gif disabled
        mCheckBoxSticker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckBoxGif.setEnabled(isChecked ? false : true);
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mSearhTerm.getText()) || TextUtils.isEmpty(mRecordLimit.getText())) {
                    mUserFeedback.showToastShort(getString(R.string.fields_required));
                } else {

                    if (checkBoxCheck()) {
                        mSearchData = new SearchData(mSearhTerm.getText().toString(),
                                Integer.parseInt(mRecordLimit.getText().toString()),
                                mRatingSpinner.getSelectedItem().toString(),
                                mLanguageSpinner.getSelectedItem().toString());

                        Intent intent = new Intent(SearchActivity.this, DisplaySearchActivity.class);
                        intent.putExtra(SEARCH_DATA_KEY, mSearchData);
                        intent.putExtra(CHECKBOX_SELECTION, checkBoxSelection);
                        startActivity(intent);
                    } else {
                        mUserFeedback.showToastShort(getString(R.string.checkbox_failed));
                    }

                }
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

    private boolean checkBoxCheck() {

        if (!mCheckBoxGif.isChecked() && !mCheckBoxSticker.isChecked()) {
            return false;
        }

        if (mCheckBoxGif.isChecked()) {
            checkBoxSelection = getString(R.string.gif_title);
            mCheckBoxSticker.setEnabled(false);
        } else if (mCheckBoxSticker.isChecked()) {
            checkBoxSelection = getString(R.string.sticker_title);
            mCheckBoxGif.setEnabled(false);
        }

        return true;
    }
}
