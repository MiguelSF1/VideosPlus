package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
    private RecyclerView.Adapter adapterMovies;
    private RecyclerView recyclerViewMovies;
    private ProgressBar loadingMovies;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        sendRequestMovies();
    }

    private void initView() {
        recyclerViewMovies = findViewById(R.id.best_movies_view);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loadingMovies = findViewById(R.id.best_movies_view_loading);
    }

    private void sendRequestMovies() {
        mRequestQueue = Volley.newRequestQueue(this);
        loadingMovies.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.103:8080/api/movies", response -> {
            Gson gson = new Gson();
            MovieList movies = gson.fromJson(response, MovieList.class);
            adapterMovies = new MovieListAdapter(movies);
            recyclerViewMovies.setAdapter(adapterMovies);
        }, error -> {
            Log.d("tag", "sendRequestMovies: ");
        });
        loadingMovies.setVisibility(View.GONE);
        mRequestQueue.add(mStringRequest);
    }

}