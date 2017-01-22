package com.example.norefle.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MoviesListFragment
        extends Fragment
        implements MoviesRequester.Subscriber, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String BASIC_URL = "https://api.themoviedb.org/3/movie";
    private static final String TOP_RATED_KEY = "top_rated";
    private static final String POPULAR_KEY = "popular";
    private static final String LANGUAGE_KEY = "language";
    private static final String LANGUAGE_VALUE = "en-US";
    private static final String API_KEY = "api_key";
    private static final String PAGE_KEY = "page";

    private boolean byPopularity;
    private PostersAdapter posterAdapter;
    private MoviesRequester requestTask;

    public MoviesListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies_list, container, false);

        posterAdapter = new PostersAdapter(getActivity(), new ArrayList<>());
        GridView movies = (GridView) root.findViewById(R.id.movies_collection_view);
        movies.setAdapter(posterAdapter);

        movies.setOnItemClickListener((adapter, view, position, l) -> showMovieInfo(position));

        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);

        byPopularity = byPopularity();
        requestMovies(1);

        return root;
    }

    @Override
    public void accept(List<Movie> movies, int current, int total) {
        posterAdapter.addAll(movies);
        if (current < total) {
            requestMovies(current + 1);
        }
    }

    private void showMovieInfo(int position) {
        final Movie movie = posterAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.ARGUMENT_KEY, movie);

        startActivity(intent);
    }

    private boolean online() {
        final Context context = getContext();
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

        return false;
    }

    private boolean byPopularity() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String popularity = prefs.getString(
                getContext().getString(R.string.sort_settings_menu),
                getContext().getString(R.string.by_popularity_sort));

        return popularity.equals(getContext().getString(R.string.by_popularity_sort));
    }

    private void requestMovies(int nextPage) {
        if (!online()) {
            Log.w(getClass().getName(), "There is no network connection to begin with.");
            return;
        }

        requestTask = new MoviesRequester(this, nextPage);
        requestTask.execute(
                Uri.parse(BASIC_URL)
                        .buildUpon()
                        .appendPath(byPopularity ? POPULAR_KEY : TOP_RATED_KEY)
                        .appendQueryParameter(LANGUAGE_KEY, LANGUAGE_VALUE)
                        .appendQueryParameter(API_KEY, BuildConfig.MOVIES_DB_API_KEY)
                        .appendQueryParameter(PAGE_KEY, Integer.toString(nextPage))
                        .build()
        );
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        final boolean newByPopularity = byPopularity();
        if (byPopularity != newByPopularity) {
            byPopularity = newByPopularity;
            requestTask.cancel(true);
            posterAdapter.clear();
            requestMovies(1);
        }
    }
}
