package com.yesplz.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable{
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String thumbnail;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private float vote_average;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("backdrop_path")
    private String backdrop;

    protected Movie(Parcel in){
        this.id = in.readLong();
        this.title = in.readString();
        this.thumbnail = in.readString();
        this.overview = in.readString();
        this.vote_average = in.readFloat();
        this.release_date = in.readString();
        this.backdrop = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(thumbnail);
        parcel.writeString(overview);
        parcel.writeFloat(vote_average);
        parcel.writeString(release_date);
        parcel.writeString(backdrop);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
