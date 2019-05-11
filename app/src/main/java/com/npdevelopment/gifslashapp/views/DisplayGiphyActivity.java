package com.npdevelopment.gifslashapp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.views.adapters.TrendingGifsAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DisplayGiphyActivity extends AppCompatActivity {

    private ImageView mGiphyImage;
    private CardView mSaveFavoriteCard;
    private ImageButton mFavoriteBtn;
    private Button mDownloadBtn, mShareBtn, mSubmitBtn;
    private TextInputEditText mTitle, mDescription;
    private Giphy giphyGifSticker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_giphy);

        // Enable back arrow in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.giphy_sticker_title));

        mGiphyImage = findViewById(R.id.displayGiphyImage);
        mSaveFavoriteCard = findViewById(R.id.card_save_favorite);
        mDownloadBtn = findViewById(R.id.download_btn);
        mShareBtn = findViewById(R.id.share_btn);
        mFavoriteBtn = findViewById(R.id.favorite_btn);
        mSubmitBtn = findViewById(R.id.submitBtn);
        mTitle = findViewById(R.id.title_favorite);
        mDescription = findViewById(R.id.description_favorite);

        retrieveAndSetData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back arrow
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Get current date in the format dd/MM/yyyy
     * @return the current date as string
     */
    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = Calendar.getInstance().getTime();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }

    private void retrieveAndSetData() {
        giphyGifSticker = getIntent().getExtras().getParcelable(TrendingGifsAdapter.GIPHY_ITEM_KEY);

        Glide.with(getApplicationContext()).load(giphyGifSticker.getImages().getImageFixedHeight().getUrl()).into(mGiphyImage);
        mTitle.setText(giphyGifSticker.getTitle());
    }
}
