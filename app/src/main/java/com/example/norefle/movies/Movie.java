package com.example.norefle.movies;

import java.io.Serializable;
import java.util.Date;

class Movie implements Serializable {
    final int id;
    final String title;
    final String poster;
    final Date release;
    final String description;
    final double popularity;
    final double rate;

    Movie(int id,
          String title,
          String description,
          Date release,
          String poster,
          double popularity,
          double rate) {

        this.id = id;
        this.title = title;
        this. description = description;
        this.release = release;
        this.poster = poster;
        this.popularity = popularity;
        this.rate = rate;
    }
}
