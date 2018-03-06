package br.com.ud851.popmoviesst1.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.adapters.MainAdapter;
import br.com.ud851.popmoviesst1.data.contracts.TMDBContract;
import br.com.ud851.popmoviesst1.data.providers.TMDBContentProvider;
import br.com.ud851.popmoviesst1.data.vos.MovieVO;
import br.com.ud851.popmoviesst1.interfaces.AsyncTaskDelegate;
import br.com.ud851.popmoviesst1.listeners.ScrollListener;
import br.com.ud851.popmoviesst1.services.TheMovieDatabaseService;
import br.com.ud851.popmoviesst1.utils.StateHolder;
import br.com.ud851.popmoviesst1.utils.TMDBUtils;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public class MainActivity extends MenuActivity implements AsyncTaskDelegate{
    private List<MovieVO> movies = new ArrayList<>();
    private Context context;
    private TextView menuTitle;

    private String NO_DATA_FOUND;

    @Override protected void onCreate(Bundle savedInstanceState) {
        context = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuTitle = (TextView) findViewById(R.id.tv_menu);

        if(StateHolder.getLastActivity() == null){
            StateHolder.setLastActivity(MainActivity.class.getName());
        }

        NO_DATA_FOUND = getString(R.string.no_data_found);

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            if(!TMDBUtils.TMDB_API_KEY.equals("")){
                getMovieData();
            } else {
                Toast.makeText(context, R.string.toast_api_key_missing, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
    }

    private void getMovieData(){
        String searchQuery;
        Intent intent = getIntent();

        if(intent.hasExtra(TMDBUtils.QUERY)){
            searchQuery = intent.getStringExtra(TMDBUtils.QUERY);
        } else {
            if(StateHolder.getLastActivity().equals(MovieDetailsActivity.class.getName())){
                searchQuery = StateHolder.getState();
                StateHolder.setLastActivity(MainActivity.class.getName());
            } else {
                searchQuery = TMDBUtils.POPULAR_QUERY;
            }
        }
        StateHolder.setState(searchQuery);
        populateMovies(searchQuery);
    }

    private void getMovieDateFromDatabase() {
        Uri uri = TMDBContract.TabMovies.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        this.movies = TMDBContentProvider.getMoviesFromCursor(cursor);
        if(this.movies.size() == 0){
            Toast.makeText(context, getString(R.string.toast_no_favorite_yet), Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }

    private void populateMovies(String searchQuery){
        if(searchQuery.equals(TMDBUtils.FAVORITES)){
           getMovieDateFromDatabase();
            populateMoviesGridView();
        } else {
            String[] args = {TMDBUtils.GET_ALL_MOVIES, searchQuery};
            TheMovieDatabaseService service = new TheMovieDatabaseService(context, this);
            service.execute(args);
        }

        setMenuTitleByQuery(searchQuery);
    }

    private void setMenuTitleByQuery(String searchQuery){
        if(searchQuery.equals(TMDBUtils.POPULAR_QUERY)){
            menuTitle.setText(getString(R.string.menu_opt_most_popular));
        } else if(searchQuery.equals(TMDBUtils.TOP_RATED_QUERY)){
            menuTitle.setText(getString(R.string.menu_opt_top_rated));
        } else {
            menuTitle.setText(getString(R.string.menu_opt_favorites));
        }
    }

    private void populateMoviesGridView(){
        GridView gv = (GridView) findViewById(R.id.grid_view);
        gv.setAdapter(new MainAdapter(context, getImagesURLs()));
        gv.setOnScrollListener(new ScrollListener(context));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieVO movie = movies.get(position);
                Context context = MainActivity.this;
                Intent startChildActivityIntent = new Intent(context, MovieDetailsActivity.class);
                startChildActivityIntent.putExtra(MovieVO.PARCELABLE_KEY, movie);
                startActivity(startChildActivityIntent);
            }
        });
    }

    private String[] getImagesURLs(){
        String[] urls = new String[movies.size()];
        for (int i = 0; i < movies.size(); i++){
            urls[i] = TMDBUtils.IMAGE_BASE_URL + TMDBUtils.IMAGE_SIZE_W185 + movies.get(i).getPosterPath();
        }
        return urls;
    }

    @Override
    public void processFinish(Object output) {
        if(output != null){
            movies = (List<MovieVO>) output;
            populateMoviesGridView();
        }else{
            Toast.makeText(this, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
    }
}
