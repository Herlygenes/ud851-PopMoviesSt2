package br.com.ud851.popmoviesst1.services.rest;

import br.com.ud851.popmoviesst1.data.responses.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Herlygenes Pinto on 10/03/2018.
 */

public interface TMDBApiInterface {
    final static String MOVIE_ID = "id";
    final static String IMAGE_ID = "image_id";
    final static String IMAGE_SIZE = "image_size";
    final static String API_KEY = "api_key";
    final static String TOP_RATED = "movie/top_rated";
    final static String POPULAR = "movie/popular";
    final static String MOVIE_DETAILS = "movie/{id}";
    final static String MOVIE_REVIEWS = "movie/{id}/reviews";
    final static String MOVIE_TRAILERS = "movie/{id}/videos";
    final static String MOVIE_IMAGE = "{"+IMAGE_SIZE+"}/{"+IMAGE_ID+"}";

    @GET(TOP_RATED)
    Call<MovieResponse> getTopRatedMovies(@Query(API_KEY) String apiKey);

    @GET(POPULAR)
    Call<MovieResponse> getPopularMovies(@Query(API_KEY) String apiKey);

    @GET(MOVIE_DETAILS)
    Call<MovieResponse> getMovieDetails(@Path(MOVIE_ID) int id, @Query(API_KEY) String apiKey);

    @GET(MOVIE_REVIEWS)
    Call<MovieResponse> getMovieReviews(@Path(MOVIE_ID) int id, @Query(API_KEY) String apiKey);

    @GET(MOVIE_TRAILERS)
    Call<MovieResponse> getMovieTrailers(@Path(MOVIE_ID) int id, @Query(API_KEY) String apiKey);

    @GET(MOVIE_IMAGE)
    Call<MovieResponse> getMovieImage(@Path(IMAGE_SIZE) String imageSize, @Path(IMAGE_ID) String imageId);

}
