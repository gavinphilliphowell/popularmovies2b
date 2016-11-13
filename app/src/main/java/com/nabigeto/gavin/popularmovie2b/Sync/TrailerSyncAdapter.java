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

/**
 * Created by Gavin on 6/8/2016.
 */
public class TrailerSyncAdapter extends AbstractThreadedSyncAdapter {

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


    public TrailerSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
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

           movie_api_id = extras.getString("trailersync_api");
           movie_database_id = extras.getString("trailersync_database");
        Log.v("Gavin", "extras loaded");
        Log.v("Gavin", "Sync Movie Location" + movie_database_id);
           Log.v("Gavin", "Sync Movie ID" + movie_api_id);
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
                    .appendPath("videos")
                    .appendQueryParameter("api_key", MOVIE_KEY);


            String Web_Location_URL = builder.build().toString();

            Log.v("Gavin", "Videos" + Web_Location_URL);

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
            Log.v("Gavin", "Gotten to this bit trailer sync adapter");
            get_movie_details_Json(movieJsonStr, movie_api_id, movie_database_id);

        } catch (JSONException e) {
            Log.e("Gavin", e.getMessage(), e);
            e.printStackTrace();
        }

        return;
    }

    private void get_movie_details_Json(String movieJsonStr, String movie_id_string, String movie_id_database)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
/**
        ArrayList<Movie_Adapter> movie_adapters = new ArrayList<Movie_Adapter>();
**/
        final String OWM_RESULTS_R = "videos";
        final String OWM_RESULTS_TOTAL = "total_results";
        final String OWM_RESULTS_RE = "results";

        final String OWM_REVIEW_FILTER = "key";


        final String OWM_ID = "id";

        Log.v("Gavin", "Review Sync Adapter" + movie_id_string);

        try {


            JSONObject reviewJson = new JSONObject(movieJsonStr);

     /**       JSONObject review_object = reviewJson.getJSONObject(OWM_RESULTS_RE);
**/

            JSONArray results_SubR = reviewJson.getJSONArray(OWM_RESULTS_RE);

            String[] trailer_name = new String [results_SubR.length()];

            int length_results = results_SubR.length();
            String length_results_t = Integer.toString(length_results);
            Log.v("Gavin", "Review Sync Adapter" + length_results_t);

            int length_t = 0;

            int trailer_case = results_SubR.length();


            if (results_SubR.length() > length_t) {

                for (int i = 0; i < results_SubR.length(); i++) {
                    trailer_name[i] = results_SubR.getJSONObject(i).getString(OWM_REVIEW_FILTER);
                    Log.v("Gavin", "review name" + trailer_name[i]);

                }
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

            if (trailer_case> 3){
                trailer_case= 3;
            }

            switch (trailer_case){

                case (1):


                    reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1, trailer_name[0]);
                    Log.v("Gavin", "reviewValues" + trailer_name[0]);
                    break;

                case (2):

                    reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1, trailer_name[0]);
                    Log.v("Gavin", "reviewValues" + trailer_name[0]);

                    reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2, trailer_name[1]);
                    Log.v("Gavin", "reviewValues" + trailer_name[1]);
                    break;

                case(3):

                    reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1, trailer_name[0]);
                    Log.v("Gavin", "reviewValues" + trailer_name[0]);

                    reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2, trailer_name[1]);
                    Log.v("Gavin", "reviewValues" + trailer_name[1]);

                    reviewValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER3, trailer_name[2]);
                    Log.v("Gavin", "reviewValues" + trailer_name[2]);
                    break;

            }




            if(trailer_case >= 1) {


            Log.v("Gavin", "got to this bit loader");
            String rSelectionClause = Movie_Contract.MovieInfo._ID + " LIKE ?";
            String[] rSelectionArgs = {movie_id_database};
            Log.v("Gavin","got to this bit loader 2");

            int num_rows = getContext().getContentResolver().update(
                    Movie_Contract.MovieInfo.CONTENT_URI_R,
                    reviewValues,
                    rSelectionClause,
                    rSelectionArgs
            );
            Log.v("Gavin", "got to this bit loader 3");
        }
            else {
                Log.v("Gavin", "No reviews");


            }

            db.close();

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
        String authority = context.getString(R.string.content_authority_review);
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

        TrailerSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority_review), false);

        syncImmediately(context);
    }

    public static void initialiseSyncAdapter(Context context){
        getSyncAccount(context);

        Log.v("Gavin", "Review Sync adapter launched 2");

    }

}

