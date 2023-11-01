package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.videosplus.R;
import com.example.videosplus.domain.MovieItem;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

public class MovieDetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar movieDetailLoading;
    private TextView titleText, movieRatingText, movieDateText, movieDurationText, movieSummaryInfo, movieGenreInfo;
    private NestedScrollView scrollView;
    private int movieId;
    private ShapeableImageView pic1;
    private ImageView pic2, backImg;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = getIntent().getIntExtra("id", 1);
        initView();
        sendRequest();
    }

    private void initView() {
        titleText = findViewById(R.id.movie_title);
        movieDetailLoading = findViewById(R.id.detail_loading);
        pic1 = findViewById(R.id.poster_normal_img);
        pic2 = findViewById(R.id.poster_big_img);
        movieRatingText = findViewById(R.id.movie_rating);
        movieDateText = findViewById(R.id.movie_release);
        movieDurationText = findViewById(R.id.movie_duration);
        movieSummaryInfo = findViewById(R.id.movie_summary_info);
        movieGenreInfo = findViewById(R.id.movie_genre_info);
        backImg = findViewById(R.id.imageView4);
        scrollView = findViewById(R.id.nested_detail_view);
        recyclerView = findViewById(R.id.images_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        backImg.setOnClickListener(v -> finish());
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        movieDetailLoading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.103:8080/api/movies/" + movieId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                movieDetailLoading.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

                MovieItem movie = gson.fromJson(response, MovieItem.class);

                Glide.with(MovieDetailActivity.this).load("poster").into(pic1);
                Glide.with(MovieDetailActivity.this).load("poster").into(pic2);
                Log.d("bug", "onResponse: " + movie.getTitle());
                titleText.setText(movie.getTitle());
                //movieRatingText.setText(movie.getRating().toString());
                movieDurationText.setText(movie.getDuration().toString());
                movieDateText.setText(movie.getReleaseDate());
                movieSummaryInfo.setText(movie.getSummary());
                movieGenreInfo.setText(movie.getGenre());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                movieDetailLoading.setVisibility(View.GONE);

            }
        });
        mRequestQueue.add(mStringRequest);
    }
}