package com.example.irecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.irecycle.databinding.ActivitySabiasQueBinding;

public class SabiasQueActivity extends AppCompatActivity {

    ActivitySabiasQueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySabiasQueBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.layoutSabias1.setOnClickListener(view1 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias2.setOnClickListener(view2 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias3.setOnClickListener(view3 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias4.setOnClickListener(view4 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias5.setOnClickListener(view5 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias6.setOnClickListener(view6 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias7.setOnClickListener(view7 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias8.setOnClickListener(view8 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias9.setOnClickListener(view9 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias10.setOnClickListener(view10 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

        binding.layoutSabias11.setOnClickListener(view11 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.temasambientales.com/2017/03/curiosidades-medio-ambiente.html"));
            startActivity(browserIntent);
        });

    }




    public void onBackHomeSB(View view){
        startActivity(new Intent(this,HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
    }
}