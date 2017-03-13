package com.kennard.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by raprasad on 10/15/16.
 */

public class TheMovieDbResponse {
    @SerializedName("results")
    ArrayList<Movie> movies;

    public TheMovieDbResponse() {
        this.movies = new ArrayList<>();
    }

    public ArrayList getMovies(){
        return movies;
    }

}
