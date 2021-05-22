package com.example.irecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class VideosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
    }
    public void onBackHome(View view){
        startActivity(new Intent(this,HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
    }
}