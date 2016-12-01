package com.example.norefle.movies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies_list, container, false);

        posterAdapter = new PostersAdapter(getActivity(), new ArrayList<Movie>());
        GridView movies = (GridView) root.findViewById(R.id.movies_collection_view);
        movies.setAdapter(posterAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        requestTask.execute(Uri.parse("https://api.themoviedb.org/3/movie/top_rated?api_key=&language=en-US&page=1"));
    }

    @Override
    public void accept(List<Movie> movies) {
        posterAdapter.addAll(movies);
    }
}
