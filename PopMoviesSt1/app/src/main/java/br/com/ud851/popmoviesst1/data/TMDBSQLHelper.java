package br.com.ud851.popmoviesst1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.ud851.popmoviesst1.data.contracts.TMDBContract;

/**
 * Created by Herlygenes Pinto on 21/02/2018.
 */

public class TMDBSQLHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "tmdb.db";
    private static final int DATABASE_VERSION = 1;

    public TMDBSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE "                                 + TMDBContract.TabMovies.TABLE_NAME + " ("    +
                TMDBContract.TabMovies._ID                      + " INTEGER PRIMARY KEY AUTOINCREMENT, "      +
                TMDBContract.TabMovies.COLUMN_MOVIE_ID          + " INTEGER NOT NULL, "                       +
                TMDBContract.TabMovies.COLUMN_TITLE             + " TEXT NOT NULL, "                          +
                TMDBContract.TabMovies.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, "                          +
                TMDBContract.TabMovies.COLUMN_POPULARITY        + " REAL NOT NULL, "                          +
                TMDBContract.TabMovies.COLUMN_VOTE_AVERAGE      + " REAL NOT NULL, "                          +
                TMDBContract.TabMovies.COLUMN_OVERVIEW          + " TEXT NOT NULL, "                          +
                TMDBContract.TabMovies.COLUMN_POSTER_PATH       + " TEXT NOT NULL, "                          +
                TMDBContract.TabMovies.COLUMN_RELEASE_DATE      + " TEXT NOT NULL)"
        ;

        final String SQL_CREATE_TRAILERS_TABLE =
                "CREATE TABLE "                                 + TMDBContract.TabTrailers.TABLE_NAME + " ("  +
                TMDBContract.TabTrailers._ID                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "      +
                TMDBContract.TabTrailers.COLUMN_TRAILER_ID      + " INTEGER NOT NULL, "                       +
                TMDBContract.TabTrailers.COLUMN_MOVIE_ID        + " INTEGER NOT NULL, "                       +
                TMDBContract.TabTrailers.COLUMN_NAME            + " TEXT NOT NULL, "                          +
                TMDBContract.TabTrailers.COLUMN_ISO_639_1       + " TEXT NOT NULL, "                          +
                TMDBContract.TabTrailers.COLUMN_ISO_3166_1      + " REAL NOT NULL, "                          +
                TMDBContract.TabTrailers.COLUMN_KEY             + " TEXT NOT NULL, "                          +
                TMDBContract.TabTrailers.COLUMN_TYPE            + " TEXT NOT NULL, "                          +
                TMDBContract.TabTrailers.COLUMN_SITE            + " TEXT NOT NULL, "                          +
                TMDBContract.TabTrailers.COLUMN_SIZE            + " TEXT NOT NULL, FOREIGN KEY ("             +
                TMDBContract.TabTrailers.COLUMN_MOVIE_ID        + ") REFERENCES "                             +
                TMDBContract.TabMovies.TABLE_NAME               + "("                                         +
                TMDBContract.TabTrailers.COLUMN_MOVIE_ID        + "));"
        ;

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL_DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS ";

        sqLiteDatabase.execSQL(SQL_DROP_TABLE_STATEMENT + TMDBContract.TabTrailers.TABLE_NAME);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_STATEMENT + TMDBContract.TabMovies.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
