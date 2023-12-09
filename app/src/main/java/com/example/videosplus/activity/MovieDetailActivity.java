package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.videosplus.R;
import com.example.videosplus.fragment.VideoVersionDialogFragment;
import com.example.videosplus.object.MovieVersion;
import com.example.videosplus.object.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private int movieId, releaseDate, duration;
    private String title, poster, summary;
    private float rating;
    private TextView movieTitle, movieRating, movieReleaseDate, movieDuration, movieSummary;
    private ShapeableImageView posterNormalSize;
    private ImageView posterBigSize;
    private List<MovieVersion> movieVersions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = getIntent().getIntExtra("id", 1);
        releaseDate = getIntent().getIntExtra("releaseDate", 1);
        duration = getIntent().getIntExtra("duration", 1);
        title = getIntent().getStringExtra("title");
        poster = getIntent().getStringExtra("poster");
        summary = getIntent().getStringExtra("summary");
        rating = getIntent().getFloatExtra("rating", 1);

        initView();
        setMovieInfo();
        sendRequestMovieVersion();
    }

    private void initView() {
        movieTitle = findViewById(R.id.movie_title);
        posterNormalSize = findViewById(R.id.poster_normal_img);
        posterBigSize = findViewById(R.id.poster_big_img);
        movieRating = findViewById(R.id.movie_rating);
        movieReleaseDate = findViewById(R.id.movie_release);
        movieDuration = findViewById(R.id.movie_duration);
        movieSummary = findViewById(R.id.movie_summary_info);
        ImageView backArrow = findViewById(R.id.imageView4);
        FloatingActionButton playArrow = findViewById(R.id.play_arrow);

        backArrow.setOnClickListener(v -> finish());

        playArrow.setOnClickListener(v -> openDialog());
    }

    private void sendRequestMovieVersion() {
        RequestQueue movieRequestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://34.34.73.78:8080/movieVersions/" + movieId, response -> {
            Type listType = new TypeToken<ArrayList<MovieVersion>>(){}.getType();
            movieVersions = new Gson().fromJson(response, listType);

        }, error -> {});

        movieRequestQueue.add(stringRequest);
    }

    public void openDialog() {
        VideoVersionDialogFragment videoVersionDialogFragment = new VideoVersionDialogFragment(movieVersions, this);
        videoVersionDialogFragment.show(getSupportFragmentManager(), "Movie Version");
    }

    @SuppressLint("SetTextI18n")
    public void setMovieInfo() {
        Glide.with(MovieDetailActivity.this).load(poster).into(posterNormalSize);
        Glide.with(MovieDetailActivity.this).load(poster).into(posterBigSize);

        movieTitle.setText(title);
        movieRating.setText(String.valueOf(rating));
        movieDuration.setText(String.valueOf(duration));
        movieReleaseDate.setText(String.valueOf(releaseDate));
        movieSummary.setText(summary);
    }
}