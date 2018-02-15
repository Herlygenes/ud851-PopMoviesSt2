package br.com.ud851.popmoviesst1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public class Movie implements Parcelable{

    public static String ID = "id";
    public static String VOTE_AVERAGE = "vote_average";
    public static String TITLE = "title";
    public static String POPULARITY = "popularity";
    public static String POSTER_PATH = "poster_path";
    public static String ORIGINAL_LANGUAGE = "original_language";
    public static String OVERVIEW = "overview";
    public static String RELEASE_DATE = "release_date";

    private String id;
    private String vote_average;
    private String title;
    private String popularity;
    private String poster_path;
    private String original_language;
    private String overview;
    private String release_date;

    public Movie(String id, String vote_average, String title, String popularity, String poster_path, String original_language, String overview, String release_date) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.overview = overview;
        this.release_date = release_date;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        vote_average = in.readString();
        title = in.readString();
        popularity = in.readString();
        poster_path = in.readString();
        original_language = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(vote_average);
        parcel.writeString(title);
        parcel.writeString(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(original_language);
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }

    @Override
    public String toString() {
        return  "Movie{" +
                ID + "='" + id + '\'' +
                VOTE_AVERAGE + "='" + vote_average + '\'' +
                TITLE + "='" + title + '\'' +
                POPULARITY + "='" + popularity + '\'' +
                POSTER_PATH + "='" + poster_path + '\'' +
                ORIGINAL_LANGUAGE + "='" + original_language + '\'' +
                OVERVIEW + "='" + overview + '\'' +
                RELEASE_DATE + "='" + release_date + '\'' +
                "}";
    }
}
