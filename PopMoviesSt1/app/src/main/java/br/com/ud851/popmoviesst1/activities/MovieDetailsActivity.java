package br.com.ud851.popmoviesst1.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.adapters.ReviewAdapter;
import br.com.ud851.popmoviesst1.adapters.TrailerAdapter;
import br.com.ud851.popmoviesst1.data.TMDBMovie;
import br.com.ud851.popmoviesst1.data.contracts.TMDBContract;
import br.com.ud851.popmoviesst1.data.providers.TMDBContentProvider;
import br.com.ud851.popmoviesst1.data.vos.MovieVO;
import br.com.ud851.popmoviesst1.data.vos.ReviewVO;
import br.com.ud851.popmoviesst1.data.vos.TrailerVO;
import br.com.ud851.popmoviesst1.interfaces.AsyncTaskDelegate;
import br.com.ud851.popmoviesst1.services.TheMovieDatabaseService;
import br.com.ud851.popmoviesst1.utils.LayoutUtils;
import br.com.ud851.popmoviesst1.utils.StateHolder;
import br.com.ud851.popmoviesst1.utils.TMDBUtils;
import br.com.ud851.popmoviesst1.utils.YoutubeUtils;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public class MovieDetailsActivity extends AppCompatActivity implements AsyncTaskDelegate {

    private static String SCORE_RATIO = "/10";
    private static String CURRENT_SERVICE;
    private static String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    private TMDBMovie mMovie;
    private TextView mTvMovieTitle;
    private TextView mTvYear;
    private TextView mTvScore;
    private TextView mTvOverview;
    private ImageView mIvMovieCover;
    private AppCompatImageButton mIbFavorite;

    private boolean mInFavorites;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CURRENT_SERVICE = "";

        mContext = MovieDetailsActivity.this;

        StateHolder.setsLastActivity(MovieDetailsActivity.class.getName());

        super.onCreate(savedInstanceState);
        String posterPath;
        setContentView(R.layout.activity_movie_details);
        mTvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        mTvYear = (TextView) findViewById(R.id.tv_year);
        mTvScore = (TextView) findViewById(R.id.tv_score);
        mTvOverview = (TextView) findViewById(R.id.tv_overview);
        mIvMovieCover = (ImageView) findViewById(R.id.iv_movie_cover);
        Intent intent = getIntent();

        if(intent.hasExtra(MovieVO.PARCELABLE_KEY)){
            mMovie = new TMDBMovie((MovieVO)intent.getParcelableExtra(MovieVO.PARCELABLE_KEY));
            posterPath = TMDBUtils.IMAGE_BASE_URL + TMDBUtils.IMAGE_SIZE_W185 + mMovie.getPosterPath();
            Log.i(LOG_TAG, posterPath);
            mTvMovieTitle.setText(mMovie.getTitle());
            mTvYear.setText(mMovie.getReleaseDate().substring(0, 4));
            mTvScore.setText(mMovie.getVoteAverage() + SCORE_RATIO);
            mTvOverview.setText(mMovie.getOverview());
            Picasso.with(this).load(posterPath).into(mIvMovieCover);
            getTrailersFromTMDB();
            setUpFavoriteButton();
        }
    }

    private void checkFavorites(){
        Uri uri = TMDBContract.TabMovies.CONTENT_URI;
        uri = uri.buildUpon().appendPath(mMovie.getId()).build();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        mInFavorites = TMDBContentProvider.getMoviesFromCursor(cursor).size() > 0;

        mIbFavorite.setImageDrawable(mInFavorites ?
                getResources().getDrawable(R.drawable.star_pressed) :
                getResources().getDrawable(R.drawable.star_unpressed));

        cursor.close();
    }

    private void setUpFavoriteButton(){
        mIbFavorite = (AppCompatImageButton) findViewById(R.id.bt_favorites);
        checkFavorites();
        mIbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mInFavorites){
                    markMovieAsFavorite();
                } else {
                    unmarkMovieAsFavorite();
                }
            }
        });
    }

    private void markMovieAsFavorite(){
        Uri uri = getContentResolver().insert(TMDBContract.TabMovies.CONTENT_URI, TMDBContentProvider.getContentValuesFromMovie(this.mMovie));
        if(uri != null){
            mInFavorites = true;
            mIbFavorite.setImageDrawable(getResources().getDrawable(R.drawable.star_pressed));
            Toast.makeText(MovieDetailsActivity.this, mMovie.getTitle() + getString(R.string.toast_added_to_favorites), Toast.LENGTH_LONG).show();
        }
    }

    private void unmarkMovieAsFavorite(){
        Uri uri = TMDBContract.TabMovies.CONTENT_URI;
        uri = uri.buildUpon().appendPath(mMovie.getId()).build();
        int itemDeleted = getContentResolver().delete(uri, null, null);
        if(itemDeleted > 0){
            mInFavorites = false;
            mIbFavorite.setImageDrawable(getResources().getDrawable(R.drawable.star_unpressed));
            Toast.makeText(MovieDetailsActivity.this, mMovie.getTitle() + getString(R.string.toast_removed_from_favorites), Toast.LENGTH_LONG).show();
        }
    }

    private void getTrailersFromTMDB(){
        CURRENT_SERVICE = TMDBUtils.GET_MOVIE_TRAILERS;
        String[] args = {TMDBUtils.GET_MOVIE_TRAILERS, mMovie.getId()};
        TheMovieDatabaseService service = new TheMovieDatabaseService(mContext, this);
        service.execute(args);
    }

    private void getReviewsFromTMDB(){
        CURRENT_SERVICE = TMDBUtils.GET_MOVIE_COMMENTS;
        String[] args = {TMDBUtils.GET_MOVIE_COMMENTS, mMovie.getId()};
        TheMovieDatabaseService service = new TheMovieDatabaseService(mContext, this);
        service.execute(args);
    }

    @Override
    public void processFinish(Object output) {
        if(output != null){
            if(CURRENT_SERVICE.equals(TMDBUtils.GET_MOVIE_TRAILERS)){
                mMovie.setmTrailers((List<TrailerVO>) output);
                ListView lvTrailer = (ListView) findViewById(R.id.trailer_list_view);

                TrailerAdapter trailerAdapter = new TrailerAdapter(this, mMovie.getmTrailers());

                lvTrailer.setAdapter(trailerAdapter);

                lvTrailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TrailerVO trailer = mMovie.getmTrailers().get(position);
                        YoutubeUtils.watchYoutubeVideo(MovieDetailsActivity.this, trailer.getKey());
                    }
                });

                LayoutUtils.setListViewHeightBasedOnChildren(lvTrailer);
                getReviewsFromTMDB();
            } else {
                mMovie.setmReviews((List<ReviewVO>) output);
                ListView lvReview = (ListView) findViewById(R.id.reviews_list_view);

                ReviewAdapter reviewAdapter = new ReviewAdapter(this, mMovie.getmReviews());

                lvReview.setAdapter(reviewAdapter);

                lvReview.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });

                LayoutUtils.setListViewHeightBasedOnChildren(lvReview);
            }
        }else{
            Toast.makeText(this, R.string.toast_no_internet, Toast.LENGTH_LONG).show();
        }
    }
}
