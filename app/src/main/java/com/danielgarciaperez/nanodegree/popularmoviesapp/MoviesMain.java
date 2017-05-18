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
import com.danielgarciaperez.nanodegree.popularmoviesapp.provider.db.MoviesProviderDB;
import com.danielgarciaperez.nanodegree.popularmoviesapp.provider.http.MoviesProviderHttp;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by danielgarciaperez on 02/05/2017.
 */

public class MoviesMain extends AppCompatActivity implements MovieLoaderListener{

    private static final String LIFECYCLE_CURRENT_MOVIE_TEXT_KEY = "current-movie";
    private static final String LIFECYCLE_CURRENT_ORDER_TEXT_KEY = "current-order";

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MoviesProvider provider;

    private MoviesProvider.Order order = MoviesProvider.Order.POPULAR;

    private Gson gson;
    private boolean moveToPosition = false;
    private int lastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.gson = new Gson();
        setContentView(R.layout.activity_movies_layout);

        recyclerView = (RecyclerView) findViewById(R.id.main_view);

        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState != null &&
                savedInstanceState.containsKey(LIFECYCLE_CURRENT_MOVIE_TEXT_KEY) &&
                savedInstanceState.containsKey(LIFECYCLE_CURRENT_ORDER_TEXT_KEY)) {
            int previousPosition = savedInstanceState.getInt(LIFECYCLE_CURRENT_MOVIE_TEXT_KEY);
            this.order = MoviesProvider.Order.valueOf(savedInstanceState.getString(LIFECYCLE_CURRENT_ORDER_TEXT_KEY));
            moveToPosition = true;

            initProviders(order);
            loadMovies(previousPosition, order);
        }else
        {
            initProviders(order);
            loadMovies(this.lastPosition, order);
            moveToPosition = false;
        }

        super.onCreate(savedInstanceState);
    }

    private void initProviders(MoviesProvider.Order order){
        if(order == MoviesProvider.Order.FAVORITE){
            provider = new MoviesProviderDB(getSupportLoaderManager(), this, this);
        }else{
            provider = new MoviesProviderHttp(this);
        }
        adapter = new MoviesAdapter(provider, this);
        recyclerView.setAdapter(adapter);
    }


    private void loadMovies(int position, MoviesProvider.Order order) {
        try {
            provider.loadMovies(position, order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMovieLoaded(int position){
        adapter.notifyDataSetChanged();
        if(moveToPosition) {
            recyclerView.scrollToPosition(position);
            moveToPosition = false;
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
            moveToPosition = true;
            initProviders(order);
            loadMovies(0, order);
            return true;
        }
        if (id == R.id.rated) {
            order = MoviesProvider.Order.RATED;
            getSupportActionBar().setTitle(R.string.app_name_rated);
            moveToPosition = true;
            initProviders(order);
            loadMovies(0, order);
            return true;
        }
        if (id == R.id.favorite) {
            order = MoviesProvider.Order.FAVORITE;
            getSupportActionBar().setTitle(R.string.app_name_favourite);
            moveToPosition = true;
            initProviders(order);
            loadMovies(0, order);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        View v = recyclerView.findChildViewUnder(0,0);
        if(v != null) {
            outState.putInt(LIFECYCLE_CURRENT_MOVIE_TEXT_KEY, Integer.valueOf(v.getTag().toString()).intValue());
            outState.putString(LIFECYCLE_CURRENT_ORDER_TEXT_KEY, order.toString());
        }
    }

    public void movieClick(View v) {
        View view = recyclerView.findChildViewUnder(0,0);
        if(view != null) {
            this.lastPosition = Integer.valueOf(view.getTag().toString()).intValue();
        }
        Intent movieDetailIntent = new Intent(MoviesMain.this, MovieDetail.class);
        int pos = (int) v.getTag();
        Movie selectedMovie = provider.getMovieAtPosition(pos);
        movieDetailIntent.putExtra(MovieDetail.MOVIE,gson.toJson(selectedMovie));
        startActivity(movieDetailIntent);
    }
}
