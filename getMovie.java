package com.example.emdb;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Sends request to OMDb API (passes IMDb code)
 * Sets up the MoviePage with Title, Rating, Genre, etc
 * Executes DownloadPoster() that sets up poster
 */

public class getMovie extends AsyncTask<String,Integer,Void> {

    String data = "";
    Context context;
    public getMovie(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.loading.setVisibility(View.INVISIBLE);
        MainActivity.animationDrawable.stop();

        try {
            String title = JSONParser("Title");
            String rating = JSONParser("imdbRating");
            String genre = JSONParser("Genre");
            String runtime = JSONParser("Runtime");
            String posterURL = JSONParser("Poster");
            new DownloadPoster().execute(posterURL);
            Intent intent = new Intent(context, MoviePage.class);
            intent.putExtra("TITLE",title.toString());
            intent.putExtra("RATING",rating.toString());
            intent.putExtra("GENRE",genre.toString());
            intent.putExtra("RUNTIME",runtime.toString());
            intent.putExtra("POSTER_URL",posterURL);
            context.startActivity(intent);

        } catch (JSONException e) {
            Toast.
                makeText(context,"Looks like there's no movie named "
                +MainActivity.searchText.getText()
                .toString().toUpperCase()+". Please check for spelling errors.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Void doInBackground(String... strings) {
        String userInput = "http://www.omdbapi.com/?i="+strings[0]+"&apikey=5ded979c";
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

    public String JSONParser(String jSONValue) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        String value = jsonObject.getString(jSONValue);
        return value;
    }
}
