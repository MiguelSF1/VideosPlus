package com.example.videosplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.videosplus.R;
import com.example.videosplus.adapter.MovieListAdapter;
import com.example.videosplus.object.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Movie> movieList;
    private EditText searchView;
    private MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search_input);
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
        List<Movie> filteredList = new ArrayList<>();
        for (Movie movie : movieList) {
            if (movie.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(movie);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this,"No videos found", Toast.LENGTH_SHORT).show();
        } else {
            movieListAdapter = new MovieListAdapter(filteredList);
            recyclerView.setAdapter(movieListAdapter);
        }
    }
}