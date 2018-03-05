package br.com.ud851.popmoviesst1.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.data.vos.MovieVO;
import br.com.ud851.popmoviesst1.data.vos.ReviewVO;
import br.com.ud851.popmoviesst1.data.vos.TrailerVO;

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

    public static List<Object> populateReviewsFromJSONString(String jsonResponse){
        NO_DATA_FOUND = App.getContext().getResources().getString(R.string.no_data_found);
        List<Object> reviews = new ArrayList<>();
        try {
            JSONObject jsonTMDBResponse = new JSONObject(jsonResponse);
            JSONArray jsonArrayMovies = jsonTMDBResponse.getJSONArray("results");
            for(int i = 0; i < jsonArrayMovies.length(); i++){
                reviews.add(generateReviewFromJSONObject(jsonArrayMovies.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    private static MovieVO generateMovieFromJSONObject(JSONObject object) throws JSONException {
        if(object != null){
            return new MovieVO(
                object.optString(MovieVO.ID),
                object.optString(MovieVO.VOTE_AVERAGE),
                object.optString(MovieVO.TITLE),
                object.optString(MovieVO.POPULARITY),
                object.optString(MovieVO.POSTER_PATH),
                object.optString(MovieVO.ORIGINAL_LANGUAGE),
                object.optString(MovieVO.OVERVIEW),
                object.optString(MovieVO.RELEASE_DATE)
            );
        } else {
            return new MovieVO();
        }
    }

    private static TrailerVO generateTrailerFromJSONObject(JSONObject object) throws JSONException {
        if(object != null){
            return new TrailerVO(
                    object.optString(TrailerVO.ID),
                    object.optString(TrailerVO.ISO_639_1),
                    object.optString(TrailerVO.ISO_3166_1),
                    object.optString(TrailerVO.KEY),
                    object.optString(TrailerVO.NAME),
                    object.optString(TrailerVO.SITE),
                    object.optString(TrailerVO.SIZE),
                    object.optString(TrailerVO.TYPE)
            );
        } else {
            return new TrailerVO();
        }
    }

    private static ReviewVO generateReviewFromJSONObject(JSONObject object) throws JSONException {
        if(object != null){
            return new ReviewVO(
                    object.optString(ReviewVO.ID),
                    object.optString(ReviewVO.AUTHOR),
                    object.optString(ReviewVO.CONTENT),
                    object.optString(ReviewVO.URL)
            );
        } else {
            return new ReviewVO();
        }
    }
}
