package com.example.islamiccompass.main;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dagger.hilt.android.HiltAndroidApp;

/**
 * Storing data in this class will give the entire application access to the data,
 * through the following way
 * MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
 * use the mApplication varaible with a getMethod will return the desired data.
 */
@HiltAndroidApp
public class MyApplication extends Application {

    private static MyApplication singleton;

    public MyApplication getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
