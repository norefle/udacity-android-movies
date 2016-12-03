package com.example.norefle.movies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

class MoviesRequester extends AsyncTask<Uri, Void, List<Movie>> {
    interface Subscriber {
        void accept(List<Movie> movies);
    }

    private Subscriber subscriber;

    MoviesRequester(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        subscriber.accept(movies);
    }

    @Override
    protected List<Movie> doInBackground(Uri... addresses) {
        List<Movie> result = new ArrayList<>();

        try {
            for (Uri address : addresses) {
                URL fullAddress = new URL(address.toString());
                HttpURLConnection request = (HttpURLConnection) fullAddress.openConnection();
                request.setRequestMethod("GET");
                request.connect();

                // Read the input stream into a String
                InputStream inputStream = request.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return result;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return result;
                }

                result.addAll(parse(buffer.toString()));
            }
        } catch (IOException e) {
            Log.e(getClass().getName(), "Failed to request movies:" + e.getMessage());
        } catch (JSONException e) {
            Log.e(getClass().getName(), "Failed to parse response with movies:" + e.getMessage());
        }

        return result;
    }

    private List<Movie> parse(String jsonAsString) throws JSONException {
        List<Movie> result = new ArrayList<>();
        final JSONObject document = new JSONObject(jsonAsString);
        final JSONArray movies = document.getJSONArray("results");
        for (int index = 0; index < movies.length(); ++index) {
            result.add(extractMovie(movies.getJSONObject(index)));
        }

        return result;
    }

    private Movie extractMovie(JSONObject document) throws JSONException {
        return new Movie(
                document.getInt("id"),
                document.getString("original_title"),
                document.getString("overview"),
                Date.valueOf(document.getString("release_date")),
                document.getString("poster_path"),
                document.getDouble("popularity"),
                document.getDouble("vote_average")
        );
    }
}
