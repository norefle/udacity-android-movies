package com.example.norefle.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment {

    private static final String ARGUMENT_KEY = "Movie";

    @BindView(R.id.detailed_poster_image)
    ImageView poster;

    @BindView(R.id.detailed_description)
    TextView description;

    @BindView(R.id.detailed_year)
    TextView releaseDate;

    @BindView(R.id.detailed_rating)
    TextView rating;

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
        ButterKnife.bind(this, root);

        final Movie movie = (Movie) getArguments().getSerializable(ARGUMENT_KEY);

        updatePoster(movie);
        updateDescription(movie);
        updateReleaseDate(movie);
        updateRating(movie);
        updateTitle(movie);

        return root;
    }

    private void updatePoster(Movie movie) {
        final Context context = getContext();
        if (context != null) {
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185" + movie.poster)
                    .placeholder(R.mipmap.poster_placeholder)
                    .error(R.mipmap.poster_error)
                    .into(poster);
        }
    }

    private void updateDescription(Movie movie) {
        description.setText(movie.description);
    }

    private void updateReleaseDate(Movie movie) {
        releaseDate.setText(
                String.format(
                        getString(R.string.year_of_release_pattern),
                        1900 + movie.release.getYear()
                )
        );
    }

    private void updateRating(Movie movie) {
        rating.setText(
                String.format(
                        getString(R.string.rating_pattern),
                        movie.rate,
                        10.0
                )
        );
    }

    private void updateTitle(Movie movie) {
        getActivity().setTitle(movie.title);
    }
}
