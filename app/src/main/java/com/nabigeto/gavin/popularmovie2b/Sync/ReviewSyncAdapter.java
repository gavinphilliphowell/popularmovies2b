package com.nabigeto.gavin.popularmovie2b.Sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nabigeto.gavin.popularmovie2b.R;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_db_Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by Gavin on 6/8/2016.
 */
public class ReviewSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final int SYNC_INTERVAL = 60;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/2;

    ContentResolver rContentResolver;



    private final static String[] NOTIFY_MOVIE_PROJECTION = new String[]{

    /**        Movie_Contract.MovieInfo.COLUMN_NAME_ENTRY_KEY,  **/
            Movie_Contract.MovieInfo.COLUMN_NAME_MOVIE_ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_TITLE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RELEASE_DATE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RATING,
            Movie_Contract.MovieInfo.COLUMN_NAME_INFO,
            Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW1,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW2,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW3,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER3,
            Movie_Contract.MovieInfo.COLUMN_FAVOURITE,
   /**         Movie_Contract.MovieInfo.COLUMN_NAME_BACKGROUND,
**/
    };


    public ReviewSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super (context, autoInitialize, allowParallelSyncs);

        rContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        final int numMovies =10;
        final String MOVIE_KEY = "bb8bfd709e4e16f868ddf8fbd62b2d59";
        String movie_api_id;
        String movie_database_id;


       if (extras != null) {

           movie_api_id = extras.getString("reviewsync_api");
           movie_database_id = extras.getString("reviewsync_database");
        Log.v("Gavin", "extras loaded");
        Log.v("Gavin", "Sync Movie Location" + movie_api_id);
       }
        else {
           movie_database_id = "8";
           movie_api_id = "27579";
           Log.v("Gavin", "no extras loaded");
       }




        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;


        try {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(movie_api_id)
                    .appendQueryParameter("api_key", MOVIE_KEY)
                    .appendQueryParameter("append_to_response","reviews,trailers");

            String Web_Location_URL = builder.build().toString();

            Log.v("Gavin", "Reviews and Trailers" + Web_Location_URL);

            URL url = new URL(Web_Location_URL);

            // Create the request to Movie Database, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.v("Gavin", "Nothing to show");
                return;
            }

            movieJsonStr = buffer.toString();

            Log.v("Gavin", movieJsonStr);

        } catch (IOException e) {
            Log.e("MainActivityFragment", e.getMessage(), e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("MainActivityFragment", "Error closing stream", e);
                }
            }
        }
        try {

            get_movie_details_Json(movieJsonStr, movie_database_id);

        } catch (JSONException e) {
            Log.e("Gavin", e.getMessage(), e);
            e.printStackTrace();
        }

        return;
    }

    private void get_movie_details_Json(String movieJsonStr, String movie_id_string)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
