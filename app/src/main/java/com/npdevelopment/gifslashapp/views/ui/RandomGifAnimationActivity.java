package com.npdevelopment.gifslashapp.views.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.npdevelopment.gifslashapp.R;

import static com.npdevelopment.gifslashapp.views.ui.MainActivity.GIPHY_CODE_KEY;

public class RandomGifAnimationActivity extends AppCompatActivity {

    private final int BOUNCE_DIRECTION = -100;

    private ImageView logoPartOne, logoPartTwo, logoPartThree, logoPartFour, questionSquare;
    private ObjectAnimator animatorBounce, animationSlide;

    private int retrievedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_gif_animation);

        getSupportActionBar().hide();

        logoPartOne = findViewById(R.id.iv_logo_part_one);
        logoPartTwo = findViewById(R.id.iv_logo_part_two);
        logoPartThree = findViewById(R.id.iv_logo_part_three);
        logoPartFour = findViewById(R.id.iv_logo_part_four);
        questionSquare = findViewById(R.id.iv_question_square);

        // Get the passed code to determine if its a gif or sticker
        retrievedCode = getIntent().getExtras().getInt(GIPHY_CODE_KEY);

        // G
        final MediaPlayer sound = MediaPlayer.create(this, R.raw.boxopening);

        // Get width of the display
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        // Get width of one of the images
        final int imageWidth = logoPartOne.getDrawable().getIntrinsicWidth();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Delay for a few seconds than play move animations
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            sound.start();
                            moveImageView(logoPartOne, width - (width + imageWidth));
                            moveImageView(logoPartTwo, width + (imageWidth / 2));
                            moveImageView(logoPartThree, width - (width + imageWidth));
                            moveImageView(logoPartFour, width + (imageWidth / 2));
                        }
                    },
                    1100);
        } else {
            logoPartOne.setVisibility(View.GONE);
            logoPartTwo.setVisibility(View.GONE);
            logoPartThree.setVisibility(View.GONE);
            logoPartFour.setVisibility(View.GONE);
        }

        // Bounce animation for box
        animatorBounce = ObjectAnimator.ofFloat(questionSquare, "translationY", BOUNCE_DIRECTION);
        animatorBounce.setInterpolator(new BounceInterpolator());
        animatorBounce.setStartDelay(500);
        animatorBounce.setDuration(1500);
        animatorBounce.start();

        // Loops bounce animation
        animatorBounce.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorBounce.start();
            }
        });

        questionSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomGifAnimationActivity.this, DisplayGiphyActivity.class);
                intent.putExtra(GIPHY_CODE_KEY, retrievedCode);
                startActivity(intent);
                finish();
            }
        });
    }

    private void moveImageView(View view, float xPosition) {
        animationSlide = ObjectAnimator.ofFloat(view, "X", xPosition);
        animationSlide.setDuration(2500);
        animationSlide.start();
    }
}
