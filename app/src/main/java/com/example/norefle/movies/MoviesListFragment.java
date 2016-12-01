package com.example.norefle.movies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Arrays;

public class MoviesListFragment extends Fragment {
    private PostersAdapter posterAdapter;

    public MoviesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Uri[] images = {
                Uri.parse("http://contentmarketinginstitute.com/wp-content/uploads/2015/08/original-jaws-poster-image-1A.png"),
                Uri.parse("http://graphicdesignjunction.com/wp-content/uploads/2011/12/grey-movie-poster.jpg"),
                Uri.parse("http://thumbs1.ebaystatic.com/images/g/pw4AAOSw4shX5zOd/s-l225.jpg"),
                Uri.parse("https://67.media.tumblr.com/f5e67c90ac9cec324ab80790088152e9/tumblr_of2srkrLLt1vzqmg1o1_400.gif"),
                Uri.parse("http://contentmarketinginstitute.com/wp-content/uploads/2015/08/original-jaws-poster-image-1A.png"),
                Uri.parse("http://graphicdesignjunction.com/wp-content/uploads/2011/12/grey-movie-poster.jpg"),
                Uri.parse("http://thumbs1.ebaystatic.com/images/g/pw4AAOSw4shX5zOd/s-l225.jpg"),
                Uri.parse("https://67.media.tumblr.com/f5e67c90ac9cec324ab80790088152e9/tumblr_of2srkrLLt1vzqmg1o1_400.gif"),
                Uri.parse("http://contentmarketinginstitute.com/wp-content/uploads/2015/08/original-jaws-poster-image-1A.png"),
                Uri.parse("http://graphicdesignjunction.com/wp-content/uploads/2011/12/grey-movie-poster.jpg"),
                Uri.parse("http://thumbs1.ebaystatic.com/images/g/pw4AAOSw4shX5zOd/s-l225.jpg"),
                Uri.parse("https://67.media.tumblr.com/f5e67c90ac9cec324ab80790088152e9/tumblr_of2srkrLLt1vzqmg1o1_400.gif")

        };

        View root = inflater.inflate(R.layout.fragment_movies_list, container, false);

        posterAdapter = new PostersAdapter(getActivity(), Arrays.asList(images));
        GridView movies = (GridView) root.findViewById(R.id.movies_collection_view);
        movies.setAdapter(posterAdapter);

        return root;
    }
}
