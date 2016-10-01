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
public class favouriteContentProvider extends ContentProvider {
/**
    public static final UriMatcher sUriMatcher = buildUriMatcher();
**/
    private Movie_db_Helper mOpenHelper;

    private static final UriMatcher sUriMatcher;

    static final int FAVOURITE_INFO = 102;

    private static final String AUTHORITY = Favourite_Contract.CONTENT_AUTHORITY_F;


    private static final SQLiteQueryBuilder sMovie_InfoQueryBuilder;

    static {

        sMovie_InfoQueryBuilder = new SQLiteQueryBuilder();

        sMovie_InfoQueryBuilder.setTables(
                Favourite_Contract.FavouriteInfo.TABLE_NAME);


    }

    private static final String sMovie_InfoSettingSelection = Favourite_Contract.FavouriteInfo.TABLE_NAME + " = ? ";

    
    private Cursor getMovie_Info(Uri uri, String[] projection, String sortOrder) {

        String Favourite_Info_Setting = Favourite_Contract.FavouriteInfo.getMovieNameFromUri(uri);


        String[] selectionArgs;
        String selection;

        selection = sMovie_InfoSettingSelection;
        selectionArgs = new String[]{Favourite_Info_Setting};

        Log.v("Gavin", "in the provider");

        return sMovie_InfoQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);


    }

    static {

        sUriMatcher = new UriMatcher(0);

        sUriMatcher.addURI(
                favouriteContentProvider.AUTHORITY,
                "favourite",
                FAVOURITE_INFO
        );

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

            case FAVOURITE_INFO:
                return Favourite_Contract.FavouriteInfo.CONTENT_TYPE_F;

            default:
                throw new UnsupportedOperationException("Unknown URI + 1" + uri);
        }
    }

        @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){

        Cursor retCursor;

        String suri = uri.toString();
        Log.v("Gavin", "movieContentProvider" + suri);

        switch (sUriMatcher.match(uri)) {

            case FAVOURITE_INFO:
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
        Log.v("Gavin", "Tester 1");
        switch(match) {

            case FAVOURITE_INFO: {

                long _id = db.insert(Favourite_Contract.FavouriteInfo.TABLE_NAME, null, contentValues);
                Log.v("Gavin", "Inserting");
                if (_id > 0)
                    returnUri = Favourite_Contract.FavouriteInfo.buildMovie_InfoUri_F(_id);
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

            case FAVOURITE_INFO:{
                rowsUpdated = db.update(Favourite_Contract.FavouriteInfo.TABLE_NAME, contentvalues, selection, selectionArgs);
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

            case FAVOURITE_INFO:{
                rowsDeleted = db.delete(Favourite_Contract.FavouriteInfo.TABLE_NAME, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri + 5" + uri);
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
            case FAVOURITE_INFO:
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
    }


    public void close(){
        mOpenHelper.close();
    }


    }

