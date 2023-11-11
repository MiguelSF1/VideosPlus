package com.example.videosplus.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.os.Bundle;

import com.example.videosplus.R;

public class PlayerActivity extends AppCompatActivity {
    private ExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        buildVideoPlayer();
    }


    private void buildVideoPlayer() {
        String videoUrl = getIntent().getStringExtra("videoUrl");
        PlayerView playerView = findViewById(R.id.video_player);

        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = new MediaItem.Builder().setUri(videoUrl).build();
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
}