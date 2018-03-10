package br.com.ud851.popmoviesst1.data.vos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public class MovieVO implements Parcelable{

    public static String ID = "id";
    public static String VOTE_AVERAGE = "vote_average";
    public static String TITLE = "title";
    public static String POPULARITY = "popularity";
    public static String POSTER_PATH = "poster_path";
    public static String ORIGINAL_LANGUAGE = "original_language";
    public static String OVERVIEW = "overview";
    public static String RELEASE_DATE = "release_date";
    public static String PARCELABLE_KEY = "parcelable";

    @SerializedName("id")
    private String id;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("title")
    private String title;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    public MovieVO(String id, String voteAverage, String title, String popularity, String posterPath, String originalLanguage, String overview, String releaseDate) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public MovieVO(){
        this.id = "";
        this.voteAverage = "";
        this.title = "";
        this.popularity = "";
        this.posterPath = "";
        this.originalLanguage = "";
        this.overview = "";
        this.releaseDate = "";
    }

    protected MovieVO(Parcel in) {
        id = in.readString();
        voteAverage = in.readString();
        title = in.readString();
        popularity = in.readString();
        posterPath = in.readString();
        originalLanguage = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<MovieVO> CREATOR = new Creator<MovieVO>() {
        @Override
        public MovieVO createFromParcel(Parcel in) {
            return new MovieVO(in);
        }

        @Override
        public MovieVO[] newArray(int size) {
            return new MovieVO[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(voteAverage);
        parcel.writeString(title);
        parcel.writeString(popularity);
        parcel.writeString(posterPath);
        parcel.writeString(originalLanguage);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
    }

    @Override
    public String toString() {
        return  "Movie{" +
                ID + "='" + id + '\'' +
                VOTE_AVERAGE + "='" + voteAverage + '\'' +
                TITLE + "='" + title + '\'' +
                POPULARITY + "='" + popularity + '\'' +
                POSTER_PATH + "='" + posterPath + '\'' +
                ORIGINAL_LANGUAGE + "='" + originalLanguage + '\'' +
                OVERVIEW + "='" + overview + '\'' +
                RELEASE_DATE + "='" + releaseDate + '\'' +
                "}";
    }
}
