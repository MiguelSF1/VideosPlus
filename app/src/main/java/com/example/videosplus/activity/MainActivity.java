package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.videosplus.R;
import com.example.videosplus.adapter.MovieListAdapter;
import com.example.videosplus.domain.Movie;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private MovieListAdapter movieListAdapter;
    private RecyclerView recyclerViewMovies;
    private ProgressBar progressBarMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        sendRequestMovies();
    }

    private void initView() {
        recyclerViewMovies = findViewById(R.id.best_movies_view);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 3));
        progressBarMovies = findViewById(R.id.best_movies_view_loading);
    }

    private void sendRequestMovies() {
        RequestQueue moviesRequestQueue = Volley.newRequestQueue(this);
        progressBarMovies.setVisibility(View.VISIBLE);
        StringRequest moviesStringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.103:8080/api/movies", response -> {
            Movie[] movies = new Gson().fromJson(response, Movie[].class);
            movieListAdapter = new MovieListAdapter(movies);
            recyclerViewMovies.setAdapter(movieListAdapter);
        }, error -> Log.d("failure", "sendRequestMovies: Failed "));
        progressBarMovies.setVisibility(View.GONE);
        moviesRequestQueue.add(moviesStringRequest);
    }
}