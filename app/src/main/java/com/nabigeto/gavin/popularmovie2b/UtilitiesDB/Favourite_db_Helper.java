package com.nabigeto.gavin.popularmovie2b.UtilitiesDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract.MovieInfo;

/**
 * Created by Gavin on 3/8/2016.
 */
public class Favourite_db_Helper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME ="favourite_database_b.db";


    public Favourite_db_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        Cursor c = sqLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + MovieInfo.TABLE_NAME + "'", null);

        if (c != null) {
            if (c.getCount() > 0) {
                Log.v("Gavin", "Table exists");
                c.close();
                sqLiteDatabase.delete(MovieInfo.TABLE_NAME, null, null);
            }

            c.close();
        }



        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieInfo.TABLE_NAME + " (" +

                MovieInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

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
                MovieInfo.COLUMN_NAME_REVIEW_AUTHOR1 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_REVIEW_AUTHOR2 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_REVIEW_AUTHOR3 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_TRAILER1 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_TRAILER2 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_NAME_TRAILER3 + " TEXT NOT NULL, " +
                MovieInfo.COLUMN_FAVOURITE + " TEXT NOT NULL " + ");";


        Log.v("Gavin", "Got to this bit in db Helper 2");



  /**      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieInfo.TABLE_NAME);
**/
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);

    }


    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieInfo.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

}
