package com.example.norefle.movies;

import org.json.JSONException;
import org.json.JSONObject;

class Movie {
    final String title;
    final String poster;
    final double popularity;
    final double rate;

    Movie(String title, String poster, double popularity, double rate) {
        this.title = title;
        this.poster = poster;
        this.popularity = popularity;
        this.rate = rate;
    }

    static Movie parse(JSONObject document) throws JSONException {
        return new Movie(
                document.getString("original_title"),
                document.getString("poster_path"),
                document.getDouble("popularity"),
                document.getDouble("vote_average")
        );
    }
}
