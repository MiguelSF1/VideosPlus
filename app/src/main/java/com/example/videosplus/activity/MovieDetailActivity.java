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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.videosplus.R;
import com.google.android.material.imageview.ShapeableImageView;

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
        movieActorsInfo = findViewById(R.id.movie_actor_info);
        backImg = findViewById(R.id.imageView4);
        recyclerView = findViewById(R.id.images_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        backImg.setOnClickListener(v -> finish());
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        movieDetailLoading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        //TODO
    }
}