package com.example.norefle.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String ARGUMENT_KEY = "Movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_movie_detail, MovieDetailFragment.newInstance(getIntent().getSerializableExtra(ARGUMENT_KEY)))
                    .commit();
        }
    }
}
