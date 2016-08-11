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
public abstract class movieContentProvider1 extends ContentProvider {
/**
    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private Movie_db_Helper mOpenHelper;

    static final int MOVIE_INFO = 100;
    static final int MOVIE_INFO_WITH_FAVOURITE = 101;
    static final int FAVOURITE = 200;

    private static final SQLiteQueryBuilder sMovie_Info_FavouritesQueryBuilder;

    static {

        sMovie_Info_FavouritesQueryBuilder = new SQLiteQueryBuilder();

        sMovie_Info_FavouritesQueryBuilder.setTables(
                Movie_Contract.MovieInfo.TABLE_NAME + " INNER_JOIN " +
                        Movie_Contract.Favourites.TABLE_NAME +
                        " ON " + Movie_Contract.MovieInfo.TABLE_NAME +
                        "."  + Movie_Contract.MovieInfo.COLUMN_NAME_TITLE +
                        " = " + Movie_Contract.Favourites.TABLE_NAME +
                        "." + Movie_Contract.Favourites.COLUMN_NAME_TITLE_F);


    }

    private static final String sMovie_InfoSettingSelection = Movie_Contract.MovieInfo.TABLE_NAME + " = ? ";

    private static final String sMovie_Info_FavouritesSettingSelection = Movie_Contract.MovieInfo.TABLE_NAME + "."
            + Movie_Contract.MovieInfo._ID + " = ? AND " +
            Movie_Contract.Favourites._ID + " + ? ";


    private Cursor getMovie_Info(Uri uri, String[] projection, String sortOrder) {

        String Movie_Info_Setting = Movie_Contract.MovieInfo.getMovieNameFromUri(uri);
        String Favourites = Movie_Contract.Favourites.getFavouritesFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sMovie_InfoSettingSelection;
        selectionArgs = new String[]{Movie_Info_Setting};

        Log.v("Gavin", "in the provider");

        return sMovie_Info_FavouritesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);


    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Movie_Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Movie_Contract.PATH_MOVIE, MOVIE_INFO);
        matcher.addURI(authority, Movie_Contract.PATH_MOVIE + "/*", MOVIE_INFO_WITH_FAVOURITE);
        matcher.addURI(authority, Movie_Contract.PATH_FAVOURITE, FAVOURITE);

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
                return Movie_Contract.MovieInfo.CONTENT_TYPE;

            case MOVIE_INFO_WITH_FAVOURITE:
                return Movie_Contract.MovieInfo.CONTENT_ITEM_TYPE;

            case FAVOURITE:
                return Movie_Contract.Favourites.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown URI" + uri);
        }
    }

        @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){

        Cursor retCursor;

        String suri = uri.toString();
        Log.v("Gavin", "movieContentProvider" + suri);

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

            case MOVIE_INFO_WITH_FAVOURITE:
                retCursor = getMovie_Info(uri, projection, sortOrder);


                break;

            case FAVOURITE:
                retCursor = mOpenHelper.getReadableDatabase().query(
                Movie_Contract.Favourites.TABLE_NAME,
                projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
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
                Log.v("Gavin", "Inserting");
                if (_id > 0)
                    returnUri = Movie_Contract.MovieInfo.buildMovie_InfoUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                break;
            }

            case FAVOURITE: {

                long _id = db.insert(Movie_Contract.Favourites.TABLE_NAME, null, contentValues);
                Log.v("Gavin", "Inserting");
                if (_id > 0)
                    returnUri = Movie_Contract.Favourites.buildFavouriteUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);

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

            case FAVOURITE:{
                rowsUpdated = db.update(Movie_Contract.Favourites.TABLE_NAME, contentvalues, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
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

            case FAVOURITE:{
                rowsDeleted = db.delete(Movie_Contract.Favourites.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);


        return rowsDeleted;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){

        Log.v("Gavin", "Bulk insert underway");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Log.v("Gavin", "Bulk insert completed");
        final int match = sUriMatcher.match(uri);
        String smatch = Integer.toString(match);
        Log.v("Gavin", smatch);

        switch(match){
            case MOVIE_INFO:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values){
                        Log.v("Gavin", "Trying to insert" + value);
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
    }**/
    }


