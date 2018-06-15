package com.yesplz.popularmovies;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yesplz.popularmovies.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yesplz.popularmovies.MainActivity.SELECT_MOVIE_INTENT;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.iv_backdrop) ImageView ivBackdrop;
    @BindView(R.id.iv_poster) ImageView ivPoster;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_release_date) TextView tvRelease;
    @BindView(R.id.tv_vote_average) TextView tvVoteAverage;
    @BindView(R.id.tv_overview) TextView tvOverview;
    Movie aMovie;
    String imageURL = "http://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            aMovie = extra.getParcelable(SELECT_MOVIE_INTENT);
        }
        displayUI();
    }

    public void displayUI(){
        Picasso.with(this).load(imageURL + aMovie.getBackdrop()).fit().centerCrop().into(ivBackdrop);
        Picasso.with(this).load(imageURL + aMovie.getThumbnail()).into(ivPoster);
        tvTitle.setText(aMovie.getTitle());
        tvRelease.setText(aMovie.getRelease_date());
        tvVoteAverage.setText(String.valueOf(aMovie.getVote_average()));
        tvOverview.setText(aMovie.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
