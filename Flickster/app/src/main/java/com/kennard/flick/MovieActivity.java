package com.kennard.flick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.kennard.model.Movie;
import com.kennard.model.TheMovieDbResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.gson.Gson;

public class MovieActivity extends AppCompatActivity {
    @BindView(R.id.lvMovieList) ListView lvMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_feed);
        ButterKnife.bind(this);

        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                Intent i = new Intent(MovieActivity.this, DetailActivity.class);
                if (movie != null){
                    String s = movie.toString();
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(movie);
                    i.putExtra("movie", jsonString);
                }
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MovieNetworkHelper helper = new MovieNetworkHelper(this);
        helper.getMovieList();
    }

    public void setMovieList(TheMovieDbResponse response){
        ArrayMovieAdapter arrayAdapter = new ArrayMovieAdapter(this,  response.getMovies());
        lvMovie.setAdapter(arrayAdapter);
    }
}
