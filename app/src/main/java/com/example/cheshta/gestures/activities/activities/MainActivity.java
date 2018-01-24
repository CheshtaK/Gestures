package com.example.cheshta.gestures.activities.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cheshta.gestures.R;
import com.example.cheshta.gestures.activities.activities.AnimationDemoActivity;
import com.example.cheshta.gestures.activities.activities.GestureDemoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLaunchDemo(View v){
        if(v.getId() == R.id.btnAnimationDemo){
            startActivity(new Intent(this, AnimationDemoActivity.class));
        } else {
            startActivity(new Intent(this, GestureDemoActivity.class));
        }
    }
}
