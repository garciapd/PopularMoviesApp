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
import com.danielgarciaperez.nanodegree.popularmoviesapp.model.Review;

/**
 * Created by danielgarciaperez on 18/05/2017.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {

    Context context;
    int layoutResourceId;
    Review data[] = null;

    public ReviewAdapter(Context context, int layoutResourceId, Review[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ReviewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ReviewHolder();
            holder.content = (TextView) row.findViewById(R.id.content);

            row.setTag(holder);
        } else {
            holder = (ReviewHolder) row.getTag();
        }

        Review review = data[position];
        holder.content.setText(review.getContent());

        return row;
    }

    @Override
    public int getCount() {
        if (data != null && data.length > 0) return data.length;
        return 0;
    }

    static class ReviewHolder {
        TextView content;
    }
}
