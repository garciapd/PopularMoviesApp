package com.danielgarciaperez.nanodegree.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.danielgarciaperez.nanodegree.popularmoviesapp.adapter.MoviesAdapter;
import com.danielgarciaperez.nanodegree.popularmoviesapp.model.Movie;
import com.danielgarciaperez.nanodegree.popularmoviesapp.provider.MoviesProvider;
import com.danielgarciaperez.nanodegree.popularmoviesapp.provider.http.MoviesProviderHttp;

import java.io.IOException;

/**
 * Created by danielgarciaperez on 02/05/2017.
 */

public class MoviesMain extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MoviesProvider provider;

    private MoviesProvider.Order order = MoviesProvider.Order.POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_layout);

        recyclerView = (RecyclerView) findViewById(R.id.main_view);

        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        provider = new MoviesProviderHttp();

        adapter = new MoviesAdapter(provider, this);
        provider.setAdapter(adapter);
        recyclerView.setAdapter(adapter);

        loadMovies(order);
    }


    private void loadMovies(MoviesProvider.Order order) {
        try {
            provider.loadMovies(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular) {
            order = MoviesProvider.Order.POPULAR;
            getSupportActionBar().setTitle(R.string.app_name);
            loadMovies(order);
            return true;
        }
        if (id == R.id.rated) {
            order = MoviesProvider.Order.RATED;
            getSupportActionBar().setTitle(R.string.app_name_rated);
            loadMovies(order);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void movieClick(View v) {
        Intent movieDetailIntent = new Intent(MoviesMain.this, MovieDetail.class);
        int pos = (int) v.getTag();
        Movie selectedMovie = provider.getMovieAtPosition(pos);
        movieDetailIntent.putExtra(MovieDetail.TITLE, selectedMovie.getTitle());
        movieDetailIntent.putExtra(MovieDetail.DATE, selectedMovie.getReleaseDate());
        movieDetailIntent.putExtra(MovieDetail.POSTER, selectedMovie.getPosterPath());
        movieDetailIntent.putExtra(MovieDetail.VOTE, selectedMovie.getVoteAverage().toString());
        movieDetailIntent.putExtra(MovieDetail.SYNOPSIS, selectedMovie.getOverview());
        startActivity(movieDetailIntent);
    }
}
