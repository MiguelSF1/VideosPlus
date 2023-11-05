package com.example.videosplus.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videosplus.R;
import com.example.videosplus.activity.MovieDetailActivity;
import com.example.videosplus.domain.Movie;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    Movie[] movies;

    public MovieListAdapter(Movie[] movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_movie, parent, false);
        return new ViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.ViewHolder holder, int position) {
        holder.movieTitle.setText(movies[position].getTitle());
        holder.movieRating.setText(movies[position].getRating().toString());
        Glide.with(holder.itemView.getContext()).load(movies[position].getPoster()).into(holder.moviePoster);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MovieDetailActivity.class);
            intent.putExtra("id", movies[position].getMovieId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle, movieRating;
        ImageView moviePoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movies_title);
            movieRating = itemView.findViewById(R.id.movies_rating);
            moviePoster = itemView.findViewById(R.id.movies_poster);
        }
    }
}
