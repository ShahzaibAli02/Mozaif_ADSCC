package com.example.adscc.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adscc.R;

public class MainScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(MainScreen.this,Dashboard.class));
    }

    public void onClickHomeService(View view) {
        startActivity(new Intent(this,HomeService.class));
    }

    public void onClickRegister(View view) {

        startActivity(new Intent(this,Register_Covid.class));
    }

    public void onClickCheckResult(View view) {

        startActivity(new Intent(this,CheckResults.class));
    }
}