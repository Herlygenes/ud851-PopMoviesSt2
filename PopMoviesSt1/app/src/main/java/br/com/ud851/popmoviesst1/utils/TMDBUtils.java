package br.com.ud851.popmoviesst1.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import br.com.ud851.popmoviesst1.BuildConfig;
import br.com.ud851.popmoviesst1.data.Trailer;

/**
 * Created by Herlygenes Pinto on 17/02/2018.
 */

public class TMDBUtils {
    public final static String TMDB_API_KEY = BuildConfig.tmdb_api_key;
    public final static String GET_ALL_MOVIES = "get_all_movies";
    public final static String GET_MOVIE_TRAILERS = "get_movie_trailers";
    public final static String POPULAR_QUERY = "popular";
    public final static String TOP_RATED_QUERY = "top_rated";
    public final static String FAVORITES = "favorites";
    public final static String VIDEOS_QUERY = "/videos";
    public final static String REVIEWS_QUERY = "/reviews";
    public final static String QUERY = "query_tmdb";
    public final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public final static String IMAGE_SIZE_W185 = "w185";
    public final static String YOUTUBE = "YouTube";

    private final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    private final static String API_KEY = "?api_key=";

    public static URL buildSearchForAllUrl(String searchQuery) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL + searchQuery + API_KEY + TMDB_API_KEY).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.i("URL_TMDB", url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildSearchForTrailersUrl(String movieId) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL + movieId + VIDEOS_QUERY + API_KEY + TMDB_API_KEY).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.i("URL_TMDB", url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildYoutubeUrl(Trailer trailer){
        URL url = null;
        if(YOUTUBE.equals(trailer.getSite())){
            Uri builtUri = Uri.parse(YOUTUBE_BASE_URL + trailer.getKey()).buildUpon().build();
            try {
                url = new URL(builtUri.toString());
                Log.i("URL_TMDB", url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url;
    }
}
