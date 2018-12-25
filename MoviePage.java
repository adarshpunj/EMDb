package com.example.emdb;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity that contains movie information
 * Has a empty string downloadlink
 * Contains download button (without any reference)
 * Download button triggers DownloadMovie()
 */

public class MoviePage extends AppCompatActivity {

    public static TextView title;
    public static TextView rating;
    public static TextView genre;
    public static TextView runtime;
    public static ImageView poster;

    public static String downloadLink;
    public static String mTitle;

    public static Context mContext;

    private static ImageView download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        getSupportActionBar().hide();

        String mRating;
        String mGenre;
        String mRuntime;

        mContext = MoviePage.this;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mTitle = null;
                mRating = null;
                mGenre = null;
                mRuntime = null;

            } else {
                mTitle= extras.getString("TITLE");
                mRating = extras.getString("RATING");
                mGenre = extras.getString("GENRE");
                mRuntime = extras.getString("RUNTIME");
            }
        } else {
            mTitle = (String) savedInstanceState.getSerializable("TITLE");
            mRating = (String) savedInstanceState.getSerializable("RATING");
            mGenre = (String) savedInstanceState.getSerializable("GENRE");
            mRuntime = (String) savedInstanceState.getSerializable("RUNTIME");
        }

        title   = findViewById(R.id.movie_title);
        rating  = findViewById(R.id.rating);
        genre   = findViewById(R.id.genre);
        runtime = findViewById(R.id.runtime);
        poster = findViewById(R.id.poster);
        download = findViewById(R.id.download);

        title.setText(mTitle);
        rating.setText(mRating);
        genre.setText(mGenre);
        runtime.setText(mRuntime);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MoviePage.mContext,"Preparing Link",Toast.LENGTH_SHORT).show();
                new DownloadMovie(MoviePage.this).execute();
            }
        });
    }
}
