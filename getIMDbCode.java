package com.example.emdb;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Called by MainActivity (Upon SEARCH)
 * Gets IMDb code from main server
 * Executes getMovie() using the IMDb code
 */

public class getIMDbCode extends AsyncTask<Void,Integer,Void> {

    public static String data = "";


    Context context;
    public getIMDbCode(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoids) {
        super.onPostExecute(aVoids);

        if(data==""){
            MainActivity.animationDrawable.stop();
            MainActivity.loading.setVisibility(View.INVISIBLE);
            Toast.makeText(context,"Please enter a movie title",Toast.LENGTH_SHORT).show();
        }else {
            MainActivity.IMDBCode = data;
            data = "";
            new getMovie(context).execute(MainActivity.IMDBCode);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String userInput = String.valueOf(MainActivity.searchText.getText());
        if(userInput==null){
            return null;
        }
        userInput = userInput.replaceAll("\\s","+");
        userInput = "http://adarshpunj.pythonanywhere.com/imdbcode/"+userInput;
        sendRequests(userInput);
        return null;
    }

    public void sendRequests(String userInput){
        try {
            URL url = new URL(userInput);
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
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
