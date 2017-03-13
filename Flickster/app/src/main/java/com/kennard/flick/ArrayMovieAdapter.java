package com.kennard.flick;

import android.content.Context;
import android.content.res.Configuration;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.kennard.model.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.OkHttpClient;

import static android.R.attr.type;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.media.CamcorderProfile.get;
import static com.kennard.flick.R.id.tvOverview;
import static com.kennard.flick.R.id.tvTitle;

/**
 * Created by raprasad on 10/14/16.
 */

public class ArrayMovieAdapter extends ArrayAdapter<Movie> {

    int TYPE_REGULAR = 0;
    int TYPE_POPULAR = 1;

    int VIEW_COUNT = 2;

    public static class ViewHolder {
        @BindView(R.id.ivMovie)
        ImageView imageView;
        @BindView(R.id.loader)
        ProgressBar pbSpinner;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvOverview)
        TextView tvOverview;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public static class ViewHolderPopular {
        @BindView(R.id.ivMovie)
        ImageView imageView;
        @BindView(R.id.loader)
        ProgressBar pbSpinner;
        @BindView(R.id.play)
        ImageView ivPlayIcon;

        public ViewHolderPopular(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public static class PicassoHelper {

        public static void setImage(Context context, ImageView view, ProgressBar bar, String movie, ImageView playIcon) {
            final ProgressBar pbBar = bar;
            final ImageView ivPlayicon = playIcon;
            OkHttpClient client = new OkHttpClient();
            Picasso picasso = new Picasso.Builder(context).downloader(new OkHttp3Downloader(client)).build();


            picasso.with(context).load(movie).transform(new RoundedCornersTransformation(10, 10)).into(view, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    if (pbBar != null) {
                        pbBar.setVisibility(View.GONE);
                    }
                    if (ivPlayicon != null){
                        ivPlayicon.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onError() {
                }
            });
        }
    }


    public ArrayMovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = (Movie) getItem(position);
        ImageView imageView;
        ProgressBar bar;
        ImageView ivPlay = null;

        int type = getItemViewType(position);

        final ViewHolderPopular viewHolderPopular;
        final ViewHolder viewHolder;

        if (type == TYPE_REGULAR) {
            if (convertView == null) {
                convertView = getInflatedLayoutForType(type);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvTitle.setText(movie.title);
            viewHolder.tvOverview.setText(movie.overview);
            viewHolder.imageView.setImageResource(0);
            viewHolder.pbSpinner.setVisibility(View.VISIBLE);
            imageView = viewHolder.imageView;
            bar = viewHolder.pbSpinner;

        } else {
            if (convertView == null) {
                convertView = getInflatedLayoutForType(type);
                viewHolderPopular = new ViewHolderPopular(convertView);
                convertView.setTag(viewHolderPopular);
            } else {
                viewHolderPopular = (ViewHolderPopular) convertView.getTag();
            }

            imageView = viewHolderPopular.imageView;
            bar = viewHolderPopular.pbSpinner;
            ivPlay = viewHolderPopular.ivPlayIcon;
            if (bar != null) {
                bar.setVisibility(View.VISIBLE);
                if (ivPlay != null) {
                    ivPlay.setVisibility(View.INVISIBLE);
                }
            }
        }


        String sMovie = "";

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            sMovie = movie.getBackground();
        } else {
            if (type == TYPE_POPULAR) {
                sMovie = movie.getBackground();
            } else {
                sMovie = movie.getPoster();
            }
        }

        PicassoHelper.setImage(getContext(), imageView, bar, sMovie, ivPlay);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        float fPopularity = getItem(position).vote;
        if (fPopularity >= 5) {
            return TYPE_POPULAR;
        }
        return TYPE_REGULAR;
    }


    private View getInflatedLayoutForType(int type) {
        if (type == TYPE_REGULAR) {
            return LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item, null);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.movie_list_popular, null);
        }

    }

}
