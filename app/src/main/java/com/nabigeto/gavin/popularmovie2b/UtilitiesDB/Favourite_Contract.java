package com.nabigeto.gavin.popularmovie2b.UtilitiesDB;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Gavin on 3/8/2016.
 */
public class Favourite_Contract {

    public static final String CONTENT_AUTHORITY = ("com.nabigeto.gavin.popularmovie2b.review");
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVOURITE = "favourite";

    public static final class FavouriteInfo implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;


        public static final String TABLE_NAME = "favourites";

        public static final String _ID = "_id";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_MOVIE_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_INFO = "info";
        public static final String COLUMN_NAME_IMAGE_FILE = "image_file";

        public static final String COLUMN_NAME_REVIEW1 = "review1";
        public static final String COLUMN_NAME_REVIEW2 = "review2";
        public static final String COLUMN_NAME_REVIEW3 = "review3";
        public static final String COLUMN_NAME_REVIEW_AUTHOR1 = "author1";
        public static final String COLUMN_NAME_REVIEW_AUTHOR2 = "author2";
        public static final String COLUMN_NAME_REVIEW_AUTHOR3 = "author3";

        public static final String COLUMN_NAME_TRAILER1 = "trailer1";
        public static final String COLUMN_NAME_TRAILER2 = "trailer2";
        public static final String COLUMN_NAME_TRAILER3 = "trailer3";

        public static final String COLUMN_FAVOURITE = "favourite";


        public static Uri buildFavourite_InfoUri(long id) {
            Log.v("Gavin", "Build MovieInfoURI");

            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();

        }


        public static String getFavouriteNameFromUri(Uri uri) {

            Log.v("Gavin", "Movie Contract");
            return uri.getPathSegments().get(1);

        }


    }
}