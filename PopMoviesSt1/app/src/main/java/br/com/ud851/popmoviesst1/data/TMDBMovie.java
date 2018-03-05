package br.com.ud851.popmoviesst1.data;

import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.data.vos.MovieVO;
import br.com.ud851.popmoviesst1.data.vos.ReviewVO;
import br.com.ud851.popmoviesst1.data.vos.TrailerVO;

/**
 * Created by Herlygenes Pinto on 04/03/2018.
 */

public class TMDBMovie extends MovieVO {

    private List<ReviewVO> reviews;
    private List<TrailerVO> trailers;

    public TMDBMovie(String id, String voteAverage, String title, String popularity, String posterPath, String originalLanguage, String overview, String releaseDate) {
        super(id, voteAverage, title, popularity, posterPath, originalLanguage, overview, releaseDate);
        this.reviews = new ArrayList<>();
        this.trailers = new ArrayList<>();
    }

    public TMDBMovie(MovieVO movie) {
        super(movie.getId(), movie.getVoteAverage(), movie.getTitle(), movie.getPopularity(), movie.getPosterPath(), movie.getOriginalLanguage(), movie.getOverview(), movie.getReleaseDate());
    }

    public TMDBMovie() {}

    public List<ReviewVO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewVO> reviews) {
        this.reviews = reviews;
    }

    public List<TrailerVO> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailerVO> trailers) {
        this.trailers = trailers;
    }
}
