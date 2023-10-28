package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.videosplus.R;
import com.example.videosplus.adapter.MovieListAdapter;
import com.example.videosplus.domain.MovieItem;
import com.example.videosplus.domain.MovieList;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterBestRatedMovies, adapterBestRatedShows;
    private RecyclerView recyclerViewBestRatedMovies, recyclerViewBestRatedShows;
    private ProgressBar loadingBestRatedMovies, loadingBestRatedShows;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        sendRequestMovies();
        sendRequestShows();
    }

    private void initView() {
        recyclerViewBestRatedMovies = findViewById(R.id.best_movies_view);
        recyclerViewBestRatedMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBestRatedShows = findViewById(R.id.best_shows_view);
        recyclerViewBestRatedShows.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loadingBestRatedMovies = findViewById(R.id.best_movies_view_loading);
        loadingBestRatedShows = findViewById(R.id.best_shows_view_loading);
    }

    private void sendRequestMovies() {
        mRequestQueue = Volley.newRequestQueue(this);
        loadingBestRatedMovies.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "http://localhost:8080/api/movies", response -> {
            Gson gson = new Gson();
            MovieList movies = gson.fromJson(response, MovieList.class);
            adapterBestRatedMovies = new MovieListAdapter(movies);
            recyclerViewBestRatedMovies.setAdapter(adapterBestRatedMovies);
        }, error -> {
            //TODO
        });
        loadingBestRatedMovies.setVisibility(View.GONE);
        mRequestQueue.add(mStringRequest);
    }

    private void sendRequestShows() {
        mRequestQueue = Volley.newRequestQueue(this);
        loadingBestRatedShows.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "", response -> {
            //TODO
        }, error -> {
            //TODO
        });
        loadingBestRatedShows.setVisibility(View.GONE);
        mRequestQueue.add(mStringRequest);
    }
}