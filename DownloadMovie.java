package com.example.emdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Executed by the download button of MoviePage Activity
 * Sets up downloadlink using main server
 * Produces a Snackbar -- Sends request to downloadlink upon positive response from user
 * Triggers WebActivity()
 */

public class DownloadMovie extends AsyncTask<Void, Integer, Void> {

    String data = "";
    public static String size;
    public static String movieName;

    Context context;
    public DownloadMovie(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            MoviePage.downloadLink = JSONParser("link");
            String movieName = JSONParser("name");
            size = JSONParser("size");

            View view = ((Activity) MoviePage
                    .mContext)
                    .getWindow()
                    .getDecorView()
                    .findViewById(R.id.relativeLayout);

            Snackbar.make(view,"Download: "+movieName+"?",Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(MoviePage.mContext, WebActivity.class));
                        }
                    }).setActionTextColor(ContextCompat.getColor(context, R.color.colorBlue)).show();

        } catch (JSONException e) {
            Toast.makeText(context,"Something went wrong. Please try again after sometime.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... voids) {
            try {
                String request = MoviePage.mTitle;
                request = request.replaceAll("\\s","+");
                request = request.replaceAll("/","+");
                URL url = new URL("http://adarshpunj.pythonanywhere.com/movie/"+request);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    data = data + line;
                }
            }catch (MalformedInputException e){
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
    }

    public String JSONParser(String jSONValue) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        String value = jsonObject.getString(jSONValue);
        return value;
    }
}
