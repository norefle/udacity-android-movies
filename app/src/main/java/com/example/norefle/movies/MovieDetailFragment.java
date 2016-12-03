package com.example.norefle.movies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class MovieDetailFragment extends Fragment {

    private static final String ARGUMENT_KEY = "Movie";

    public static MovieDetailFragment newInstance(Serializable movie) {
        final MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_KEY, movie);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        final Movie movie = (Movie) getArguments().getSerializable(ARGUMENT_KEY);

        ImageView poster = (ImageView) root.findViewById(R.id.detailed_poster_image);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185" + movie.poster).into(poster);

        TextView description = (TextView) root.findViewById(R.id.detailed_description);
        description.setText(movie.description);

        TextView releaseDate = (TextView) root.findViewById(R.id.detailed_year);
        releaseDate.setText(Long.toString(1900 + movie.release.getYear()));

        TextView rating = (TextView) root.findViewById(R.id.detailed_rating);
        rating.setText(Double.toString(movie.rate) + " / 10");

        getActivity().setTitle(movie.title);

        return root;
    }
}
