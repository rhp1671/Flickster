package com.kennard.model;

import com.google.gson.annotations.SerializedName;
import com.kennard.flick.MovieNetworkHelper;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by raprasad on 10/11/16.
 */

public class Movie {

    @SerializedName("poster_path")
    public String poster;
    @SerializedName("backdrop_path")
    public String background;
    public int id;
    @SerializedName("original_title")
    public String title;
    public String overview;
    @SerializedName("original_language")
    public String language;
    @SerializedName("release_date")
    public String releaseDate;
    public float popularity;
    @SerializedName("vote_average")
    public float vote;
    @SerializedName("vote_count")
    public float voteCount;
    @SerializedName("video")
    public boolean video;

    public Movie(String poster, String background, int id, String title, String overview, String language, String releaseDate, float popularity, float vote, float voteCount, boolean video) {
        this.poster = poster;
        this.background = background;
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.language = language;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.vote = vote;
        this.voteCount = voteCount;
        this.video = video;

    }

    public String getPoster() {
        return String.format(MovieNetworkHelper.imageUrlPrefix+ "%s", this.poster );
    }

    public String getBackground() {
         return String.format(MovieNetworkHelper.imageUrlPrefix+ "%s",this.background);
    }

    public Movie(){

    }
}
