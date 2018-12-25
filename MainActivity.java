package com.example.emdb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Executes getIMDbCode()
 */

public class MainActivity extends AppCompatActivity {

    public static ImageView searchButton;
    public static ImageView loading;
    public static EditText searchText;
    public static String IMDBCode;
    public static Context mContext;
    private ImageView github;

    public static AnimationDrawable animationDrawable;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        searchButton = findViewById(R.id.searchButton);
        searchText = findViewById(R.id.searchText);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchButton.performClick();
                return false;
            }
        });

        github = findViewById(R.id.github);

        loading = findViewById(R.id.image_loading);
        loading.setBackgroundResource(R.drawable.loading);
        animationDrawable = (AnimationDrawable) loading.getBackground();

        mContext = MainActivity.this;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                animationDrawable.start();
                new getIMDbCode(MainActivity.this).execute();
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/adarshpunj/EMDb")));
            }
        });

    }
}
