package br.com.ud851.popmoviesst1.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.BuildConfig;
import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.services.TheMovieDatabaseService;
import br.com.ud851.popmoviesst1.adapters.MainAdapter;
import br.com.ud851.popmoviesst1.data.Movie;
import br.com.ud851.popmoviesst1.interfaces.AsyncTaskDelegate;
import br.com.ud851.popmoviesst1.listeners.ScrollListener;
import br.com.ud851.popmoviesst1.utils.NetworkUtils;

public class MainActivity extends MenuActivity implements AsyncTaskDelegate{
    private List<Movie> movies = new ArrayList<>();
    private Context context;

    private String NO_DATA_FOUND;
    //private final static String TMDB_API_KEY = BuildConfig.tmdb_api_key;

    @Override protected void onCreate(Bundle savedInstanceState) {
        context = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NO_DATA_FOUND = getString(R.string.no_data_found);

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            if(!NetworkUtils.TMDB_API_KEY.equals("")){
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

        if(intent.hasExtra(NetworkUtils.QUERY_TMDB)){
            searchQuery = intent.getStringExtra(NetworkUtils.QUERY_TMDB);
        } else {
            searchQuery = NetworkUtils.TMDB_POPULAR_QUERY;
        }

        populateMoviesGridView(searchQuery);
    }

    private void populateMoviesGridView(String searchQuery){
        URL searchUrl = NetworkUtils.buildUrl(searchQuery, context);
        TheMovieDatabaseService service = new TheMovieDatabaseService(context, this);
        service.execute(searchUrl);
    }

    private String[] getImagesURLs(){
        String[] urls = new String[movies.size()];
        for (int i = 0; i < movies.size(); i++){
            urls[i] = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_SIZE_W185 + movies.get(i).getPosterPath();
        }
        return urls;
    }

    @Override
    public void processFinish(Object output) {
        if(output != null){
            movies = (List<Movie>) output;

            GridView gv = (GridView) findViewById(R.id.grid_view);
            gv.setAdapter(new MainAdapter(context, getImagesURLs()));
            gv.setOnScrollListener(new ScrollListener(context));

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie = movies.get(position);
                    Context context = MainActivity.this;
                    Intent startChildActivityIntent = new Intent(context, MovieDetailsActivity.class);
                    startChildActivityIntent.putExtra(Movie.PARCELABLE_KEY, movie);
                    startActivity(startChildActivityIntent);
                }
            });
        }else{
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }    }
}
