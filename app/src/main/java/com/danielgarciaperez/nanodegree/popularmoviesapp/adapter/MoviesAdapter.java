package com.danielgarciaperez.nanodegree.popularmoviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.danielgarciaperez.nanodegree.popularmoviesapp.R;
import com.danielgarciaperez.nanodegree.popularmoviesapp.model.Movie;
import com.danielgarciaperez.nanodegree.popularmoviesapp.provider.MoviesProvider;
import com.squareup.picasso.Picasso;

/**
 * Created by danielgarciaperez on 08/05/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder>{
    private MoviesProvider provider;
    private Context context;

    public MoviesAdapter(MoviesProvider provider, Context context){
        this.provider = provider;
        this.context = context;
    }


    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_preview;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapterViewHolder holder, int position) {
        int pos = position * 2;

        Movie movie1 = provider.getMovieAtPosition(pos);
        if(movie1 != null){
            holder.imageView1.setTag(pos);
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movie1.getPosterPath()).into(holder.imageView1);
        }

        Movie movie2 = provider.getMovieAtPosition(pos+1);
        if(movie2 != null) {
            holder.imageView2.setTag(pos+1);
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movie2.getPosterPath()).into(holder.imageView2);
        }
    }

    @Override
    public int getItemCount() {
        return this.provider.getNumMovies()/2;
    }


    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageView1;
        public final ImageView imageView2;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            imageView1 = (ImageView) view.findViewById(R.id.imageView1);
            imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        }

    }

}