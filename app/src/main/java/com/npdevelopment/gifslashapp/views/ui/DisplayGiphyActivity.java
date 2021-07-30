package com.npdevelopment.gifslashapp.views.ui;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Favorite;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.models.History;
import com.npdevelopment.gifslashapp.utils.NetworkConnection;
import com.npdevelopment.gifslashapp.utils.Permissions;
import com.npdevelopment.gifslashapp.utils.UserFeedback;
import com.npdevelopment.gifslashapp.viewmodels.FavoriteViewModel;
import com.npdevelopment.gifslashapp.viewmodels.GiphyViewModel;
import com.npdevelopment.gifslashapp.viewmodels.HistoryViewModel;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DisplayGiphyActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 111;
    private final String DEFAULT_RATING = "PG-13";

    private ImageView mGiphyImage, mPoweredByGiphy;
    private CardView mSaveFavoriteCard;
    private ImageButton mFavoriteBtn;
    private Button mDownloadBtn, mShareBtn, mSubmitBtn;
    private TextInputEditText mTitle, mDescription;

    private Giphy mGiphyGifSticker;
    private Favorite mFavorite;
    private History mHistory;
    private FavoriteViewModel mFavoriteViewModel;
    private GiphyViewModel mGiphyViewModel;
    private HistoryViewModel mHistoryViewModel;
    private Permissions mPermissions;
    private NetworkConnection mNetworkConnection;
    private UserFeedback mUserFeedback;

    private int mRetrievedCode;
    private String mImageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_giphy);

        // Enable back arrow in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.giphy_sticker_title));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mGiphyImage = findViewById(R.id.displayGiphyImage);
        mPoweredByGiphy = findViewById(R.id.poweredByGiphy);
        mSaveFavoriteCard = findViewById(R.id.card_save_favorite);
        mDownloadBtn = findViewById(R.id.download_btn);
        mShareBtn = findViewById(R.id.share_btn);
        mFavoriteBtn = findViewById(R.id.favorite_btn);
        mSubmitBtn = findViewById(R.id.submitBtn);
        mTitle = findViewById(R.id.title_favorite);
        mDescription = findViewById(R.id.description_favorite);

        // Load powered by Giphy gif on top
        Glide.with(this).load(R.drawable.giphy_horizontal_light).into(mPoweredByGiphy);

        // Get View Model
        mFavoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        mGiphyViewModel =  new ViewModelProvider(this).get(GiphyViewModel.class);
        mHistoryViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        // Creating instances of several classes
        mPermissions = new Permissions(DisplayGiphyActivity.this);
        mNetworkConnection = new NetworkConnection(getApplicationContext());
        mUserFeedback = new UserFeedback(getApplicationContext());

        mRetrievedCode = getIntent().getExtras().getInt(MainActivity.GIPHY_CODE_KEY);

        // Based on retrieved code load the correct data
        switch (mRetrievedCode) {
            // Retrieve random GIF from Giphy
            case MainActivity.RANDOM_GIF_CODE:
                mGiphyViewModel.getRandomGif(DEFAULT_RATING);

                mGiphyViewModel.getOneRandomGif().observe(this, gif -> {
                    saveRandomGifStickerInHistory(gif);
                    setAllDataGiphy(gif);
                });
                break;
            // Retrieve random sticker from Giphy
            case MainActivity.RANDOM_STICKER_CODE:
                mGiphyViewModel.getRandomSticker(DEFAULT_RATING);

                mGiphyViewModel.getOneRandomSticker().observe(this, sticker -> {
                    saveRandomGifStickerInHistory(sticker);
                    setAllDataGiphy(sticker);
                });
                break;
            // Retrieve favorite GIF/Sticker from the passed object
            case MainActivity.SHOW_FAVORITE_GIPHY_CODE:
                mFavorite = getIntent().getExtras().getParcelable(MainActivity.GIPHY_ITEM_KEY);

                mSubmitBtn.setText(getString(R.string.save_button));
                mSaveFavoriteCard.setVisibility(View.VISIBLE);
                setAllDataFavorite(mFavorite);
                break;
            // Retrieve History GIF/Sticker from the passed object
            case MainActivity.SHOW_HISTORY_CARD_CODE:
                mHistory = getIntent().getExtras().getParcelable(MainActivity.GIPHY_ITEM_KEY);
                setAllDataHistory(mHistory);
                break;
            // Default load the object that was passed
            default:
                mGiphyGifSticker = getIntent().getExtras().getParcelable(MainActivity.GIPHY_ITEM_KEY);
                setAllDataGiphy(mGiphyGifSticker);
                break;
        }

        // Set button listeners
        buttonActions();
    }

    /**
     * Listen to the callback of device requests
     *
     * @param requestCode  that is defined as constants
     * @param permissions  the permission what it is all about
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, show message
                    mUserFeedback.showSnackBarLong(getWindow().getDecorView().getRootView(),
                            getString(R.string.permission_granted), R.color.colorAccent);
                } else {
                    mUserFeedback.showSnackBarLong(getWindow().getDecorView().getRootView(),
                            getString(R.string.write_permission_required), R.color.red);
                }
                return;
            }
        }
    }

    /**
     * Icons defined in the toolbar
     *
     * @param item the menu item
     * @return a boolean
     */
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
     *
     * @return the current date as string
     */
    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = Calendar.getInstance().getTime();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }

    /**
     * Set all data of the Giphy object
     *
     * @param object Giphy object that is passed by the activity
     */
    private void setAllDataGiphy(Giphy object) {
        // Save image url in variable to use for download and share
        mImageUrl = object.getImages().getImageFixedHeight().getUrl();

        Glide.with(getApplicationContext()).load(object.getImages().getImageFixedHeight().getUrl()).into(mGiphyImage);
        mTitle.setText(object.getTitle());
    }

    private void saveRandomGifStickerInHistory(Giphy object) {
        History newHistoryObject = new History(
                object.getTitle(),
                null,
                getCurrentDate(),
                object.getImages().getImageFixedHeight().getUrl());

        mHistoryViewModel.insert(newHistoryObject);
    }

    /**
     * Set all data of the Favorite object
     *
     * @param object Favorite object that is passed by the activity
     */
    private void setAllDataFavorite(Favorite object) {
        // Save image url in variable to use for download and share
        mImageUrl = object.getImageUrl();

        Glide.with(getApplicationContext()).load(object.getImageUrl()).into(mGiphyImage);
        mTitle.setText(object.getTitle());
        mDescription.setText(object.getDescription());
    }

    /**
     * Set all data of the History object
     *
     * @param object History object that is passed by the activity
     */
    private void setAllDataHistory(History object) {
        // Save image url in variable to use for download and share
        mImageUrl = object.getImageUrl();

        Glide.with(getApplicationContext()).load(object.getImageUrl()).into(mGiphyImage);
        mTitle.setText(object.getTitle());
        mDescription.setText(object.getDescription());
    }

    /**
     * Set all actions of the buttons
     */
    private void buttonActions() {
        mFavoriteBtn.setOnClickListener(v -> {
            mSaveFavoriteCard.setVisibility(View.VISIBLE);
            mPoweredByGiphy.setVisibility(View.GONE);
        });

        mShareBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.check_out_share));
            intent.putExtra(Intent.EXTRA_TEXT, mImageUrl);
            startActivity(Intent.createChooser(intent, getString(R.string.share)));
        });

        mDownloadBtn.setOnClickListener(v -> {
            if (mNetworkConnection.availableNetworkConnection()) {
                if (mPermissions.checkPermissionExternalStorage()) {
                    saveImageToGallery(mImageUrl);

                    mUserFeedback.showSnackBarLong(getWindow().getDecorView().getRootView(),
                            getString(R.string.success_message), R.color.colorAccent);
                } else {
                    mUserFeedback.showSnackBarLong(getWindow().getDecorView().getRootView(),
                            getString(R.string.write_permission_required), R.color.red);
                }
            } else {
                mUserFeedback.showSnackBarLong(getWindow().getDecorView().getRootView(),
                        getString(R.string.no_internet_connection), R.color.colorPrimary);
            }
        });

        mSubmitBtn.setOnClickListener(v -> {
            // Set all data in favorite object
            if (mRetrievedCode == MainActivity.SHOW_FAVORITE_GIPHY_CODE) {
                mFavorite.setTitle(mTitle.getText().toString());
                mFavorite.setDescription(mDescription.getText().toString());
                mFavorite.setDateSaved(getCurrentDate());
                mFavorite.setImageUrl(mFavorite.getImageUrl());

                mFavoriteViewModel.update(mFavorite);
                finish();
            } else {
                mFavorite = new Favorite(
                        mTitle.getText().toString(),
                        mDescription.getText().toString(),
                        getCurrentDate(),
                        mImageUrl);

                mFavoriteViewModel.insert(mFavorite);
            }

            mSaveFavoriteCard.setVisibility(View.GONE);
            mUserFeedback.showToastLong(getString(R.string.success_message));
        });
    }

    /**
     * Function to download the GIFS
     *
     * @param url the download url
     */
    private void saveImageToGallery(String url) {
        File dir = new File(Environment.getExternalStorageDirectory()
                + getString(R.string.save_to_folder));

        if (!dir.exists()) {
            dir.mkdirs();
        }

        DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(getString(R.string.download))
                .setDestinationInExternalPublicDir(getString(R.string.save_to_folder), getString(R.string.app_name) + ".gif");

        downloadManager.enqueue(request);
    }

}
