package com.example.videosplus.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.videosplus.R;
import com.example.videosplus.object.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class PlayerActivity extends AppCompatActivity {
    private ExoPlayer exoPlayer;
    private String id, movieId, movieFormat, movieResolution, movieLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        id = getIntent().getStringExtra("id");
        movieId = getIntent().getStringExtra("movieId");
        movieFormat = getIntent().getStringExtra("movieFormat");
        movieResolution = getIntent().getStringExtra("movieResolution");
        movieLink = getIntent().getStringExtra("movieLink");

        makeRequest();
    }


    private void buildVideoPlayer(String movieUrl) {
        PlayerView playerView = findViewById(R.id.video_player);

        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = new MediaItem.Builder().setUri(movieUrl).build();
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
        playerView.setKeepScreenOn(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        exoPlayer.pause();
    }


    private void makeRequest() {
        try {
            RequestQueue versionRequestQueue = VolleySingleton.getInstance(this).getRequestQueue();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", id);
            jsonBody.put("movieId", movieId);
            jsonBody.put("movieFormat", movieFormat);
            jsonBody.put("movieResolution", movieResolution);
            jsonBody.put("movieLink", movieLink);
            String responseBody = jsonBody.toString();

            StringRequest versionStringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.103:8080/movieVersions/stream",
                    this::buildVideoPlayer,
                    error -> Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    return responseBody.getBytes(StandardCharsets.UTF_8);
                }
            };

            versionRequestQueue.add(versionStringRequest);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}