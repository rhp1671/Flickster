package com.kennard.flick;

import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.kennard.model.Movie;
import com.kennard.model.TheMovieDbResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.OkHttpClient;

import static com.kennard.flick.MovieNetworkHelper.url;
import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

public class DetailActivity   extends YouTubeBaseActivity {

    Movie theMovie;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        ImageView iv = (ImageView) findViewById(R.id.ivMovie);
        final ProgressBar pbBar = (ProgressBar) findViewById(R.id.loader);
        String json = getIntent().getStringExtra("movie");
        Gson gson = new Gson();
        theMovie = gson.fromJson(json, com.kennard.model.Movie.class);
/*
        OkHttpClient client = new OkHttpClient();
        Picasso picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(client)).build();


        picasso.with(this).load(theMovie.getBackground()).transform(new RoundedCornersTransformation(10, 10)).into(iv, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                if (pbBar != null) {
                    pbBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
            }
        });*/



         youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);


        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(theMovie.title);

        TextView tvDate = (TextView) findViewById(R.id.releaseDate);
        tvDate.setText("Release Date: " + theMovie.releaseDate);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(theMovie.vote);

        TextView tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvOverview.setText(theMovie.overview);

        getTrailersResponse();
    }

    public void getTrailersResponse(){

        MovieNetworkHelper helper = new MovieNetworkHelper(null);
        RequestParams params = helper.getRequestParams();
        String trailerUrl = String.format(MovieNetworkHelper.trailerUrl, theMovie.id);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this, trailerUrl, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(LOG_TAG, "error = " + statusCode + " desc = " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(LOG_TAG, "success = " + statusCode + " desc = " + responseString);
                try {
                    JSONObject jsonResult = new JSONObject(responseString);
                    JSONArray jsonReviewsArray = jsonResult.getJSONArray("results");

                    for (int i = 0; i < jsonReviewsArray.length(); i++){
                        JSONObject res = jsonReviewsArray.getJSONObject(i);
                        String type = res.getString("type");

                        if (type.equals("Trailer")){
                            final String key = res.getString("key");
                            Log.d(LOG_TAG, "===>key"  + key);
                            youTubePlayerView.initialize("AIzaSyCtWQLfQlPLj6aEF5GEXjQp2C7x6ZorEps",
                                    new YouTubePlayer.OnInitializedListener() {
                                        @Override
                                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                            YouTubePlayer youTubePlayer, boolean b) {

                                            // do any work here to cue video, play video, etc.
                                            if (theMovie.vote > 5) {
                                                youTubePlayer.loadVideo(key);
                                                youTubePlayer.play();
                                            } else {
                                                youTubePlayer.cueVideo(key);
                                            }
                                        }
                                        @Override
                                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                            YouTubeInitializationResult youTubeInitializationResult) {

                                        }

                                    });

                            break;
                        }

                    }
                } catch (JSONException ex){
                    Log.d(LOG_TAG, "json error = " +ex.getMessage());
                }
            }
        });
    }

}
