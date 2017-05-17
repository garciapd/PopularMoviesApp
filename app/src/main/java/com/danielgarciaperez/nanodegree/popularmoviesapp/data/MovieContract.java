package com.danielgarciaperez.nanodegree.popularmoviesapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by danielgarciaperez on 17/05/2017.
 */

public class MovieContract {

    public static final String AUTHORITY = "com.danielgarciaperez.nanodegree.popularmoviesapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES= "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE = "movie";
    }
}
