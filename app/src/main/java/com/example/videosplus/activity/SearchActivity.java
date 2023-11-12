package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.videosplus.R;
import com.example.videosplus.adapter.MovieListAdapter;
import com.example.videosplus.object.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText searchView = findViewById(R.id.search_input);
        recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        movieList = (ArrayList<Movie>) bundle.getSerializable("movieList");
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }
        });

    }

    private void filterList(String newText) {
        if (newText.isEmpty()) {
            return;
        }

        List<Movie> filteredList = new ArrayList<>();
        for (Movie movie : movieList) {
            if (movie.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(movie);
            }
        }

        MovieListAdapter movieListAdapter = new MovieListAdapter(filteredList);
        recyclerView.setAdapter(movieListAdapter);
    }
}