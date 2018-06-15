package com.yesplz.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yesplz.popularmovies.model.Movie;
import com.yesplz.popularmovies.model.MovieList;
import com.yesplz.popularmovies.retrofit2.MovieService;
import com.yesplz.popularmovies.retrofit2.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.rv_movies) RecyclerView rvMovieList;
    @BindView(R.id.pb_loading) ProgressBar pbLoading;
    private static final String POPULAR = "POPULAR";
    private static final String TOP_RATED = "TOP_RATED";
    private static final String STATE_MOVIE_LIST = "STATE_MOVIE_LIST";
    private static final String API_KEY = "api_key";
    static final String SELECT_MOVIE_INTENT = "selectedMovie";

    private MovieAdapter mMovieAdapter;
    private RecyclerView mMovieRecyclerView;
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private String sortBy = POPULAR;
    MovieService movieService;
    Map<String, String> queryMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        queryMap.put(API_KEY, getString(R.string.api_key));

        mMovieRecyclerView = rvMovieList;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mMovieRecyclerView.setLayoutManager(gridLayoutManager);
        mMovieAdapter = new MovieAdapter(mMovieList, this);
        mMovieRecyclerView.setAdapter(mMovieAdapter);

        setupShareddPreferences();

        if(savedInstanceState == null || !savedInstanceState.containsKey(STATE_MOVIE_LIST)) {
            fetchMovieData();
        }else{
            mMovieList = savedInstanceState.getParcelableArrayList(STATE_MOVIE_LIST);
            mMovieAdapter.setMovieList(mMovieList);
        }

    }

    private void fetchMovieData(){
        if(NetworkUtil.isNetworkAvailable(this)) {
            new FetchMovies().execute(sortBy);
        }else{
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void setupShareddPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sortBy = sharedPreferences.getString(getString(R.string.lp_sortby), POPULAR);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.lp_sortby))) {
            sortBy = sharedPreferences.getString(getString(R.string.lp_sortby), POPULAR);
            fetchMovieData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_MOVIE_LIST, mMovieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClick(int index) {
        Intent detailPageIntent = new Intent(MainActivity.this, DetailActivity.class);
        detailPageIntent.putExtra(SELECT_MOVIE_INTENT, mMovieList.get(index));
        startActivity(detailPageIntent);
    }

    class FetchMovies extends AsyncTask<String, Void, MovieList>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoading.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected MovieList doInBackground(String... strings) {
            if(strings.length == 0){
                return null;
            }

            movieService = RetrofitClient.getInstance("https://api.themoviedb.org/3/").create(MovieService.class);
            try {
                if(TOP_RATED.equals(strings[0])) {
                    Call<MovieList> call = movieService.getTopRatedMoview(queryMap);
                    Response<MovieList> response = call.execute();
                    return response.body();
                }else if(POPULAR.equals(strings[0])){
                    Call<MovieList> call = movieService.getPopularMovies(queryMap);
                    Response<MovieList> response = call.execute();
                    return response.body();
                }else{
                    return null;
                }

            }catch (IOException e){
                Log.e("FetchMovie", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieList movies) {
            pbLoading.setVisibility(ProgressBar.INVISIBLE);
            if(movies != null) {
                mMovieAdapter.removeAllMovieList();
                mMovieList = movies.getMovieList();
                mMovieAdapter.setMovieList(mMovieList);
            }else{
                Log.d("FetchMovies","No result");
            }
        }
    }
}
