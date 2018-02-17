package br.com.ud851.popmoviesst1.services;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.data.Movie;
import br.com.ud851.popmoviesst1.interfaces.AsyncTaskDelegate;
import br.com.ud851.popmoviesst1.utils.JSONUtils;
import br.com.ud851.popmoviesst1.utils.NetworkUtils;

/**
 * Created by sujei on 16/02/2018.
 */

public class TheMovieDatabaseService extends AsyncTask<URL, String, List<Movie>> {

    private AsyncTaskDelegate delegate = null;
    private Context context;
    private String NO_DATA_FOUND;

    public TheMovieDatabaseService(Context context, AsyncTaskDelegate responder) {
        delegate = responder;
        this.context = context;
        NO_DATA_FOUND = context.getString(R.string.no_data_found);
    }

    @Override
    protected List<Movie> doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String tmdbSearchResults = null;
        try {
            tmdbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            if (tmdbSearchResults != null && !tmdbSearchResults.equals("")) {
                return JSONUtils.populateMoviesFromJSONString(tmdbSearchResults, context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        if(delegate != null){
            delegate.processFinish(movies);
        }
    }
}
