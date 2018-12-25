package com.example.emdb;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    public static WebView webView;
    public static ImageView loading;
    public TextView size;
    public AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getSupportActionBar().hide();

        loading = findViewById(R.id.image_loading);
        loading.setBackgroundResource(R.drawable.loading);
        animationDrawable = (AnimationDrawable) loading.getBackground();

        webView = findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(MoviePage.downloadLink);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                requestPermission();
            }
        });
        webView.setBackgroundColor(Color.BLACK);
        size = findViewById(R.id.size);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animationDrawable.start();
    }

    public void requestPermission(){
        ActivityCompat
                .requestPermissions(WebActivity.this,
                        new String[]
                                {Manifest
                                        .permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(MoviePage.downloadLink));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "EMDb: "+MoviePage.mTitle+".mp4");
//                    request.addRequestHeader("Content-Length","704000000");
                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                    loading.setVisibility(View.INVISIBLE);
                    animationDrawable.stop();

                    Toast.makeText(WebActivity.this, "The downloaded has started",Toast.LENGTH_SHORT).show();
                    size.setText("File Size: "+DownloadMovie.size+"\n"+"Though you might not see any visible progress,"+"\n"+"the file is still getting downloaded, and you would"+"\n"+"eventually find it in your Downloads directory. ");

                } else {
                    Snackbar.
                            make(findViewById(R.id.layout)
                                    ,"This permission will ONLY give EMDb the access to write files."
                                    ,Snackbar.LENGTH_INDEFINITE)
                            .setAction("REQUEST", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    requestPermission();
                                }
                            }).setActionTextColor(ContextCompat.getColor(this, R.color.colorBlue)).show(); }
            }
        }
    }
}

