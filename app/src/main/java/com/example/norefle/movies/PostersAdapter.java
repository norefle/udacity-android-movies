package com.example.norefle.movies;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

class PostersAdapter extends ArrayAdapter<Uri> {
    public PostersAdapter(Activity context, List<Uri> posters) {
        super(context, 0, posters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Uri posterUri = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }

        ImageView posterImage = (ImageView) convertView.findViewById(R.id.movie_poster_image);
        Picasso.with(getContext()).load(posterUri).into(posterImage);

        return convertView;
    }
}
