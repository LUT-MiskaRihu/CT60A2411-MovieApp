package com.example.movieapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ApplicationContext.setContext(getApplicationContext());

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMainActivity();
            }
        }, 500);
    }

    private void loadMainActivity() {
        Database.getInstance();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        }, 500);
    }
}