package br.com.ud851.popmoviesst1.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.data.Movie;
import br.com.ud851.popmoviesst1.data.Trailer;

/**
 * Created by Herlygenes on 16/02/2018.
 */

public class JSONUtils {
    private static String NO_DATA_FOUND;

    public static List<Object> populateMoviesFromJSONString(String jsonResponse){
        NO_DATA_FOUND = App.getContext().getResources().getString(R.string.no_data_found);
        List<Object> movies = new ArrayList<>();
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

    public static List<Object> populateTrailersFromJSONString(String jsonResponse){
        NO_DATA_FOUND = App.getContext().getResources().getString(R.string.no_data_found);
        List<Object> trailers = new ArrayList<>();
        try {
            JSONObject jsonTMDBResponse = new JSONObject(jsonResponse);
            JSONArray jsonArrayMovies = jsonTMDBResponse.getJSONArray("results");
            for(int i = 0; i < jsonArrayMovies.length(); i++){
                trailers.add(generateTrailerFromJSONObject(jsonArrayMovies.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
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

    private static Trailer generateTrailerFromJSONObject(JSONObject object) throws JSONException {
        if(object != null){
            return new Trailer(
                    object.optString(Trailer.ID),
                    object.optString(Trailer.ISO_639_1),
                    object.optString(Trailer.ISO_3166_1),
                    object.optString(Trailer.KEY),
                    object.optString(Trailer.NAME),
                    object.optString(Trailer.SITE),
                    object.optString(Trailer.SIZE),
                    object.optString(Trailer.TYPE)
            );
        } else {
            return new Trailer();
        }
    }
}
