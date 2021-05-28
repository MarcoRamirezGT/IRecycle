package com.example.irecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LatasActivity extends AppCompatActivity {

    Button continueReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latas);

        continueReading =  findViewById(R.id.buttonReading);

        continueReading.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.renovablesverdes.com/reciclar-latas/"));
            startActivity(browserIntent);
        });
    }

    public void onBackHome(View view){
        startActivity(new Intent(this, MaterialesActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
    }
}