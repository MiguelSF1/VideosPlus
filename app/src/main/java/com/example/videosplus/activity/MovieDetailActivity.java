package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.videosplus.R;
import com.example.videosplus.object.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

public class MovieDetailActivity extends AppCompatActivity {
    private int movieId;
    private TextView movieTitle, movieRating, movieReleaseDate, movieDuration, movieSummary;
    private ProgressBar movieProgressBar;
    private NestedScrollView nestedScrollView;
    private ShapeableImageView posterNormalSize;
    private ImageView posterBigSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = getIntent().getIntExtra("id", 1);
        initView();
        sendRequest();
    }

    private void initView() {
        movieTitle = findViewById(R.id.movie_title);
        movieProgressBar = findViewById(R.id.detail_loading);
        posterNormalSize = findViewById(R.id.poster_normal_img);
        posterBigSize = findViewById(R.id.poster_big_img);
        movieRating = findViewById(R.id.movie_rating);
        movieReleaseDate = findViewById(R.id.movie_release);
        movieDuration = findViewById(R.id.movie_duration);
        movieSummary = findViewById(R.id.movie_summary_info);
        ImageView backArrow = findViewById(R.id.imageView4);
        FloatingActionButton playArrow = findViewById(R.id.play_arrow);
        nestedScrollView = findViewById(R.id.nested_detail_view);

        backArrow.setOnClickListener(v -> finish());

        // fragment for selecting movie version here
        playArrow.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlayerActivity.class);
            intent.putExtra("videoUrl", "http://techslides.com/demos/sample-videos/small.mp4");
            startActivity(intent);
        });
    }

    private void sendRequest() {
        RequestQueue movieRequestQueue = Volley.newRequestQueue(this);
        movieProgressBar.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);

        @SuppressLint("SetTextI18n")
        StringRequest movieStringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.103:8080/api/movies/" + movieId, response -> {
            movieProgressBar.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.VISIBLE);

            Movie movie = new Gson().fromJson(response, Movie.class);

            Glide.with(MovieDetailActivity.this).load(movie.getPoster()).into(posterNormalSize);
            Glide.with(MovieDetailActivity.this).load(movie.getPoster()).into(posterBigSize);

            movieTitle.setText(movie.getTitle());
            movieRating.setText(movie.getRating().toString());
            movieDuration.setText(movie.getDuration().toString());
            movieReleaseDate.setText(movie.getReleaseDate());
            movieSummary.setText(movie.getSummary());
        }, error -> movieProgressBar.setVisibility(View.GONE));

        movieRequestQueue.add(movieStringRequest);
    }

    // make request for video link
}