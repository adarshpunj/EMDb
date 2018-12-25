package com.example.emdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;

public class DownloadPoster extends AsyncTask<String,Void,Bitmap> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        try{
            MoviePage.poster.setImageBitmap(bitmap);
        }catch (Exception e){
            // Failed to load poster
        }
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String imageURL = strings[0];

        Bitmap bitmap = null;
        try {
            InputStream input = new java.net.URL(imageURL).openStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