/**
        ArrayList<Movie_Adapter> movie_adapters = new ArrayList<Movie_Adapter>();
**/
        final String OWM_RESULTS_R = "reviews";
        final String OWM_RESULTS_T = "trailers";
        final String OWM_RESULTS_RE = "results";
        final String OWM_YOUTUBE = "youtube";

        final String OWM_REVIEW_FILTER = "name";
        final String OWM_REVIEW_AUTHOR = "author";
        final String OWM_TRAILER_FILTER = "name";
        final String OWM_TRAILER_SOURCE = "source";

        final String OWM_ID = "id";


        try {


            JSONObject reviewJson = new JSONObject(movieJsonStr);

            JSONObject review_object = reviewJson.getJSONObject(OWM_RESULTS_R);

            JSONArray results_SubR = review_object.getJSONArray(OWM_RESULTS_RE);

            String[] review_name = new String [results_SubR.length()];
            String[] review_author = new String [results_SubR.length()];

            int length_t = 0;
            int review_case;
            int trailer_case;



            if (results_SubR.length() > length_t) {
                review_case = 1;
                for (int i = 0; i < results_SubR.length(); i++) {
                    review_name[i] = results_SubR.getJSONObject(i).getString(OWM_REVIEW_FILTER);
                    review_author[i] = results_SubR.getJSONObject(i).getString(OWM_REVIEW_AUTHOR);
                    Log.v("Gavin", "review name" + review_name[i]);
                }
            }

            else {
                review_case = 2;
            }

            JSONObject trailer_object = reviewJson.getJSONObject(OWM_RESULTS_T);

            JSONArray trailer_SubR = trailer_object.getJSONArray(OWM_YOUTUBE);

            String[] trailer_name = new String [trailer_SubR.length()];
            String[] trailer_source = new String [trailer_SubR.length()];


            if (trailer_SubR.length() > length_t) {
                trailer_case = 1;
                for (int i=0; i<trailer_SubR.length(); i++) {
                    trailer_name[i] = trailer_SubR.getJSONObject(i).getString(OWM_TRAILER_FILTER);
                    trailer_source[i] = trailer_SubR.getJSONObject(i).getString(OWM_TRAILER_SOURCE);
                    Log.v("Gavin", "trailer name" + trailer_name[i]);
                    Log.v("Gavin", "trailer source" + trailer_source[i]);
            }
            }

            else {
                trailer_case = 2;
            }

            for (int i=0; i<trailer_SubR.length(); i++) {

            }
/**
            Vector<ContentValues> cMector = new Vector<ContentValues>(movieArray.length());

           /** long favouriteID =addFavourite(OWM_NAME, "y");
                    **/
            Log.v("Gavin", "Started JSON");
/**
            for (int i = 0; i < movieArray.length(); i++) {

        /**        String number_ID;
                String reviews1;
                String reviews2;
                String reviews3;  **/
 /**               String trailer1;
                String trailer2;
                String trailer3;
       /**         String id;
**/


            Movie_db_Helper mdbmovie = new Movie_db_Helper(getContext());

            SQLiteDatabase db = mdbmovie.getWritableDatabase();

            ContentValues reviewValues = new ContentValues();



            switch (review_case){

                case (1):

                    if(review_name[0] != null){
                        reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW1, review_name[0]);
                    }
                    if(review_name[1] != null){
                        reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW2, review_name[1]);
                    }
                    if(review_name[2] != null){
                        reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW3, review_name[2]);
                    }

                    if(review_author[0] != null){
                        reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR1, review_author[0]);
                    }
                    if(review_author[1] != null){
                        reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR2, review_author[1]);
                    }
                    if(review_author[2] != null){
                        reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR3, review_author[2]);
                    }

                    break;

                    default:

                    break;
            }

            ContentValues trailerValues = new ContentValues();

            switch (trailer_case){

                case (1):

                    if (trailer_source[0] != null) {
                        Log.v("Gavin", "checking this bit");
                        trailerValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1, trailer_source[0]);
                        Log.v("Gavin", "checking this bit again");
                    }
                    if (trailer_source[1] != null) {
                        Log.v("Gavin", "checking this bit 2");
                        trailerValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2, trailer_source[1]);
                    }
                    if (trailer_source[2] != null) {
                        Log.v("Gavin", "checking this bit 3");
                        trailerValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER3, trailer_source[2]);
                    }

                    break;

                    default:

                    break;
            }

            if(review_case <= 1) {


            Log.v("Gavin", "got to this bit loader");
            String rSelectionClause = Movie_Contract.MovieInfo._ID + "LIKE ?";
            String[] rSelectionArgs = {movie_id_string};
            Log.v("Gavin","got to this bit loader 2");

            int num_rows = getContext().getContentResolver().update(
                    Movie_Contract.MovieInfo.CONTENT_URI,
                    reviewValues,
                    rSelectionClause,
                    rSelectionArgs
            );
            Log.v("Gavin", "got to this bit loader 3");
        }
            else {
                Log.v("Gavin", "No reviews");


            }


            if(trailer_case <= 1) {


                Log.v("Gavin", "got to this bit loader t");
                String rSelectionClause = Movie_Contract.MovieInfo._ID + " LIKE ? ";
                String[] rSelectionArgs = {movie_id_string};
                Log.v("Gavin","got to this bit loader 2 t");

                int num_rows = getContext().getContentResolver().update(
                        Movie_Contract.MovieInfo.CONTENT_URI,
                        trailerValues,
                        rSelectionClause,
                        rSelectionArgs
                );
                Log.v("Gavin", "got to this bit loader 3 t");
            }
            else {
                Log.v("Gavin", "No trailers");

            }
/**
                cMector.add(movieValues);

                /**
                 Movie_Adapter movie_data = new Movie_Adapter(name, rating, release, info, image, background, id);
                 movie_adapters.add(movie_data);
                 **/
/**
            }
        **/
  /**          int inserted = 0;
            String Start = "0";

            if (cMector.size() > 0) {

                Log.v("Gavin", "Started Database Upload");

                ContentValues[] cvArray = new ContentValues[cMector.size()];
                cMector.toArray(cvArray);
                getContext().getContentResolver().bulkInsert(Movie_Contract.MovieInfo.CONTENT_URI, cvArray);
        /**        getContext().getContentResolver().bulkInsert(Movie_Contract.Favourites.CONTENT_URI,cvArray);

               Log.v("Gavin", "Database Upload Complete");

                getContext().getContentResolver().delete(Movie_Contract.MovieInfo.CONTENT_URI, Movie_Contract.MovieInfo.COLUMN_NAME_ENTRY_ID + "<=?", new String[]{Start});
                **/
   /**         }
**/
        }


        catch (JSONException e) {
            e.printStackTrace();
        }

    }
/**
    long addFavourite(String favouritesTitle, String addDatabase) {
        long favouriteID;

        Cursor favouriteCursor = getContext().getContentResolver().query(
                Movie_Contract.Favourites.CONTENT_URI,
                new String[]{Movie_Contract.Favourites._ID},
                Movie_Contract.Favourites.COLUMN_NAME_TITLE_F + " = ?",
                new String[]{favouritesTitle},
                null);

        if (favouriteCursor.moveToFirst()) {
            int favouriteIdIndex = favouriteCursor.getColumnIndex(Movie_Contract.Favourites._ID);
            favouriteID = favouriteCursor.getLong(favouriteIdIndex);
        } else {

            ContentValues favouriteValues = new ContentValues();

            favouriteValues.put(Movie_Contract.Favourites.COLUMN_NAME_TITLE_F, favouritesTitle);
            favouriteValues.put(Movie_Contract.Favourites.COLUMN_NAME_FAVOURITE, addDatabase);

            Uri insertUri = getContext().getContentResolver().insert(
                    Movie_Contract.Favourites.CONTENT_URI,
                    favouriteValues
            );

            favouriteID = ContentUris.parseId(insertUri);
        }

        favouriteCursor.close();

        return favouriteID;

    }
**/

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority_movie);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority_review), bundle);
    }

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        if ( null == accountManager.getPassword(newAccount) ) {

            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {

        ReviewSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority_review), false);

        syncImmediately(context);
    }

    public static void initialiseSyncAdapter(Context context){
        getSyncAccount(context);

        Log.v("Gavin", "Sync adapter launched 2");

    }

}

