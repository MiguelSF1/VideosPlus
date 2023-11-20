package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.videosplus.R;
import com.example.videosplus.adapter.MovieListAdapter;
import com.example.videosplus.object.Movie;
import com.example.videosplus.object.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieListAdapter movieListAdapter;
    private RecyclerView recyclerViewMovies;
    private ProgressBar progressBarMovies;
    private List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        sendRequestMovies();
    }

   private void initView() {
        recyclerViewMovies = findViewById(R.id.recycler_view);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 3));
        progressBarMovies = findViewById(R.id.progressBar);
        ImageView searchIcon = findViewById(R.id.search_icon);

        searchIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("movieList", (Serializable) movies);
            intent.putExtra("Bundle", args);
            startActivity(intent);
        });
    }

    private void sendRequestMovies() {
        RequestQueue moviesRequestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        progressBarMovies.setVisibility(View.VISIBLE);
        StringRequest moviesStringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.103:8080/movies", response -> {
            progressBarMovies.setVisibility(View.GONE);
            Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
            movies = new Gson().fromJson(response, listType);
            movieListAdapter = new MovieListAdapter(movies);
            recyclerViewMovies.setAdapter(movieListAdapter);
        }, error -> {
            progressBarMovies.setVisibility(View.GONE);
            Log.d("failure", "sendRequestMovies: Failed ");
        });

        moviesRequestQueue.add(moviesStringRequest);
    }
}