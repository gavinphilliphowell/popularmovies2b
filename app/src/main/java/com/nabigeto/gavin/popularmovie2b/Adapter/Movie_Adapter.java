package com.nabigeto.gavin.popularmovie2b.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gavin on 1/15/2016.
 */
public class Movie_Adapter implements Parcelable {
    String movieName;
    String movieRating;
    String movieRelease;
    String movieInfo;
    String movieImage;
    String movieBackground;
    String movieID;

    public Movie_Adapter(String mName, String mRating, String mRelease, String mInfo, String image, String mBackground, String mID){
    this.movieName = mName;
    this.movieRating = mRating;
    this.movieRelease = mRelease;
    this.movieInfo = mInfo;
    this.movieImage = image;
    this.movieBackground = mBackground;
    this.movieID = mID;



    }

    public int getItem(int position){

     return position;
    }

    public int describeContents() {

        return 0;
    }

    public Movie_Adapter(Parcel in){
        movieName = in.readString();
        movieRating = in.readString();
        movieRelease = in.readString();
        movieInfo = in.readString();
        movieImage = in.readString();
        movieBackground = in.readString();
        movieID = in.readString();

    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(movieRating);
        parcel.writeString(movieRelease);
        parcel.writeString(movieInfo);
        parcel.writeString(movieImage);
        parcel.writeString(movieBackground);
        parcel.writeString(movieID);

    }

    public String getTitle (){
        return movieName;
    }

    public String getRating (){
        return movieRating;
    }

    public String getRelease (){
        return movieRelease;
    }

    public String getInfo (){
        return movieInfo;
    }

    public String getImage (){
        return movieImage;
    }

    public String getBackground (){
        return movieBackground;
    }

    public String getMovieID () {return movieID;}


    public void setTitle (String movieName){ this.movieName = movieName; }

    public void setRating (String movieRating){
        this.movieRating = movieRating;
    }

    public void setRelease (String movieRelease){
        this.movieRelease = movieRelease;
    }

    public void setInfo (String movieInfo){
        this.movieInfo = movieInfo;
    }

    public void setImage (String movieImage){
        this.movieImage = movieImage;
    }

    public void setBackground (String movieBackground){
        this.movieBackground = movieBackground;
    }

    public void setMovieID (String movieID) {this.movieID = movieID;}



    public static final Creator<Movie_Adapter> CREATOR = new Creator<Movie_Adapter>() {

        @Override
        public Movie_Adapter createFromParcel(Parcel parcel){
            return new Movie_Adapter(parcel);
        }

        public Movie_Adapter[] newArray(int Size) {
            return new Movie_Adapter[Size];
        }
    };
}
