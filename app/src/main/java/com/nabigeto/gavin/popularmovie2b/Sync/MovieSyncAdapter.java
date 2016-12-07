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
import java.util.Vector;

/**
 * Created by Gavin on 6/8/2016.
 */
public class MovieSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final int SYNC_INTERVAL = 1000;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/2;

    private final static String[] NOTIFY_MOVIE_PROJECTION = new String[]{

    /**        Movie_Contract.MovieInfo.COLUMN_NAME_ENTRY_KEY,  **/
            Movie_Contract.MovieInfo.COLUMN_NAME_MOVIE_ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_TITLE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RELEASE_DATE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RATING,
            Movie_Contract.MovieInfo.COLUMN_NAME_INFO,
            Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE,
            Movie_Contract.MovieInfo.COLUMN_FAVOURITE,
   /**         Movie_Contract.MovieInfo.COLUMN_NAME_BACKGROUND,
**/
    };

    public MovieSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);


    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        final int numMovies =10;
        final String MOVIE_KEY = "";

        int database_location = extras.getInt("gridview_default_load");
        String show_database_location = Integer.toString(database_location);

        String api_selection_option = extras.getString("gridview_load");

        Movie_db_Helper mdbmovie = new Movie_db_Helper(getContext());

        SQLiteDatabase db = mdbmovie.getWritableDatabase();
        mdbmovie.onClear(db);
        mdbmovie.onCreate(db);

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
                    .appendPath(api_selection_option)
                    .appendQueryParameter("api_key", MOVIE_KEY);

            String Web_Location_URL = builder.build().toString();


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

            get_movie_details_Json(movieJsonStr, numMovies);

        } catch (JSONException e) {
            Log.e("Gavin", e.getMessage(), e);
            e.printStackTrace();
        }

        return;
    }

    private void get_movie_details_Json(String movieJsonStr, int numMovies)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
/**
        ArrayList<Movie_Adapter> movie_adapters = new ArrayList<Movie_Adapter>();
**/
        final String OWM_RESULTS = "results";
        final String OWM_NAME = "original_title";
        final String OWM_RATING = "vote_average";
        final String OWM_RELEASE = "release_date";
        final String OWM_INFO = "overview";
        final String OWM_IMAGE = "poster_path";
        final String OWM_BACKGROUND = "backdrop_path";
        final String OWM_ID = "id";

        final String picture_URL = "http://image.tmdb.org/t/p/w185//";



        try {


            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(OWM_RESULTS);

            Vector<ContentValues> cMector = new Vector<ContentValues>(movieArray.length());


            for (int i = 0; i < movieArray.length(); i++) {

                String number_ID;
                String title;
                String rating;
                String release;
                String info;
                String image;
                String background;
                String id;
                String favourite_s;


                // Get the JSON object representing the day
                JSONObject movieItem = movieArray.getJSONObject(i);

                number_ID = Integer.toString(i);
                title = movieItem.getString(OWM_NAME);
                rating = movieItem.getString(OWM_RATING);
                release = movieItem.getString(OWM_RELEASE);
                info = movieItem.getString(OWM_INFO);
                image = picture_URL + movieItem.getString(OWM_IMAGE);
                background = picture_URL + movieItem.getString(OWM_BACKGROUND);
                id = movieItem.getString(OWM_ID);
                favourite_s = "n";

                ContentValues movieValues = new ContentValues();


                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_ENTRY_ID, number_ID);
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_MOVIE_ID, id);
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TITLE, title);

                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_RELEASE_DATE, release);
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_RATING, rating);
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_INFO, info);
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE, image);
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_FAVOURITE, favourite_s);

                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW1, "b");
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW2, "b");
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW3, "b");

                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR1, "b");
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR2, "b");
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR3, "b");

                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1, "b");
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2, "b");
                movieValues.put(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER3, "b");


                cMector.add(movieValues);

            }

            int inserted = 0;
            String Start = "0";

            if (cMector.size() > 0) {

                Uri uri = Uri.parse("content://" + "com.nabigeto.gavin.popularmovie2b.UtilitiesDB.movieContent.provider");
                ContentValues[] cvArray = new ContentValues[cMector.size()];
                cMector.toArray(cvArray);
                getContext().getContentResolver().bulkInsert(Movie_Contract.MovieInfo.CONTENT_URI, cvArray);

                getContext().getContentResolver().delete(Movie_Contract.MovieInfo.CONTENT_URI, Movie_Contract.MovieInfo.COLUMN_NAME_ENTRY_ID + "<=?", new String[]{Start});

            }

        }


        catch (JSONException e) {
            e.printStackTrace();
        }

    }

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
                context.getString(R.string.content_authority_movie), bundle);
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

        MovieSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority_movie), true);

        syncImmediately(context);
    }

    public static void initialiseSyncAdapter(Context context){
        getSyncAccount(context);
    }

}

