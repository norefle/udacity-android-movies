package com.example.norefle.movies;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

class PostersAdapter extends ArrayAdapter<Movie> {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/w185";

    PostersAdapter(Activity context, List<Movie> posters) {
        super(context, 0, posters);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }

        Movie movie = getItem(position);
        if (movie != null) {
            final Uri address = Uri.parse(BASE_URL + movie.poster);
            ImageView posterImage = (ImageView) convertView.findViewById(R.id.movie_poster_image);
            Picasso.with(getContext()).load(address).into(posterImage);
        }

        return convertView;
    }
}
