package com.example.irecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MaterialesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiales);
    }

    public void onBackHome(View view){
        startActivity(new Intent(this,HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
    }

    public void onPlasticos(View view){
        startActivity(new Intent(this,PlasticosActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void onPapel(View view){
        startActivity(new Intent(this, PapelActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void onVidrio(View view){
        startActivity(new Intent(this, VidrioActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void onLatas(View view){
        startActivity(new Intent(this, LatasActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void onTetra(View view){
        startActivity(new Intent(this, TetraActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }


    public void onElectronicos(View view){
        startActivity(new Intent(this, ElectronicosActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}