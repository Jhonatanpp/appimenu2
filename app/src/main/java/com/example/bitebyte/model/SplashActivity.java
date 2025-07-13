package com.example.bitebyte.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.bitebyte.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logoImage);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce_up);
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale_up);

        logo.startAnimation(fadeIn);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.startAnimation(bounce);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        bounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                logo.startAnimation(scale);
            }

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 3000);
    }
}