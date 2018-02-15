package br.com.ud851.popmoviesst1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView tv_movie_title;
    private TextView tv_year;
    private TextView tv_score;
    private TextView tv_overview;
    private ImageView iv_movie_cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        tv_movie_title = (TextView) findViewById(R.id.tv_movie_title);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_overview = (TextView) findViewById(R.id.tv_overview);
        iv_movie_cover = (ImageView) findViewById(R.id.iv_movie_cover);
        Intent intent = getIntent();

        if(intent.hasExtra(Movie.TITLE)){
            tv_movie_title.setText(intent.getStringExtra(Movie.TITLE));
            tv_year.setText(intent.getStringExtra(Movie.RELEASE_DATE));
            tv_score.setText(intent.getStringExtra(Movie.VOTE_AVERAGE));
            tv_overview.setText(intent.getStringExtra(Movie.OVERVIEW));
            Picasso.with(this).load(NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_SIZE_W185 + intent.getStringExtra(Movie.POSTER_PATH)).into(iv_movie_cover);
        }
    }
}
