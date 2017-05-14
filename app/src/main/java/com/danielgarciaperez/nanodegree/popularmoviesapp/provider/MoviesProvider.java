package com.danielgarciaperez.nanodegree.popularmoviesapp.provider;

import com.danielgarciaperez.nanodegree.popularmoviesapp.adapter.MoviesAdapter;
import com.danielgarciaperez.nanodegree.popularmoviesapp.model.Movie;

import java.io.IOException;

/**
 * Created by danielgarciaperez on 09/05/2017.
 */

public interface MoviesProvider {

    enum Order {
        POPULAR, RATED
    }

    Movie getMovieAtPosition(int position);

    int getNumMovies();

    void loadMovies(Order order) throws IOException;

    void setAdapter(MoviesAdapter adapter);

}
