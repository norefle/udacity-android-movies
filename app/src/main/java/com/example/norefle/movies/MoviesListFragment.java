package com.example.norefle.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MoviesListFragment
        extends Fragment
        implements MoviesRequester.Subscriber {

    private PostersAdapter posterAdapter;
    private MoviesRequester requestTask;

    public MoviesListFragment() {
        requestTask = new MoviesRequester(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies_list, container, false);

        posterAdapter = new PostersAdapter(getActivity(), new ArrayList<>());
        GridView movies = (GridView) root.findViewById(R.id.movies_collection_view);
        movies.setAdapter(posterAdapter);

        movies.setOnItemClickListener((adapter, view, position, l) -> showMovieInfo(position));

        requestTask.execute(
                Uri.parse("https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1")
                        .buildUpon()
                        .appendQueryParameter("api_key", BuildConfig.MOVIES_DB_API_KEY)
                        .build()
        );

        return root;
    }

    @Override
    public void accept(List<Movie> movies) {
        posterAdapter.addAll(movies);
    }

    private void showMovieInfo(int position) {
        final Movie movie = posterAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.ARGUMENT_KEY, movie);

        startActivity(intent);
    }
}
