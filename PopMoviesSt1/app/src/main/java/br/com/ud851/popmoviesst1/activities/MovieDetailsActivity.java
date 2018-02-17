package br.com.ud851.popmoviesst1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.data.Movie;
import br.com.ud851.popmoviesst1.utils.NetworkUtils;

public class MovieDetailsActivity extends AppCompatActivity {
    private Movie movie;
    private TextView tv_movie_title;
    private TextView tv_year;
    private TextView tv_score;
    private TextView tv_overview;
    private ImageView iv_movie_cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String posterPath;
        setContentView(R.layout.activity_movie_details);
        tv_movie_title = (TextView) findViewById(R.id.tv_movie_title);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_overview = (TextView) findViewById(R.id.tv_overview);
        iv_movie_cover = (ImageView) findViewById(R.id.iv_movie_cover);
        Intent intent = getIntent();

        if(intent.hasExtra(Movie.PARCELABLE_KEY)){
            movie = intent.getParcelableExtra(Movie.PARCELABLE_KEY);
            posterPath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_SIZE_W185 + movie.getPosterPath();
            Log.i("POSTER_URL", posterPath);
            tv_movie_title.setText(movie.getTitle());
            tv_year.setText(movie.getReleaseDate());
            tv_score.setText(movie.getVoteAverage());
            tv_overview.setText(movie.getOverview());
            Picasso.with(this).load(posterPath).into(iv_movie_cover);
        }
    }
}
