package com.yesplz.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yesplz.popularmovies.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context mContext;
    private List<Movie> mMovieList;
    final private MovieItemClickListener mMovieItemClickListener;

    public interface MovieItemClickListener{
        void onMovieItemClick(int index);
    }

    public MovieAdapter(List<Movie> movieList, MovieItemClickListener movieItemClickListener){
        this.mMovieList = movieList;
        this.mMovieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie aMovie = mMovieList.get(position);
        holder.bind(aMovie);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setMovieList(List<Movie> movieList){
        this.mMovieList = movieList;
        notifyDataSetChanged();
    }

    public void removeAllMovieList(){
        this.mMovieList.clear();
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.iv_movie_poster) ImageView ivMovieImage;
        ImageView movieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            movieImage = ivMovieImage;
            itemView.setOnClickListener(this);
        }

        public void bind(Movie aMovie){
            String moviePoster = "http://image.tmdb.org/t/p/w185/" + aMovie.getThumbnail();
            Picasso.with(mContext).load(moviePoster).into(movieImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mMovieItemClickListener.onMovieItemClick(position);
        }
    }
}
