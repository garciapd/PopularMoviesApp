package com.danielgarciaperez.nanodegree.popularmoviesapp.provider.db;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.danielgarciaperez.nanodegree.popularmoviesapp.MovieDetail;
import com.danielgarciaperez.nanodegree.popularmoviesapp.MovieLoaderListener;
import com.danielgarciaperez.nanodegree.popularmoviesapp.data.MovieContract;
import com.danielgarciaperez.nanodegree.popularmoviesapp.databinding.MovieDetailBinding;
import com.danielgarciaperez.nanodegree.popularmoviesapp.model.Movie;
import com.danielgarciaperez.nanodegree.popularmoviesapp.provider.MoviesProvider;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

/**
 * Created by danielgarciaperez on 17/05/2017.
 */

public class MoviesProviderDB implements MoviesProvider, LoaderManager.LoaderCallbacks<Cursor>{

    private MovieLoaderListener listener;
    private LoaderManager loaderManager;
    private Context context;

    private static final int TASK_LOADER_ID = 1;

    private Cursor cursor;

    private Gson gson = new Gson();

    private int previousPosition = 0;

    public MoviesProviderDB(LoaderManager loaderManager, Context context, MovieLoaderListener listener){
        this.listener = listener;
        this.loaderManager = loaderManager;
        this.context = context;
    }

    @Override
    public Movie getMovieAtPosition(int position) {
        if(cursor == null) return null;
        if(position >=cursor.getCount()) {
            return null;
        }
        cursor.moveToPosition(position);
        return gson.fromJson(cursor.getString(1), Movie.class);
    }

    @Override
    public int getNumMovies() {
        if(cursor == null) return 0;
        return cursor.getCount();
    }

    @Override
    public void loadMovies(int position, Order order) throws IOException {
        this.previousPosition = position;
        this.loaderManager.initLoader(TASK_LOADER_ID, null, MoviesProviderDB.this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this.context, MovieContract.MovieEntry.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void swapCursor(Cursor newCursor) {
        if (newCursor != null) {
            cursor = newCursor;
            listener.onMovieLoaded(this.previousPosition);
        }
    }

}
