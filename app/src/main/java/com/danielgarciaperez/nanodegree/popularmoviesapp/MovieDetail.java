package com.danielgarciaperez.nanodegree.popularmoviesapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielgarciaperez.nanodegree.popularmoviesapp.databinding.MovieDetailBinding;
import com.danielgarciaperez.nanodegree.popularmoviesapp.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by danielgarciaperez on 13/05/2017.
 */

public class MovieDetail extends AppCompatActivity {

    public static final String MOVIE = "movie";

    private MovieDetailBinding movieDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        movieDetailBinding = DataBindingUtil.setContentView(this, R.layout.movie_detail);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Gson gson = new Gson();
        Movie movie = gson.fromJson(extras.getString(MOVIE), Movie.class);

        fillView(movie);
    }

    private void fillView(Movie movie) {
        movieDetailBinding.title.setText(movie.getTitle());
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185/"+movie.getPosterPath()).into(movieDetailBinding.poster);
        movieDetailBinding.date.setText(movie.getReleaseDate().substring(0,4));
        movieDetailBinding.vote.setText(movie.getVoteAverage()+"/10");
        movieDetailBinding.synopsis.setText(movie.getOverview());
    }
}
