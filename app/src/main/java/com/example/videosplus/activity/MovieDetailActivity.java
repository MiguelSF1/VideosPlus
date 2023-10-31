package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import com.example.videosplus.adapter.ImageListAdapter;
import com.example.videosplus.domain.MovieItem;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar movieDetailLoading;
    private TextView titleText, movieRatingText, movieDateText, movieDurationText, movieSummaryInfo, movieActorsInfo;
    private NestedScrollView scrollView;
    private int movieId;
    private ShapeableImageView pic1;
    private ImageView pic2, backImg;
    private RecyclerView.Adapter adapterImgList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = getIntent().getIntExtra("id", 0);
        initView();
    }

    private void initView() {
        titleText = findViewById(R.id.title_text);
        movieDetailLoading = findViewById(R.id.detail_loading);
        pic1 = findViewById(R.id.poster_normal_img);
        pic2 = findViewById(R.id.poster_big_img);
        movieRatingText = findViewById(R.id.movie_rating);
        movieDateText = findViewById(R.id.movie_release);
        movieDurationText = findViewById(R.id.movie_duration);
        movieSummaryInfo = findViewById(R.id.movie_summary_info);
        movieActorsInfo = findViewById(R.id.movie_genre_info);
        backImg = findViewById(R.id.imageView4);
        recyclerView = findViewById(R.id.images_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        backImg.setOnClickListener(v -> finish());
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        movieDetailLoading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest = new StringRequest(Request.Method.GET, "http://localhost:8080/api/movies" + movieId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                movieDetailLoading.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

                MovieItem movie = gson.fromJson(response, MovieItem.class);

                Glide.with(MovieDetailActivity.this).load("poster").into(pic1);
                Glide.with(MovieDetailActivity.this).load("poster").into(pic2);
                titleText.setText(movie.getTitle());
                movieRatingText.setText("rating");
                movieDurationText.setText("duration");
                movieDateText.setText("release date");
                movieSummaryInfo.setText((CharSequence) movie.getDescription());
                movieActorsInfo.setText("actors");

                if ("movie.getImages()" != null) {
                    adapterImgList = new ImageListAdapter(new ArrayList<>()); // "movie.getImages()"
                    recyclerView.setAdapter(adapterImgList);
                }
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