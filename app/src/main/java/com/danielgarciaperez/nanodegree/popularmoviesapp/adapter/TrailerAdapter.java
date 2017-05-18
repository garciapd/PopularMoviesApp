package com.danielgarciaperez.nanodegree.popularmoviesapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.danielgarciaperez.nanodegree.popularmoviesapp.R;
import com.danielgarciaperez.nanodegree.popularmoviesapp.model.Trailer;

/**
 * Created by danielgarciaperez on 18/05/2017.
 */

public class TrailerAdapter extends ArrayAdapter<Trailer> {

    Context context;
    int layoutResourceId;
    Trailer data[] = null;

    public TrailerAdapter(Context context, int layoutResourceId, Trailer[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TrailerHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TrailerHolder();
            holder.imgButton = (ImageButton)row.findViewById(R.id.playTrailer);
            holder.trailerTitle = (TextView)row.findViewById(R.id.trailerText);

            row.setTag(holder);
        }
        else
        {
            holder = (TrailerHolder)row.getTag();
        }

        Trailer trailer = data[position];
        holder.trailerTitle.setText(trailer.getName());
        holder.imgButton.setTag(trailer.getKey());

        return row;
    }

    @Override
    public int getCount() {
        if(data !=null && data.length > 0) return data.length;
        return 0;
    }

    static class TrailerHolder
    {
        ImageButton imgButton;
        TextView trailerTitle;
    }
}
