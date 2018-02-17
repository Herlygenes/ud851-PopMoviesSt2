package br.com.ud851.popmoviesst1.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.data.Movie;

/**
 * Created by sujei on 16/02/2018.
 */

public class JSONUtils {
    private static String NO_DATA_FOUND;

    public static List<Movie> populateMoviesFromJSONString(String jsonResponse, Context context){
        NO_DATA_FOUND = context.getResources().getString(R.string.no_data_found);
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject jsonTMDBResponse = new JSONObject(jsonResponse);
            JSONArray jsonArrayMovies = jsonTMDBResponse.getJSONArray("results");
            for(int i = 0; i < jsonArrayMovies.length(); i++){
                movies.add(generateMovieFromJSONObject(jsonArrayMovies.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private static Movie generateMovieFromJSONObject(JSONObject object) throws JSONException {
        if(object != null){
            return new Movie(
                object.optString(Movie.ID),
                object.optString(Movie.VOTE_AVERAGE),
                object.optString(Movie.TITLE),
                object.optString(Movie.POPULARITY),
                object.optString(Movie.POSTER_PATH),
                object.optString(Movie.ORIGINAL_LANGUAGE),
                object.optString(Movie.OVERVIEW),
                object.optString(Movie.RELEASE_DATE)
            );
        } else {
            return new Movie();
        }
    }
}
