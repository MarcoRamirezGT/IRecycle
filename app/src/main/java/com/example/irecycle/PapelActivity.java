package com.example.irecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PapelActivity extends AppCompatActivity {

    Button continueReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papel);

        continueReading =  findViewById(R.id.buttonReading);

        continueReading.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.recytrans.com/blog/como-se-recicla-el-papel-y-carton/"));
            startActivity(browserIntent);
        });
    }

    public void onBackHome(View view){
        startActivity(new Intent(this, MaterialesActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
    }
}