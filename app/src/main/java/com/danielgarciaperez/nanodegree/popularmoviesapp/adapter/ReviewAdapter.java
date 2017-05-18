package com.danielgarciaperez.nanodegree.popularmoviesapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        for(int i=0; i< data.length; i++){
            View r = inflater.inflate(layoutResourceId, null, false);
            TextView tv = (TextView) r.findViewById(R.id.content);
            tv.setText(data[i].getContent());
            LinearLayout l = (LinearLayout) ((Activity) context).findViewById(R.id.reviews);
            l.addView(r);
        }
    }

    @Override
    public int getCount() {
        if (data != null && data.length > 0) return data.length;
        return 0;
    }

}
