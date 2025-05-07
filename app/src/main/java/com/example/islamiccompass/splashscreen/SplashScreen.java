package com.example.islamiccompass.splashscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.islamiccompass.main.MainActivity;
import com.example.islamiccompass.R;

public class SplashScreen extends AppCompatActivity {
    private LottieAnimationView lottie_main;
    private RelativeLayout splashscreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isMorningMode= prefs.getBoolean("isMorningMode", false);
        System.out.println("isMorningMode: " + isMorningMode);

        lottie_main = (LottieAnimationView) findViewById(R.id.lottie_main);
        splashscreen = (RelativeLayout) findViewById(R.id.splashcreen);
        lottie_main.setAnimation("RippleGold.json");

        if(isMorningMode) splashscreen.setBackgroundColor(Color.parseColor("#F5F5F5"));
        else splashscreen.setBackgroundColor(Color.parseColor("#000000"));



        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 2000);

    }
}