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
public abstract class reviewContentProvider extends ContentProvider {

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private Movie_db_Helper mOpenHelper;

    static final int MOVIE_INFO = 100;
    static final int MOVIE_INFO_WITH_FAVOURITE = 101;
    static final int FAVOURITE = 200;

    private static final SQLiteQueryBuilder sMovie_Info_FavouritesQueryBuilder;

    static {

        sMovie_Info_FavouritesQueryBuilder = new SQLiteQueryBuilder();

        sMovie_Info_FavouritesQueryBuilder.setTables(
                Favourite_Contract.FavouriteInfo.TABLE_NAME);


    }

    private static final String sFavourite_InfoSettingSelection = Favourite_Contract.FavouriteInfo.TABLE_NAME + " = ? ";

    private static final String sMovie_Info_FavouritesSettingSelection = Favourite_Contract.FavouriteInfo.TABLE_NAME + "."
            + Favourite_Contract.FavouriteInfo._ID + " = ? AND " +
            Favourite_Contract.FavouriteInfo._ID + " + ? ";


    private Cursor getFavourite_Info(Uri uri, String[] projection, String sortOrder) {

        String Movie_Info_Setting = Favourite_Contract.FavouriteInfo.getFavouriteNameFromUri(uri);


        String[] selectionArgs;
        String selection;

        selection = sFavourite_InfoSettingSelection;
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
        final String authority = Favourite_Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Favourite_Contract.PATH_FAVOURITE, FAVOURITE);

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
                return Favourite_Contract.FavouriteInfo.CONTENT_TYPE;

            case MOVIE_INFO_WITH_FAVOURITE:
                return Favourite_Contract.FavouriteInfo.CONTENT_ITEM_TYPE;

            case FAVOURITE:
                return Favourite_Contract.FavouriteInfo.CONTENT_TYPE;

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
                        Favourite_Contract.FavouriteInfo.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case MOVIE_INFO_WITH_FAVOURITE:
                retCursor = getFavourite_Info(uri, projection, sortOrder);


                break;

            case FAVOURITE:
                retCursor = mOpenHelper.getReadableDatabase().query(
                Favourite_Contract.FavouriteInfo.TABLE_NAME,
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

                long _id = db.insert(Favourite_Contract.FavouriteInfo.TABLE_NAME, null, contentValues);
                Log.v("Gavin", "Inserting");
                if (_id > 0)
                    returnUri = Favourite_Contract.FavouriteInfo.buildFavourite_InfoUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                break;
            }

            case FAVOURITE: {

                long _id = db.insert(Favourite_Contract.FavouriteInfo.TABLE_NAME, null, contentValues);
                Log.v("Gavin", "Inserting");
                if (_id > 0)
                    returnUri = Favourite_Contract.FavouriteInfo.buildFavourite_InfoUri(_id);
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
                rowsUpdated = db.update(Favourite_Contract.FavouriteInfo.TABLE_NAME, contentvalues, selection, selectionArgs);
                break;
            }

            case FAVOURITE:{
                rowsUpdated = db.update(Favourite_Contract.FavouriteInfo.TABLE_NAME, contentvalues, selection, selectionArgs);
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
                rowsDeleted = db.delete(Favourite_Contract.FavouriteInfo.TABLE_NAME, selection, selectionArgs);
                break;
            }

            case FAVOURITE:{
                rowsDeleted = db.delete(Favourite_Contract.FavouriteInfo.TABLE_NAME, selection, selectionArgs);
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
                        long _id = db.insert(Favourite_Contract.FavouriteInfo.TABLE_NAME, null, value);
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


