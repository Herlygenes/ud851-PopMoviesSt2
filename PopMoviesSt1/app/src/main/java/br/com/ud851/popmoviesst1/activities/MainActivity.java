package br.com.ud851.popmoviesst1.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private static String LOG_TAG = MainActivity.class.getSimpleName();
    private static String CURRENT_TILE = "current_tile";
    private static int TILE_WIDTH = 512;
    private List<MovieVO> mMovies = new ArrayList<>();
    private Context mContext;
    private TextView mMenuTitle;
    private TextView mTvNoInternet;
    private GridView mGvActivityMain;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MainActivity.this;
        setContentView(R.layout.activity_main);
        mMenuTitle = (TextView) findViewById(R.id.tv_menu);
        mTvNoInternet =  (TextView) findViewById(R.id.tv_no_internet);
        mGvActivityMain = (GridView) findViewById(R.id.grid_view);
        mGvActivityMain.setNumColumns(calculateNumberOfColumns());

        if(StateHolder.getsLastActivity() == null){
            StateHolder.setsLastActivity(MainActivity.class.getName());
        }

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            mTvNoInternet.setVisibility(TextView.INVISIBLE);
            if(!TMDBUtils.TMDB_API_KEY.equals("")){
                getMovieData();
            } else {
                Toast.makeText(mContext, R.string.toast_api_key_missing, Toast.LENGTH_LONG).show();
            }
        } else {
            mTvNoInternet.setVisibility(TextView.VISIBLE);
            Toast.makeText(mContext, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_TILE, mGvActivityMain.getFirstVisiblePosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mGvActivityMain.setSelection(savedInstanceState.getInt(CURRENT_TILE));
        super.onRestoreInstanceState(savedInstanceState);
    }

    private int calculateNumberOfColumns(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels / TILE_WIDTH;
    }

    private void getMovieData(){
        String searchQuery;
        Intent intent = getIntent();

        if(intent.hasExtra(TMDBUtils.QUERY)){
            searchQuery = intent.getStringExtra(TMDBUtils.QUERY);
        } else {
            if(StateHolder.getsLastActivity().equals(MovieDetailsActivity.class.getName())){
                searchQuery = StateHolder.getsState();
                StateHolder.setsLastActivity(MainActivity.class.getName());
            } else {
                searchQuery = TMDBUtils.POPULAR_QUERY;
            }
        }
        StateHolder.setsState(searchQuery);
        populateMovies(searchQuery);
    }

    private void getMovieDateFromDatabase() {
        Uri uri = TMDBContract.TabMovies.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        this.mMovies = TMDBContentProvider.getMoviesFromCursor(cursor);
        if(this.mMovies.size() == 0){
            Toast.makeText(mContext, getString(R.string.toast_no_favorite_yet), Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }

    private void populateMovies(String searchQuery){
        if(searchQuery.equals(TMDBUtils.FAVORITES)){
           getMovieDateFromDatabase();
            populateMoviesGridView();
        } else {
            String[] args = {TMDBUtils.GET_ALL_MOVIES, searchQuery};
            TheMovieDatabaseService service = new TheMovieDatabaseService(mContext, this);
            service.execute(args);
        }

        setMenuTitleByQuery(searchQuery);
    }

    private void setMenuTitleByQuery(String searchQuery){
        if(searchQuery.equals(TMDBUtils.POPULAR_QUERY)){
            mMenuTitle.setText(getString(R.string.menu_opt_most_popular));
        } else if(searchQuery.equals(TMDBUtils.TOP_RATED_QUERY)){
            mMenuTitle.setText(getString(R.string.menu_opt_top_rated));
        } else {
            mMenuTitle.setText(getString(R.string.menu_opt_favorites));
        }
    }

    private void populateMoviesGridView(){
        GridView gv = (GridView) findViewById(R.id.grid_view);
        gv.setAdapter(new MainAdapter(mContext, getImagesURLs()));
        gv.setOnScrollListener(new ScrollListener(mContext));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieVO movie = mMovies.get(position);
                Context context = MainActivity.this;
                Intent startChildActivityIntent = new Intent(context, MovieDetailsActivity.class);
                startChildActivityIntent.putExtra(MovieVO.PARCELABLE_KEY, movie);
                startActivity(startChildActivityIntent);
            }
        });
    }

    private String[] getImagesURLs(){
        String[] urls = new String[mMovies.size()];
        for (int i = 0; i < mMovies.size(); i++){
            urls[i] = TMDBUtils.IMAGE_BASE_URL + TMDBUtils.IMAGE_SIZE_W185 + mMovies.get(i).getPosterPath();
        }
        return urls;
    }

    @Override
    public void processFinish(Object output) {
        if(output != null){
            mMovies = (List<MovieVO>) output;
            populateMoviesGridView();
        }else{
            Toast.makeText(this, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
    }
}
