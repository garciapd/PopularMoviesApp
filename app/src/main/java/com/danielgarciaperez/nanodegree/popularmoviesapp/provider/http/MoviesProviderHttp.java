package com.danielgarciaperez.nanodegree.popularmoviesapp.provider.http;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.danielgarciaperez.nanodegree.popularmoviesapp.BuildConfig;
import com.danielgarciaperez.nanodegree.popularmoviesapp.MovieLoaderListener;
import com.danielgarciaperez.nanodegree.popularmoviesapp.adapter.MoviesAdapter;
import com.danielgarciaperez.nanodegree.popularmoviesapp.model.Movie;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.danielgarciaperez.nanodegree.popularmoviesapp.model.PaginatedResult;
import com.danielgarciaperez.nanodegree.popularmoviesapp.provider.MoviesProvider;
import com.google.gson.Gson;

/**
 * Created by danielgarciaperez on 09/05/2017.
 */

public class MoviesProviderHttp implements MoviesProvider, OnFetchListener{

    private final String API_KEY = BuildConfig.API_KEY;
    private final String URL = "http://api.themoviedb.org/3/movie";

    private Map<Integer, Boolean> fetching = new HashMap<>();

    private Map<Integer, PaginatedResult> paginatedResults = new HashMap<>();

    private Order order;

    private MovieLoaderListener listener;

    private final int elementsPerPage = 20;


    public MoviesProviderHttp(MovieLoaderListener listener){
        this.listener = listener;
    }


    @Override
    public Movie getMovieAtPosition(int position) {
        int pageNumber = 0;
        if(position >0 && elementsPerPage > 0){
            pageNumber = position / elementsPerPage;
        }

        PaginatedResult page = paginatedResults.get(pageNumber);

        if(page == null || page.getResults().size() == 0) {
            if(fetching.get(pageNumber) == null || !fetching.get(pageNumber)){
                String[] urls = new String[1];
                urls[0] = URL +getOrderPath(this.order)+"?api_key="+ API_KEY +"&page="+Integer.valueOf(pageNumber+1);

                fetching.put(pageNumber, true);
                new FetchMoviesTask(this, position).execute(urls);
            }
            return null;
        }

        int positionInPage = position - (elementsPerPage*pageNumber);

        return page.getResults().get(positionInPage);

    }

    @Override
    public int getNumMovies() {

        if(paginatedResults == null || paginatedResults.size() ==0) return 0;

        for(PaginatedResult pages: paginatedResults.values()){
            if(pages != null){
                return pages.getTotalResults();
            }
        }

        return 0;
    }

    private String getOrderPath(Order order){
        if(order == Order.POPULAR){
            return "/popular";
        }else{
            return "/top_rated";
        }

    }

    @Override
    public void loadMovies(int position, Order order) throws IOException {
        this.order = order;
        clearCache();
        getMovieAtPosition(position);
    }

    private void clearCache() {
        this.fetching = new HashMap<>();
        this.paginatedResults = new HashMap<>();
    }

    @Override
    public void onStringCompleted(String result, int position) {
        if(result != null && result.length() > 0){
            Gson gson = new Gson();
            PaginatedResult results = gson.fromJson(result, PaginatedResult.class);
            this.paginatedResults.put(results.getPage().intValue()-1, results);

            this.fetching.put(results.getPage().intValue()-1, true);
            listener.onMovieLoaded(position);
        }
    }

    @Override
    public void onStringError(String url) {
        if(url.contains("page=")){
            int page = Integer.valueOf(url.substring(url.indexOf("page=")+"page=".length(),url.length()));
            fetching.put(Integer.valueOf(page-1).intValue(), false);
        }
    }

}

interface OnFetchListener {

    void onStringCompleted(String s, int position);

    void onStringError(String s);
}


class FetchMoviesTask extends AsyncTask<String, Void, String> {

     private final OnFetchListener listener;
     private int position= 0;

     FetchMoviesTask(OnFetchListener listener, int position){
         this.listener = listener;
         this.position = position;
     }

     @Override
     protected String doInBackground(String... urls) {
         String url = urls[0];
         try {
             return run(url);
         } catch (IOException e) {
             listener.onStringError(url);
             e.printStackTrace();
         }

         return "";
     }

     @Override
     protected void onPostExecute(String result) {
         listener.onStringCompleted(result, position);
     }

     private String run(String url) throws IOException {
         OkHttpClient client = new OkHttpClient();
         Request request = new Request.Builder()
                 .url(url)
                 .build();

         Response response = client.newCall(request).execute();
         return response.body().string();
     }

 }
