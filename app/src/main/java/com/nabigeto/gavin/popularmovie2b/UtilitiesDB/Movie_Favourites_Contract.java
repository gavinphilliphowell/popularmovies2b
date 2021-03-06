package com.nabigeto.gavin.popularmovie2b.UtilitiesDB;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Gavin on 3/8/2016.
 */
public class Movie_Favourites_Contract {


    public static final String CONTENT_AUTHORITY_F = ("com.nabigeto.gavin.popularmovie2b.movie_favourite.provider");

    public static final Uri BASE_CONTENT_URI_F = Uri.parse("content://" + CONTENT_AUTHORITY_F);

    public static final String PATH_MOVIE_F = "movie_favourite";



    public static final class FavouriteInfo implements BaseColumns {

        public static final Uri CONTENT_URI_F = BASE_CONTENT_URI_F.buildUpon().appendPath(PATH_MOVIE_F).build();

        public static final String CONTENT_TYPE_F = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY_F + "/" + PATH_MOVIE_F;

        public static final String CONTENT_ITEM_TYPE_F = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY_F + "/" + PATH_MOVIE_F;

        public static final String TABLE_NAME_F = "movie_favourite";

        public static final String _ID = "_id";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
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

        public static final String COLUMN_FAVOURITE = "favourites_selection";

        public static Uri buildMovie_InfoUri_F(long id) {

            return CONTENT_URI_F.buildUpon().appendPath(String.valueOf(id)).build();

        }

        public static String getMovieNameFromUri(Uri uri) {

            return uri.getPathSegments().get(1);

        }


    }
}