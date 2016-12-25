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
        void accept(List<Movie> movies, int current, int total);
    }

    private static final String RESULTS_KEY = "results";
    private static final String PAGES_KEY = "total_pages";
    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "original_title";
    private static final String OVERVIEW_KEY = "overview";
    private static final String RELEASE_KEY = "release_date";
    private static final String POSTER_KEY = "poster_path";
    private static final String POPULARITY_KEY = "popularity";
    private static final String VOTE_KEY = "vote_average";

    private final Subscriber subscriber;
    private final int currentPage;
    private int pagesTotal;

    MoviesRequester(Subscriber subscriber, int currentPage) {
        this.subscriber = subscriber;
        this.currentPage = currentPage;
        this.pagesTotal = 0;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        subscriber.accept(movies, currentPage, pagesTotal);
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
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return result;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
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
        final JSONArray movies = document.getJSONArray(RESULTS_KEY);
        pagesTotal = document.getInt(PAGES_KEY);
        for (int index = 0; index < movies.length(); ++index) {
            result.add(extractMovie(movies.getJSONObject(index)));
        }

        return result;
    }

    private Movie extractMovie(JSONObject document) throws JSONException {
        final String releaseDate = document.getString(RELEASE_KEY);
        return new Movie(
                document.getInt(ID_KEY),
                document.getString(TITLE_KEY),
                document.getString(OVERVIEW_KEY),
                releaseDate.isEmpty() ? Date.valueOf("1970-01-01") : Date.valueOf(releaseDate),
                document.getString(POSTER_KEY),
                document.getDouble(POPULARITY_KEY),
                document.getDouble(VOTE_KEY)
        );
    }
}
