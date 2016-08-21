package com.nabigeto.gavin.popularmovie2b.UtilitiesDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract.MovieInfo;

/**
 * Created by Gavin on 3/8/2016.
 */
public class Review_db_Helper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME ="movie_database_b.db";


    public Review_db_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
/**
        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " + Movie_Contract.Favourites.TABLE_NAME + " (" +

                Movie_Contract.Favourites._ID + " INTEGER PRIMARY KEY," +

                Movie_Contract.Favourites.COLUMN_NAME_TITLE_F + " TEXT NOT NULL, " +
                Movie_Contract.Favourites.COLUMN_NAME_FAVOURITE + " TEXT NOT NULL " +

                " );";

            Log.v("Gavin", "Got to this bit in the db Helper 1");
**/
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieInfo.TABLE_NAME + " (" +

                MovieInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

      /**          MovieInfo.COLUMN_NAME_ENTRY_KEY + " INT NOT NULL, " +  **/
                MovieInfo.COLUMN_NAME_ENTRY_ID + " INT NOT NULL, " +
                MovieInfo.COLUMN_NAME_MOVIE_ID + " INT NOT NULL, " +
                MovieInfo.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_RATING + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_INFO + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_IMAGE_FILE + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_REVIEW1 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_REVIEW2 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_REVIEW3 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_TRAILER1 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_TRAILER2 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_TRAILER3 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_FAVOURITE + " TEXT NOT NULL " + ");";

/**
                " FOREIGN KEY (" + MovieInfo.COLUMN_NAME_ENTRY_KEY + ") REFERENCES " +
                Movie_Contract.Favourites.TABLE_NAME + " (" + Movie_Contract.Favourites._ID + "), " +

                " UNIQUE (" + Movie_Contract.MovieInfo.COLUMN_NAME_TITLE + ", " +
                MovieInfo.COLUMN_NAME_MOVIE_ID + ") ON CONFLICT REPLACE);";
        **/
        Log.v("Gavin", "Got to this bit in db Helper 2");

/**        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITES_TABLE); **/
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);

    }


    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){

  /**      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Movie_Contract.Favourites.TABLE_NAME); **/
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieInfo.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

}
