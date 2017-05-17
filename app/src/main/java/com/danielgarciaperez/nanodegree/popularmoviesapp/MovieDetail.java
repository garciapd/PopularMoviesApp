package com.danielgarciaperez.nanodegree.popularmoviesapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielgarciaperez.nanodegree.popularmoviesapp.data.MovieContract;
import com.danielgarciaperez.nanodegree.popularmoviesapp.databinding.MovieDetailBinding;
import com.danielgarciaperez.nanodegree.popularmoviesapp.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by danielgarciaperez on 13/05/2017.
 */

public class MovieDetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String MOVIE = "movie";

    private static final int TASK_LOADER_ID = 0;
    private static final String[] PROJECTION = new String[] { "_id" };

    private MovieDetailBinding movieDetailBinding;

    private Movie movie;

    private String movieJson;

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        movieDetailBinding = DataBindingUtil.setContentView(this, R.layout.movie_detail);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        movieJson = extras.getString(MOVIE);
        Gson gson = new Gson();
        movie = gson.fromJson(movieJson, Movie.class);

        isFavorite = false;
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, MovieDetail.this);

        fillView(movie);
    }

    private void fillView(Movie movie) {
        movieDetailBinding.title.setText(movie.getTitle());
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185/"+movie.getPosterPath()).into(movieDetailBinding.poster);
        movieDetailBinding.date.setText(movie.getReleaseDate().substring(0,4));
        movieDetailBinding.vote.setText(movie.getVoteAverage()+"/10");
        movieDetailBinding.synopsis.setText(movie.getOverview());

        movieDetailBinding.button.setVisibility(View.INVISIBLE);
    }

    private void fillMarkFavoriteButton(boolean isFavorite){
        if(isFavorite){
            movieDetailBinding.button.setText(getString(R.string.markedFavorite));
        }else{
            movieDetailBinding.button.setText(getString(R.string.markFavorite));
        }

        movieDetailBinding.button.setVisibility(View.VISIBLE);
    }

    public void toogleFavorite(View v){
        String id = Integer.toString(movie.getId());
        Uri uri = MovieContract.MovieEntry.CONTENT_URI;


        if(isFavorite){
            uri = uri.buildUpon().appendPath(id).build();
            int count = getContentResolver().delete(uri, null, null);
        }else{
            ContentValues values = new ContentValues();
            values.put(MovieContract.MovieEntry._ID, id);
            values.put(MovieContract.MovieEntry.COLUMN_MOVIE, movieJson);
            Uri newUri = getContentResolver().insert(uri,values);
        }

        isFavorite = !isFavorite;
        fillMarkFavoriteButton(isFavorite);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = "_id=?";
        String[] selectionArgs = new String[]{movie.getId().toString()};
        return new CursorLoader(MovieDetail.this, MovieContract.MovieEntry.CONTENT_URI,
                PROJECTION, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        isFavorite = false;
        if(data != null && data.getCount() > 0){
            isFavorite = true;
        }
        fillMarkFavoriteButton(isFavorite);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }
}
