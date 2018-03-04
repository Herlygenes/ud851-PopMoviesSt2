package br.com.ud851.popmoviesst1.data.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.ud851.popmoviesst1.data.TMDBSQLHelper;
import br.com.ud851.popmoviesst1.data.contracts.TMDBContract;
import br.com.ud851.popmoviesst1.data.vos.MovieVO;

import static br.com.ud851.popmoviesst1.data.contracts.TMDBContract.TabMovies.TABLE_NAME;

/**
 * Created by Herlygenes Pinto on 21/02/2018.
 */

public class TMDBContentProvider extends ContentProvider {
    Context context;
    private TMDBSQLHelper mTMDBSQLHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int MOVIES = 100;
    private static final int MOVIES_WITH_ID = 101;
    private static final int TRAILERS = 200;
    private static final int TRAILERS_WITH_ID = 201;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(TMDBContract.CONTENT_AUTHORITY, TMDBContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(TMDBContract.CONTENT_AUTHORITY, TMDBContract.PATH_MOVIES + "/#", MOVIES_WITH_ID);

        uriMatcher.addURI(TMDBContract.CONTENT_AUTHORITY, TMDBContract.PATH_TRAILERS, TRAILERS);
        uriMatcher.addURI(TMDBContract.CONTENT_AUTHORITY, TMDBContract.PATH_TRAILERS + "/#", TRAILERS_WITH_ID);

        return  uriMatcher;
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        mTMDBSQLHelper = new TMDBSQLHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mTMDBSQLHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case MOVIES:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case MOVIES_WITH_ID:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        TMDBContract.TabMovies.COLUMN_MOVIE_ID + "=?",
                        new String[]{uri.getPathSegments().get(1)},
                        null,
                        null,
                        sortOrder);
                break;

            case TRAILERS:
                retCursor =  db.query(TMDBContract.TabTrailers.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mTMDBSQLHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        long id = 0;
        switch (match) {
            case MOVIES:
                id = db.insert(TABLE_NAME, null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(TMDBContract.TabMovies.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            case TRAILERS:
                id = db.insert(TMDBContract.TabTrailers.TABLE_NAME, null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(TMDBContract.TabTrailers.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mTMDBSQLHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        int itemDeleted;

        switch (match) {
            case MOVIES_WITH_ID:
                itemDeleted =  db.delete(TMDBContract.TabMovies.TABLE_NAME,TMDBContract.TabMovies.COLUMN_MOVIE_ID + "=?", new String[]{uri.getPathSegments().get(1)});
                break;

            case TRAILERS_WITH_ID:
                itemDeleted = db.delete(TMDBContract.TabTrailers.TABLE_NAME, TMDBContract.TabTrailers.COLUMN_TRAILER_ID + "=?", new String[]{uri.getPathSegments().get(1)});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (itemDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return itemDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static List<MovieVO> getMoviesFromCursor(Cursor cursor){
        List<MovieVO> movies = new ArrayList<>();
        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                movies.add(new MovieVO(
                        cursor.getString(cursor.getColumnIndex(TMDBContract.TabMovies.COLUMN_MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(TMDBContract.TabMovies.COLUMN_VOTE_AVERAGE)),
                        cursor.getString(cursor.getColumnIndex(TMDBContract.TabMovies.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(TMDBContract.TabMovies.COLUMN_POPULARITY)),
                        cursor.getString(cursor.getColumnIndex(TMDBContract.TabMovies.COLUMN_POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndex(TMDBContract.TabMovies.COLUMN_ORIGINAL_LANGUAGE)),
                        cursor.getString(cursor.getColumnIndex(TMDBContract.TabMovies.COLUMN_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(TMDBContract.TabMovies.COLUMN_RELEASE_DATE))
                ));
            }
        }
        return movies;
    }

    public static ContentValues getContentValuesFromMovie(MovieVO movie){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TMDBContract.TabMovies.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(TMDBContract.TabMovies.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        contentValues.put(TMDBContract.TabMovies.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(TMDBContract.TabMovies.COLUMN_POPULARITY, movie.getPopularity());
        contentValues.put(TMDBContract.TabMovies.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(TMDBContract.TabMovies.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(TMDBContract.TabMovies.COLUMN_TITLE, movie.getTitle());
        contentValues.put(TMDBContract.TabMovies.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        return contentValues;
    }

}
