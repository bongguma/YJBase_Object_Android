package com.example.yjbase_object_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}