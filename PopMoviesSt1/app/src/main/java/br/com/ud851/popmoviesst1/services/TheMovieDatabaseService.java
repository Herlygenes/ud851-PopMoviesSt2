package br.com.ud851.popmoviesst1.services;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.interfaces.AsyncTaskDelegate;
import br.com.ud851.popmoviesst1.utils.JSONUtils;
import br.com.ud851.popmoviesst1.utils.NetworkUtils;
import br.com.ud851.popmoviesst1.utils.TMDBUtils;

/**
 * Created by Herlygenes Pinto on 16/02/2018.
 */

public class TheMovieDatabaseService extends AsyncTask<String, String, List<Object>> {

    private AsyncTaskDelegate mDelegate = null;
    private Context mContext;

    public TheMovieDatabaseService(Context context, AsyncTaskDelegate responder) {
        mDelegate = responder;
        this.mContext = context;
    }

    @Override
    protected List<Object> doInBackground(String... args) {
        String tmdbSearchResults = null;
        try {
            if(args[0].equals(TMDBUtils.GET_ALL_MOVIES)){
                String searchQuery = args[1];
                URL searchUrl = TMDBUtils.buildSearchForAllUrl(searchQuery);
                tmdbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                if (tmdbSearchResults != null && !tmdbSearchResults.equals("")) {
                    return JSONUtils.populateMoviesFromJSONString(tmdbSearchResults);
                }
            } else if(args[0].equals(TMDBUtils.GET_MOVIE_TRAILERS)){
                String movieId = args[1];
                URL searchUrl = TMDBUtils.buildSearchForTrailersUrl(movieId);
                tmdbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                if (tmdbSearchResults != null && !tmdbSearchResults.equals("")) {
                    return JSONUtils.populateTrailersFromJSONString(tmdbSearchResults);
                }
            } else if(args[0].equals(TMDBUtils.GET_MOVIE_COMMENTS)){
                String movieId = args[1];
                URL searchUrl = TMDBUtils.buildSearchForCommentsUrl(movieId);
                tmdbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                if (tmdbSearchResults != null && !tmdbSearchResults.equals("")) {
                    return JSONUtils.populateReviewsFromJSONString(tmdbSearchResults);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Object> data) {
        super.onPostExecute(data);
        if(mDelegate != null){
            mDelegate.processFinish(data);
        }
    }
}
