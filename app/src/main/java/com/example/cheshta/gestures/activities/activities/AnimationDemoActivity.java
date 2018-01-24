package com.example.cheshta.gestures.activities.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cheshta.gestures.R;

public class AnimationDemoActivity extends AppCompatActivity {

    Button btnFadeOut, btnRepeat;
    TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_demo);
        
        btnFadeOut = findViewById(R.id.btnFadeOut);
        btnRepeat = findViewById(R.id.btnRepeat);
        tvMessage = findViewById(R.id.tvMessage);
    }

    public void onFadeOut(View v){
        btnFadeOut.animate().alpha(0).setDuration(2000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                btnFadeOut.animate().alpha(1f).setDuration(2000);
            }
        });
    }

    public void onSlideMessage(View v){
        tvMessage.setTranslationY(tvMessage.getHeight());
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.animate().translationY(0).setDuration(2000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tvMessage.animate().translationY(tvMessage.getHeight()).setDuration(2000).setStartDelay(2000);
            }
        });
    }

    public void onRepeatAnimation(View v){
        ObjectAnimator scaleAnimY = ObjectAnimator.ofFloat(btnRepeat, "scaleY", 1.0f, 1.5f);
        scaleAnimY.setDuration(3000);
        scaleAnimY.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnimY.setRepeatMode(ValueAnimator.REVERSE);

        ObjectAnimator scaleAnimX = ObjectAnimator.ofFloat(btnRepeat, "scaleX", 1.0f, 1.5f);
        scaleAnimX.setDuration(3000);
        scaleAnimX.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnimX.setRepeatMode(ValueAnimator.REVERSE);
        // Setup animation set
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleAnimX, scaleAnimY);
        set.start();
    }

    public void onActivityTransition(View v){
        Intent i = new Intent(AnimationDemoActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public int getScreenHeight(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
