package com.yesplz.popularmovies.retrofit2;

import com.yesplz.popularmovies.model.MovieList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MovieService {
    @GET("movie/popular")
    Call<MovieList> getPopularMovies(@QueryMap(encoded=true) Map<String, String> options);

    @GET("movie/top_rated")
    Call<MovieList> getTopRatedMoview(@QueryMap(encoded=true) Map<String, String> options);

    @GET("movie/{movie_id}/videos")
    Call<MovieList> getMovieTrailers(@QueryMap(encoded=true) Map<String, String> options);

    @GET("movie/{movie_id}/reviews")
    Call<MovieList> getMovieReviews(@QueryMap(encoded=true) Map<String, String> options);
}
