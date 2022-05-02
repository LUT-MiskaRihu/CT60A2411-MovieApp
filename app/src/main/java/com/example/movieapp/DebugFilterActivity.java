package com.example.movieapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DebugFilterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_filter);
        TextView textView = findViewById(R.id.textView);
        textView.setText(Filter.getInstance().toString());
    }
}