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
public class movie_favouriteContentProvider extends ContentProvider {
/**
    public static final UriMatcher sUriMatcher = buildUriMatcher();
**/
    private Movie_Favourite_db_Helper mOpenHelper;

    private static final UriMatcher sUriMatcher;

    static final int FAVOURITE_INFO = 102;

    private static final String AUTHORITY = Movie_Favourites_Contract.CONTENT_AUTHORITY_F;
    private static final String TABLE_NAME = "movie_favourite";


    private static final SQLiteQueryBuilder sMovie_InfoQueryBuilder;

    static {

        sMovie_InfoQueryBuilder = new SQLiteQueryBuilder();

        sMovie_InfoQueryBuilder.setTables(
                Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F);


    }

    private static final String sMovie_InfoSettingSelection = Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F + " = ? ";

    
    private Cursor getMovie_Info(Uri uri, String[] projection, String sortOrder) {

        String Favourite_Info_Setting = Movie_Favourites_Contract.FavouriteInfo.getMovieNameFromUri(uri);


        String[] selectionArgs;
        String selection;

        selection = sMovie_InfoSettingSelection;
        selectionArgs = new String[]{Favourite_Info_Setting};


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
                movie_favouriteContentProvider.AUTHORITY,
                Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F,
                FAVOURITE_INFO
        );

    }



    @Override
    public boolean onCreate(){
        mOpenHelper = new Movie_Favourite_db_Helper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int typeUri = sUriMatcher.match(uri);

        switch (typeUri) {

            case FAVOURITE_INFO:
                return Movie_Favourites_Contract.FavouriteInfo.CONTENT_TYPE_F;

            default:
                throw new UnsupportedOperationException("Unknown URI + 1" + uri);
        }
    }

        @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

            Cursor retCursor;

            String suri = uri.toString();

            final int typeUri = sUriMatcher.match(uri);

            switch (typeUri) {

                case FAVOURITE_INFO: {

                    retCursor = mOpenHelper.getReadableDatabase().query(
                            Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder

                    );

                    break;
                }

                default:
                    throw new UnsupportedOperationException("Unknown uri + 2b" + uri);
            }

            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
            return retCursor;
        }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues){

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch(match) {

            case FAVOURITE_INFO: {

                long db_id = db.insert(Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F, null, contentValues);
                if (db_id > 0)
                    returnUri = Movie_Favourites_Contract.FavouriteInfo.buildMovie_InfoUri_F(db_id);
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
                rowsUpdated = db.update(Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F, contentvalues, selection, selectionArgs);
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
                rowsDeleted = db.delete(Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F, selection, selectionArgs);
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

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        String smatch = Integer.toString(match);

        switch(match){
            case FAVOURITE_INFO:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values){
                        long _id = db.insert(Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F, null, value);
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


