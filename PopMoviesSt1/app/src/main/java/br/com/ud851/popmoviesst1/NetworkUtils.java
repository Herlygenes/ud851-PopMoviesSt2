package br.com.ud851.popmoviesst1;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public class NetworkUtils {
    private final static String TMDB_API_KEY = BuildConfig.tmdb_api_key;
    private final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String API_KEY = "?api_key=";
    final static String TMDB_POPULAR_QUERY = "popular";
    final static String TMDB_TOP_RATED_QUERY = "top_rated";
    final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    final static String IMAGE_SIZE_W185 = "w185/";


    public static URL buildUrl(String searchQuery, Context context) {
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

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}