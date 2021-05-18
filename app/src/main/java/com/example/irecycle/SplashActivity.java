package com.example.irecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    AppPreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new AppPreferenceManager(this);

        if (preferenceManager.getDarkModeState())
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}