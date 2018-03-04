package br.com.ud851.popmoviesst1.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.adapters.TrailerAdapter;
import br.com.ud851.popmoviesst1.data.contracts.TMDBContract;
import br.com.ud851.popmoviesst1.data.providers.TMDBContentProvider;
import br.com.ud851.popmoviesst1.data.vos.MovieVO;
import br.com.ud851.popmoviesst1.data.vos.TrailerVO;
import br.com.ud851.popmoviesst1.interfaces.AsyncTaskDelegate;
import br.com.ud851.popmoviesst1.services.TheMovieDatabaseService;
import br.com.ud851.popmoviesst1.utils.StateHolder;
import br.com.ud851.popmoviesst1.utils.TMDBUtils;
import br.com.ud851.popmoviesst1.utils.YoutubeUtils;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public class MovieDetailsActivity extends AppCompatActivity implements AsyncTaskDelegate {

    private String SCORE_RATIO = "/10";

    private MovieVO movie;
    private List<TrailerVO> trailers = new ArrayList<>();
    private TextView tv_movie_title;
    private TextView tv_year;
    private TextView tv_score;
    private TextView tv_overview;
    private ImageView iv_movie_cover;
    private AppCompatImageButton ib_favorite;

    private boolean inFavorites;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = MovieDetailsActivity.this;

        StateHolder.setLastActivity(MovieDetailsActivity.class.getName());

        super.onCreate(savedInstanceState);
        String posterPath;
        setContentView(R.layout.activity_movie_details);
        tv_movie_title = (TextView) findViewById(R.id.tv_movie_title);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_overview = (TextView) findViewById(R.id.tv_overview);
        iv_movie_cover = (ImageView) findViewById(R.id.iv_movie_cover);
        Intent intent = getIntent();

        if(intent.hasExtra(MovieVO.PARCELABLE_KEY)){
            movie = intent.getParcelableExtra(MovieVO.PARCELABLE_KEY);
            posterPath = TMDBUtils.IMAGE_BASE_URL + TMDBUtils.IMAGE_SIZE_W185 + movie.getPosterPath();
            Log.i("POSTER_URL", posterPath);
            tv_movie_title.setText(movie.getTitle());
            tv_year.setText(movie.getReleaseDate().substring(0, 4));
            tv_score.setText(movie.getVoteAverage() + SCORE_RATIO);
            tv_overview.setText(movie.getOverview());
            Picasso.with(this).load(posterPath).into(iv_movie_cover);
        }

        getTrailerDataFromTMDB();
        setUpFavoriteButton();
    }

    private void checkFavorites(){
        Uri uri = TMDBContract.TabMovies.CONTENT_URI;
        uri = uri.buildUpon().appendPath(movie.getId()).build();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        inFavorites = TMDBContentProvider.getMoviesFromCursor(cursor).size() > 0;

        ib_favorite.setImageDrawable(inFavorites ?
                getResources().getDrawable(R.drawable.star_pressed) :
                getResources().getDrawable(R.drawable.star_unpressed));

        cursor.close();
    }

    private void setUpFavoriteButton(){
        ib_favorite = (AppCompatImageButton) findViewById(R.id.bt_favorites);
        checkFavorites();
        ib_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inFavorites){
                    markMovieAsFavorite();
                } else {
                    unmarkMovieAsFavorite();
                }
            }
        });
    }

    private void markMovieAsFavorite(){
        Uri uri = getContentResolver().insert(TMDBContract.TabMovies.CONTENT_URI, TMDBContentProvider.getContentValuesFromMovie(this.movie));
        if(uri != null){
            inFavorites = true;
            ib_favorite.setImageDrawable(getResources().getDrawable(R.drawable.star_pressed));
            Toast.makeText(MovieDetailsActivity.this, movie.getTitle() + getString(R.string.toast_added_to_favorites), Toast.LENGTH_LONG).show();
        }
    }

    private void unmarkMovieAsFavorite(){
        Uri uri = TMDBContract.TabMovies.CONTENT_URI;
        uri = uri.buildUpon().appendPath(movie.getId()).build();
        int itemDeleted = getContentResolver().delete(uri, null, null);
        if(itemDeleted > 0){
            inFavorites = false;
            ib_favorite.setImageDrawable(getResources().getDrawable(R.drawable.star_unpressed));
            Toast.makeText(MovieDetailsActivity.this, movie.getTitle() + getString(R.string.toast_removed_from_favorites), Toast.LENGTH_LONG).show();
        }
    }

    private void getTrailerDataFromTMDB(){
        String[] args = {TMDBUtils.GET_MOVIE_TRAILERS, movie.getId()};
        TheMovieDatabaseService service = new TheMovieDatabaseService(context, this);
        service.execute(args);
    }

    @Override
    public void processFinish(Object output) {
        if(output != null){
            trailers = (List<TrailerVO>) output;
            ListView lvTrailer = (ListView) findViewById(R.id.trailer_list_view);

            TrailerAdapter trailerAdapter = new TrailerAdapter(this, trailers);

            lvTrailer.setAdapter(trailerAdapter);

            lvTrailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TrailerVO trailer = trailers.get(position);
                    YoutubeUtils.watchYoutubeVideo(MovieDetailsActivity.this, trailer.getKey());
                }
            });
        }else{
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }
    }

}
