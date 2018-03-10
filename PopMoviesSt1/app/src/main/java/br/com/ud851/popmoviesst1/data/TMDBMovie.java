package br.com.ud851.popmoviesst1.data;

import java.util.List;

import br.com.ud851.popmoviesst1.data.vos.MovieVO;
import br.com.ud851.popmoviesst1.data.vos.ReviewVO;
import br.com.ud851.popmoviesst1.data.vos.TrailerVO;

/**
 * Created by Herlygenes Pinto on 04/03/2018.
 */

public class TMDBMovie extends MovieVO {

    private List<ReviewVO> mReviews;
    private List<TrailerVO> mTrailers;

    public TMDBMovie(MovieVO movie) {
        super(movie.getId(), movie.getVoteAverage(), movie.getTitle(), movie.getPopularity(), movie.getPosterPath(), movie.getOriginalLanguage(), movie.getOverview(), movie.getReleaseDate());
    }

    public List<ReviewVO> getmReviews() {
        return mReviews;
    }

    public void setmReviews(List<ReviewVO> mReviews) {
        this.mReviews = mReviews;
    }

    public List<TrailerVO> getmTrailers() {
        return mTrailers;
    }

    public void setmTrailers(List<TrailerVO> mTrailers) {
        this.mTrailers = mTrailers;
    }
}
