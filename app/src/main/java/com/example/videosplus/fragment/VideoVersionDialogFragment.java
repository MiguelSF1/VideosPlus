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
import com.example.videosplus.activity.MovieDetailActivity;
import com.example.videosplus.activity.PlayerActivity;
import com.example.videosplus.object.MovieVersion;

import java.util.ArrayList;
import java.util.List;

public class VideoVersionDialogFragment extends DialogFragment {
    private Spinner movieVersion;
    private final List<MovieVersion> movieVersions;
    MovieDetailActivity activity;

    public VideoVersionDialogFragment(List<MovieVersion> movieVersions, MovieDetailActivity activity) {
        this.movieVersions = movieVersions;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.video_version_dialog_layout, null);
        movieVersion = view.findViewById(R.id.movie_version);

        List<String> movieFormatsAndResolutions = new ArrayList<>();

        for (MovieVersion movieVersion : movieVersions) {
            movieFormatsAndResolutions.add(movieVersion.getVersionId() + " " + movieVersion.getMovieFormat() + " " + movieVersion.getMovieResolution());
        }

        ArrayAdapter adapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, movieFormatsAndResolutions);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        movieVersion.setAdapter(adapter);


        builder.setView(view).setTitle("Movie Version").setNegativeButton("Cancel", (dialog, which) -> {
        }).setPositiveButton("Play", (dialog, which) -> {
            String selectedOption = (String) movieVersion.getSelectedItem();
            String[] selectedVersion = selectedOption.split(" ");
            int versionId = Integer.parseInt(selectedVersion[0]);

            for (MovieVersion movieVersion : movieVersions) {
               if (versionId == movieVersion.getVersionId()) {
                   Intent intent = new Intent(activity, PlayerActivity.class);
                   intent.putExtra("movieLink", movieVersion.getMovieLink());
                   startActivity(intent);
                   break;
                }
            }
        });

        return builder.create();
    }
}