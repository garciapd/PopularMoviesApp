package com.danielgarciaperez.nanodegree.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by danielgarciaperez on 13/05/2017.
 */

public class MovieDetail extends AppCompatActivity {

    public static final String TITLE = "title";
    public static final String POSTER = "poster";
    public static final String DATE = "date";
    public static final String VOTE = "vote";
    public static final String SYNOPSIS = "synopsis";

    private TextView title;
    private ImageView poster;
    private TextView date;
    private TextView vote;
    private TextView synopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        title = (TextView) findViewById(R.id.title);
        poster = (ImageView) findViewById(R.id.poster);
        date = (TextView) findViewById(R.id.date);
        vote = (TextView) findViewById(R.id.vote);
        synopsis = (TextView) findViewById(R.id.synopsis);

        fillView();
    }

    private void fillView() {
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        title.setText(extras.getString(TITLE));
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185/"+extras.getString(POSTER)).into(poster);
        date.setText(extras.getString(DATE).substring(0,4));
        vote.setText(extras.getString(VOTE)+"/10");
        synopsis.setText(extras.getString(SYNOPSIS));
    }
}
