package com.kennard.flick;

/**
 * Created by raprasad on 10/14/16.
 */

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kennard.model.TheMovieDbResponse;
import com.loopj.android.http.*;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

public class MovieNetworkHelper {

    public static String key = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static String url = "https://api.themoviedb.org/3/movie/now_playing";
    public static String trailerUrl = " https://api.themoviedb.org/3/movie/%s/videos";
    public static String API_KEY = "api_key";
    public static String imageUrlPrefix = "https://image.tmdb.org/t/p/w342/";
    private MovieActivity mActivity;

    public MovieNetworkHelper(MovieActivity activity) {
        this.mActivity = activity;
    }

    public RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        params.put(API_KEY, key);
        return params;
    }

    public void getMovieList() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter(API_KEY, key);
        String urlString = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(urlString)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(LOG_TAG, "error = " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String resString = response.body().string();
                    Log.d(LOG_TAG, "success1 = " + resString);
                    if (response != null) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TheMovieDbResponse res = parseJSON(resString);
                                if (res != null) {
                                    mActivity.setMovieList(res);
                                }
                            }
                        });
                    }
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }


    public TheMovieDbResponse parseJSON(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        TheMovieDbResponse movieDbResponse = gson.fromJson(response, TheMovieDbResponse.class);
        return movieDbResponse;
    }
}
