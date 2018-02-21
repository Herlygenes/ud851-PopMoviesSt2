package br.com.ud851.popmoviesst1.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.R;
import br.com.ud851.popmoviesst1.adapters.TrailerAdapter;
import br.com.ud851.popmoviesst1.data.Movie;
import br.com.ud851.popmoviesst1.data.Trailer;
import br.com.ud851.popmoviesst1.interfaces.AsyncTaskDelegate;
import br.com.ud851.popmoviesst1.services.TheMovieDatabaseService;
import br.com.ud851.popmoviesst1.utils.TMDBUtils;
import br.com.ud851.popmoviesst1.utils.YoutubeUtils;

/**
 * Created by Herlygenes Pinto on 09/12/2017.
 */

public class MovieDetailsActivity extends AppCompatActivity implements AsyncTaskDelegate {
    private Movie movie;
    private List<Trailer> trailers = new ArrayList<>();
    private TextView tv_movie_title;
    private TextView tv_year;
    private TextView tv_score;
    private TextView tv_overview;
    private ImageView iv_movie_cover;
    private ImageButton ib_play_trailer;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = MovieDetailsActivity.this;

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
            posterPath = TMDBUtils.IMAGE_BASE_URL + TMDBUtils.IMAGE_SIZE_W185 + movie.getPosterPath();
            Log.i("POSTER_URL", posterPath);
            tv_movie_title.setText(movie.getTitle());
            tv_year.setText(movie.getReleaseDate());
            tv_score.setText(movie.getVoteAverage());
            tv_overview.setText(movie.getOverview());
            Picasso.with(this).load(posterPath).into(iv_movie_cover);
        }

        getTrailerDataFromTMDB();
    }

    private void getTrailerDataFromTMDB(){
        String[] args = {TMDBUtils.GET_MOVIE_TRAILERS, movie.getId()};
        TheMovieDatabaseService service = new TheMovieDatabaseService(context, this);
        service.execute(args);
    }

    private ArrayList<String> getYoutubeUrls(){
        ArrayList<String> urls = new ArrayList<>();
        for (Trailer trailer : trailers){
            urls.add(YoutubeUtils.YOUTUBE_BASE_URL + trailer.getKey());
        }
        return urls;
    }

    @Override
    public void processFinish(Object output) {
        if(output != null){
            trailers = (List<Trailer>) output;
            ListView lvTrailer = (ListView) findViewById(R.id.trailer_list_view);

            TrailerAdapter trailerAdapter = new TrailerAdapter(this, trailers);

            lvTrailer.setAdapter(trailerAdapter);

            lvTrailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Trailer trailer = trailers.get(position);
                    YoutubeUtils.watchYoutubeVideo(MovieDetailsActivity.this, trailer.getKey());
                }
            });
        }else{
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }
    }

}
