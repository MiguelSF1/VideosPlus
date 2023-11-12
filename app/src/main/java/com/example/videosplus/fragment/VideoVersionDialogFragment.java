package com.example.videosplus.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.videosplus.R;
import com.example.videosplus.activity.PlayerActivity;
import com.example.videosplus.object.MovieVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VideoVersionDialogFragment extends DialogFragment {
    private Spinner movieFormat;
    private Spinner movieResolution;
    private final List<MovieVersion> movieVersions;
    private List<String> movieFormats;
    private List<String> movieResolutions;

    public VideoVersionDialogFragment(List<MovieVersion> movieVersions) {
        this.movieVersions = movieVersions;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.video_version_dialog_layout, null);
        movieFormat = view.findViewById(R.id.movie_format);
        movieResolution = view.findViewById(R.id.movie_resolution);

        buildLists();


        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, movieFormats);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        movieFormat.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, movieResolutions);
        adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        movieResolution.setAdapter(adapter2);

        builder.setView(view).setTitle("Movie Version").setNegativeButton("Cancel", (dialog, which) -> {
        }).setPositiveButton("Play", (dialog, which) -> {
            String selectedFormat = (String) movieFormat.getSelectedItem();
            String selectedResolution = (String) movieResolution.getSelectedItem();
            String videoUrl = "";

            for (MovieVersion movieVersion : movieVersions) {
               if (Objects.equals(movieVersion.getMovieFormat(), selectedFormat) && Objects.equals(movieVersion.getMovieResolution(), selectedResolution)) {
                    videoUrl = movieVersion.getMovieLink();
                    break;
                }
            }

            Intent intent = new Intent(getContext(), PlayerActivity.class);
            intent.putExtra("videoUrl", videoUrl);
            startActivity(intent);
        });

        return builder.create();
    }

    private void buildLists() {
        movieFormats = new ArrayList<>();
        movieResolutions = new ArrayList<>();

        for (MovieVersion movieVersion : movieVersions) {
            if (!movieFormats.contains(movieVersion.getMovieFormat())) {
                movieFormats.add(movieVersion.getMovieFormat());
            }
            if (!movieResolutions.contains(movieVersion.getMovieResolution())) {
                movieResolutions.add(movieVersion.getMovieResolution());
            }
        }
    }
}