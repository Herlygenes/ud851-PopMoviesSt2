package br.com.ud851.popmoviesst1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MenuActivity {
    private final List<Movie> movies = new ArrayList<>();
    private Context context;

    private String NO_DATA_FOUND;

    private final static String TMDB_API_KEY = BuildConfig.tmdb_api_key;

    @Override protected void onCreate(Bundle savedInstanceState) {
        context = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NO_DATA_FOUND = getString(R.string.no_data_found);

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            if(!TMDB_API_KEY.equals("")){
                getDataFromTMDB();
            } else {
                Toast.makeText(context, R.string.api_key_missing, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG).show();
        }
    }

    private void getDataFromTMDB(){
        String searchQuery;
        Intent intent = getIntent();

        if(intent.hasExtra("query_tmdb")){
            searchQuery = intent.getStringExtra("query_tmdb");
        } else {
            searchQuery = NetworkUtils.TMDB_POPULAR_QUERY;
        }

        URL searchUrl = NetworkUtils.buildUrl(searchQuery, context);
        new TheMovieDataBaseQueryTask().execute(searchUrl);
    }

    private String[] getImagesURLs(){
        String[] urls = new String[movies.size()];
        for (int i = 0; i < movies.size(); i++){
            urls[i] = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_SIZE_W185 + movies.get(i).getPoster_path();
        }
        return urls;
    }

    public class TheMovieDataBaseQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String tmdbSearchResults = null;
            try {
                tmdbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tmdbSearchResults;
        }

        @Override
        protected void onPostExecute(String tmdbSearchResults) {
            if (tmdbSearchResults != null && !tmdbSearchResults.equals("")) {
                populateMoviesFromJSONString(tmdbSearchResults);
                GridView gv = (GridView) findViewById(R.id.grid_view);
                gv.setAdapter(new MainAdapter(context, getImagesURLs()));
                gv.setOnScrollListener(new ScrollListener(context));

                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Movie movie = movies.get(position);
                        Context context = MainActivity.this;
                        Class movieDetailsClass = MovieDetailsActivity.class;
                        Intent startChildActivityIntent = new Intent(context, movieDetailsClass);
                        startChildActivityIntent.putExtra(Movie.TITLE, movie.getTitle());
                        startChildActivityIntent.putExtra(Movie.RELEASE_DATE, movie.getRelease_date());
                        startChildActivityIntent.putExtra(Movie.VOTE_AVERAGE, movie.getPopularity());
                        startChildActivityIntent.putExtra(Movie.OVERVIEW, movie.getOverview());
                        startChildActivityIntent.putExtra(Movie.POSTER_PATH, movie.getPoster_path());
                        startActivity(startChildActivityIntent);
                    }
                });
            }
        }

        private void populateMoviesFromJSONString(String jsonResponse){
            try {
                JSONObject jsonTMDBResponse = new JSONObject(jsonResponse);
                JSONArray jsonArrayMovies = jsonTMDBResponse.getJSONArray("results");
                for(int i = 0; i < jsonArrayMovies.length(); i++){
                    movies.add(generateMovieFromJSONObject(jsonArrayMovies.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private Movie generateMovieFromJSONObject(JSONObject o) throws JSONException {
            return new Movie(
                    o.getString(Movie.ID)!=null?o.getString(Movie.ID):NO_DATA_FOUND,
                    o.getString(Movie.VOTE_AVERAGE)!=null?o.getString(Movie.VOTE_AVERAGE):NO_DATA_FOUND,
                    o.getString(Movie.TITLE)!=null?o.getString(Movie.TITLE):NO_DATA_FOUND,
                    o.getString(Movie.POPULARITY)!=null?o.getString(Movie.POPULARITY):NO_DATA_FOUND,
                    o.getString(Movie.POSTER_PATH)!=null?o.getString(Movie.POSTER_PATH):NO_DATA_FOUND,
                    o.getString(Movie.ORIGINAL_LANGUAGE)!=null?o.getString(Movie.ORIGINAL_LANGUAGE):NO_DATA_FOUND,
                    o.getString(Movie.OVERVIEW)!=null?o.getString(Movie.OVERVIEW):NO_DATA_FOUND,
                    o.getString(Movie.RELEASE_DATE)!=null?o.getString(Movie.RELEASE_DATE):NO_DATA_FOUND
            );
        }
    }
}
