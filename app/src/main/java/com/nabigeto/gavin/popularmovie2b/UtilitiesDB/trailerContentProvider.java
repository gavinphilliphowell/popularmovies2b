package com.nabigeto.gavin.popularmovie2b.UtilitiesDB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Gavin on 3/29/2016.
 */
public class trailerContentProvider extends ContentProvider {

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private Movie_db_Helper mOpenHelper;

    static final int MOVIE_INFO = 101;

    private static final String AUTHORITY = Movie_Contract.CONTENT_AUTHORITY_R;

    private static final SQLiteQueryBuilder sMovie_InfoQueryBuilder;

    static {

        sMovie_InfoQueryBuilder = new SQLiteQueryBuilder();

        sMovie_InfoQueryBuilder.setTables(
                Movie_Contract.MovieInfo.TABLE_NAME);


    }

    private static final String sMovie_InfoSettingSelection = Movie_Contract.MovieInfo.TABLE_NAME + " = ? ";

    
    private Cursor getMovie_Info(Uri uri, String[] projection, String sortOrder) {

        String Movie_Info_Setting = Movie_Contract.MovieInfo.getMovieNameFromUri(uri);


        String[] selectionArgs;
        String selection;

        selection = sMovie_InfoSettingSelection;
        selectionArgs = new String[]{Movie_Info_Setting};


        return sMovie_InfoQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);


    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Movie_Contract.CONTENT_AUTHORITY_R;

        matcher.addURI(authority, Movie_Contract.PATH_MOVIE, MOVIE_INFO);

        return matcher;

    }


    @Override
    public boolean onCreate(){
        mOpenHelper = new Movie_db_Helper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int typeUri = sUriMatcher.match(uri);

        switch (typeUri) {

            case MOVIE_INFO:
                return Movie_Contract.MovieInfo.CONTENT_TYPE_R;

            default:
                throw new UnsupportedOperationException("Unknown URI + 1" + uri);
        }
    }

        @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){

        Cursor retCursor;

        String suri = uri.toString();

        switch (sUriMatcher.match(uri)) {

            case MOVIE_INFO:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        Movie_Contract.MovieInfo.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder

                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: + 2" + uri);

        }

        retCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues){

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch(match) {

            case MOVIE_INFO: {

                long _id = db.insert(Movie_Contract.MovieInfo.TABLE_NAME, null, contentValues);
                if (_id > 0)
                    returnUri = Movie_Contract.MovieInfo.buildMovie_InfoUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri + 3" + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);


        return returnUri;
    }

    @Override
    public int update(Uri uri, ContentValues contentvalues, String selection , String[] selectionArgs){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch(match){

            case MOVIE_INFO:{
                rowsUpdated = db.update(Movie_Contract.MovieInfo.TABLE_NAME, contentvalues, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri + 4" + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String [] selectionArgs){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) selection ="1";

        switch(match){

            case MOVIE_INFO:{
                rowsDeleted = db.delete(Movie_Contract.MovieInfo.TABLE_NAME, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri + 5" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        db.close();
        return rowsDeleted;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        String smatch = Integer.toString(match);

        switch(match){
            case MOVIE_INFO:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values){
                        long _id = db.insert(Movie_Contract.MovieInfo.TABLE_NAME, null, value);
                        if(_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                }
                finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
        }

        return super.bulkInsert(uri, values);

        }

    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
    }


