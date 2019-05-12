package com.npdevelopment.gifslashapp.views;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.utils.NetworkConnection;
import com.npdevelopment.gifslashapp.utils.Permissions;
import com.npdevelopment.gifslashapp.views.adapters.TrendingGifsAdapter;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DisplayGiphyActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 111;

    private ImageView mGiphyImage;
    private CardView mSaveFavoriteCard;
    private ImageButton mFavoriteBtn;
    private Button mDownloadBtn, mShareBtn, mSubmitBtn;
    private TextInputEditText mTitle, mDescription;
    private Snackbar mSnackBar;

    private Giphy giphyGifSticker;
    private Permissions permissions;
    private NetworkConnection networkConnection;

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

        permissions = new Permissions(DisplayGiphyActivity.this);
        networkConnection = new NetworkConnection(getApplicationContext());
        retrieveAndSetData();

        mFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaveFavoriteCard.setVisibility(View.VISIBLE);
            }
        });

        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.check_out_share));
                intent.putExtra(Intent.EXTRA_TEXT, giphyGifSticker.getImages().getImageFixedHeight().getUrl());
                startActivity(intent);
            }
        });

        mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkConnection.availableNetworkConnection()) {
                    if (permissions.checkPermissionExternalStorage()) {
                        saveImageToGallery(giphyGifSticker.getImages().getImageFixedHeight().getUrl());
                        showSnackBar(getString(R.string.success_message), R.color.colorAccent);
                    } else {
                        showSnackBar(getString(R.string.write_permission_required), R.color.red);
                    }
                } else {
                    showSnackBar(getString(R.string.no_internet_connection), R.color.colorPrimary);
                }
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaveFavoriteCard.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, show message
                    showSnackBar(getString(R.string.permission_granted), R.color.colorAccent);
                } else {
                    showSnackBar(getString(R.string.write_permission_required), R.color.red);
                }
                return;
            }
        }
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
     *
     * @return the current date as string
     */
    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = Calendar.getInstance().getTime();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }

    private void showSnackBar(String message, int colorId) {
        mSnackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View sbView = mSnackBar.getView();
        sbView.setBackgroundColor(getResources().getColor(colorId));
        mSnackBar.show();
    }

    /**
     * Get the passed data from the intent and display the data
     */
    private void retrieveAndSetData() {
        giphyGifSticker = getIntent().getExtras().getParcelable(TrendingGifsAdapter.GIPHY_ITEM_KEY);

        Glide.with(getApplicationContext()).load(giphyGifSticker.getImages().getImageFixedHeight().getUrl()).into(mGiphyImage);
        mTitle.setText(giphyGifSticker.getTitle());
    }

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
