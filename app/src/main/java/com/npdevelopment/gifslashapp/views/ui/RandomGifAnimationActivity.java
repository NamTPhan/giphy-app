package com.npdevelopment.gifslashapp.views.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.npdevelopment.gifslashapp.R;

import static com.npdevelopment.gifslashapp.views.ui.MainActivity.GIPHY_CODE_KEY;

public class RandomGifAnimationActivity extends AppCompatActivity {

    private final int BOUNCE_DIRECTION = -100;

    private ImageView mQuestionSquare;
    private ObjectAnimator mAnimatorBounce;

    private int mRetrievedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_gif_animation);

        getSupportActionBar().hide();

        mQuestionSquare = findViewById(R.id.iv_question_square);

        // Get the passed code to determine if its a gif or sticker
        mRetrievedCode = getIntent().getExtras().getInt(GIPHY_CODE_KEY);

        // Get box opening sound from resources
        final MediaPlayer sound = MediaPlayer.create(this, R.raw.boxopening);

        // Delay for a few seconds than play move animations
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        sound.start();
                    }
                },
                600);


        // Bounce animation for box
        mAnimatorBounce = ObjectAnimator.ofFloat(mQuestionSquare, "translationY", BOUNCE_DIRECTION);
        mAnimatorBounce.setInterpolator(new BounceInterpolator());
        mAnimatorBounce.setStartDelay(500);
        mAnimatorBounce.setDuration(1500);
        mAnimatorBounce.start();

        // Loops bounce animation
        mAnimatorBounce.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorBounce.start();
            }
        });

        mQuestionSquare.setOnClickListener(v -> {
            Intent intent = new Intent(RandomGifAnimationActivity.this, DisplayGiphyActivity.class);
            intent.putExtra(GIPHY_CODE_KEY, mRetrievedCode);
            startActivity(intent);
            finish();
        });
    }
}
