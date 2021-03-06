package com.nabigeto.gavin.popularmovie2b;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.nabigeto.gavin.popularmovie2b.Adapter.Custom_Movie_Adapter;
import com.nabigeto.gavin.popularmovie2b.Sync.MovieSyncAdapter;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Favourite_db_Helper;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Favourites_Contract;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.movie_favouriteContentProvider;

import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public Custom_Movie_Adapter mAdapter;


    public int position;

    private boolean mUseCaseLayout;

    public boolean favourite = true;

    public int selector_id = 1;

    public String movie_selection_type;

    public static String database_id;

    public static final String ACCOUNT_TYPE = "popularmovie2b.nabigeto.com";
    public static final String ACCOUNT = "dummyaccount";
    public static final String ACCOUNT_R = "dummyaccount_r";

    Account mAccount;

    private int mPosition = GridView.INVALID_POSITION;

    private static final String SELECTED_KEY = "selected_position";

    private static final int MOVIE_LOADER = 0;
    private static final int FAVOURITE_LOADER = 1;

    public static final String [] MOVIE_COLUMNS = {
            Movie_Contract.MovieInfo.TABLE_NAME + "." +
            Movie_Contract.MovieInfo._ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_ENTRY_ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_MOVIE_ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_TITLE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RELEASE_DATE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RATING,
            Movie_Contract.MovieInfo.COLUMN_NAME_INFO,
            Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE,
            Movie_Contract.MovieInfo.COLUMN_FAVOURITE


    };


    public static final String [] FAVOURITE_COLUMNS = {
            Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F + "." +
            Movie_Favourites_Contract.FavouriteInfo._ID,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_ENTRY_ID,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TITLE,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RATING,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_INFO,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_IMAGE_FILE,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_FAVOURITE
            };


    public static final int _ID = 0;
    public static final int COL_MOVIE_ENTRY_ID = 1;
    public static final int COL_MOVIE_ID = 2;
    public static final int COL_MOVIE_TITLE = 3;
    public static final int COL_MOVIE_RELEASE_DATE = 4;
    public static final int COL_MOVIE_RATING = 5;
    public static final int COL_MOVIE_INFO = 6;
    public static final int COL_MOVIE_IMAGE_FILE = 7;
    public static final int COL_FAVOURITE = 8;


    public interface Callback {

        void onItemSelected(String location_ID);
        void favourite_state(Boolean favourite_state_m);
        void favourite_finder(String movie_ID);

    }

    public MainActivityFragment () {

    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);

        mAccount = CreateSyncAccount(getContext());

        boolean database_status = doesDatabaseExist(getContext(), "movie_favourite_database_a.db");

        if (database_status != true){

            Movie_Favourite_db_Helper movie_favourite_db_helper = new Movie_Favourite_db_Helper(getContext());

            SQLiteDatabase db = movie_favourite_db_helper.getWritableDatabase();

            movie_favourite_db_helper.onClear(db);
            movie_favourite_db_helper.onCreate(db);

        }

        else {


        }

        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);



        if (isOnline() != true) {
    Toast.makeText(getActivity(), "No network detected", Toast.LENGTH_LONG).show();

    } else {
    Toast.makeText(getActivity(), "Network connection available - we are getting your data", Toast.LENGTH_LONG).show();

    }

        favourite = false;

    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_options_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){

            case(R.id.movie_options_sort1):
                movie_selection_type = "popular";

                Bundle settingsBundlem = new Bundle();

                settingsBundlem.putString("gridview_load", movie_selection_type);
                settingsBundlem.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundlem.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                ContentResolver.requestSync(mAccount, Movie_Contract.CONTENT_AUTHORITY, settingsBundlem);

                favourite = false;
                ((Callback) getActivity()).favourite_state(favourite);
                String boolean_Value = String.valueOf(favourite);
                Log.v("Gavin", "MainActivity Fragment " + boolean_Value);

                break;

            case(R.id.movie_options_sort2):
                movie_selection_type = "top_rated";

                Bundle settingsBundlen = new Bundle();
                settingsBundlen.putString("gridview_load", movie_selection_type);
                settingsBundlen.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundlen.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                ContentResolver.requestSync(mAccount, Movie_Contract.CONTENT_AUTHORITY, settingsBundlen);

                favourite = false;
                ((Callback) getActivity()).favourite_state(favourite);
                boolean_Value = String.valueOf(favourite);
                Log.v("Gavin", "MainActivity Fragment " + boolean_Value);

                break;

            case(R.id.movie_options_sort3):
                movie_selection_type = "favourite";

             getLoaderManager().restartLoader(FAVOURITE_LOADER, null, this);

                favourite = true;
                ((Callback) getActivity()).favourite_state(favourite);

                break;



            case(R.id.movie_options_sort4):
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        final GridView gridView = (GridView) view.findViewById(R.id.grid_view_movie);

        mAdapter = new Custom_Movie_Adapter(getActivity(), null, 0);

        gridView.setAdapter(mAdapter);

        movie_selection_type = "popular";

        Bundle settingsBundlem = new Bundle();

        settingsBundlem.putString("gridview_load", movie_selection_type);
        settingsBundlem.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundlem.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mAccount, Movie_Contract.CONTENT_AUTHORITY, settingsBundlem);

        favourite = false;
        ((Callback) getActivity()).favourite_state(favourite);

        getLoaderManager().destroyLoader(FAVOURITE_LOADER);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {



                    cursor.moveToPosition(position);

                    database_id = cursor.getString(_ID);
                    String movie_id = cursor.getString(COL_MOVIE_ID);
                    String image = cursor.getString(COL_MOVIE_IMAGE_FILE);
                    String info = cursor.getString(COL_MOVIE_INFO);
                    Uri puri = Movie_Contract.MovieInfo.CONTENT_URI_R;
                    String ppuri = puri.toString();


                    ((Callback) getActivity()).favourite_finder(movie_id);

                    Bundle settingsBundle = new Bundle();
                    settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                    settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                    settingsBundle.putString("reviewsync_database", database_id);
                    settingsBundle.putString("reviewsync_api", movie_id);

                    ContentResolver.requestSync(mAccount, Movie_Contract.CONTENT_AUTHORITY_R, settingsBundle);

                    Bundle settingsBundlet = new Bundle();
                    settingsBundlet.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                    settingsBundlet.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                    settingsBundlet.putString("trailersync_database", database_id);
                    settingsBundlet.putString("trailersync_api", movie_id);

                    ContentResolver.requestSync(mAccount, Movie_Contract.CONTENT_AUTHORITY_T, settingsBundlet);


                    ((Callback) getActivity()).onItemSelected(database_id);


                }
                mPosition = position;

                                            }

                                        }
        );

        if (savedInstanceState !=null && savedInstanceState.containsKey(SELECTED_KEY)){
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return view;
    }




    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (mPosition != GridView.INVALID_POSITION)
            savedInstanceState.putInt(SELECTED_KEY,mPosition);
            savedInstanceState.putBoolean(SELECTED_KEY, favourite);

        super.onSaveInstanceState(savedInstanceState);
    }




    private void updateMovieSelection() {
        MovieSyncAdapter.syncImmediately(getActivity());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {


        switch (i) {


            case MOVIE_LOADER:

                String sortOrder = Movie_Contract.MovieInfo.COLUMN_NAME_RATING + " ASC";

                CursorLoader movie_loader =  new CursorLoader(getContext(),
                        Movie_Contract.MovieInfo.CONTENT_URI,
                        MOVIE_COLUMNS,
                        null,
                        null,
                        sortOrder);

                return movie_loader;

            case FAVOURITE_LOADER:

                String favouritesortOrder = Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RATING + " ASC";

                CursorLoader favourite_loader = new CursorLoader(getContext(),
                        Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F,
                        FAVOURITE_COLUMNS,
                        null,
                        null,
                        favouritesortOrder);

                return favourite_loader;

        }


        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        int loader_id;

        loader_id = loader.getId();

        switch (loader_id){

            case MOVIE_LOADER:
                mAdapter.swapCursor(data);
            break;

            case FAVOURITE_LOADER:

                if (favourite == true) {
                    mAdapter.swapCursor(data);
                }

            break;

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){

        int loader_id;

        loader_id = loader.getId();

        switch (loader_id){

            case MOVIE_LOADER:
                mAdapter.swapCursor(null);
                break;

            case FAVOURITE_LOADER:
                mAdapter.swapCursor(null);
                break;
        }

    }

    public void setuseCaseLayout(boolean useCaseLayout){

        mUseCaseLayout = useCaseLayout;

    }


    @Override
    public void onResume() {
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
        getLoaderManager().restartLoader(FAVOURITE_LOADER, null, this);
        super.onResume();
    }

    public boolean isOnline() {
        Context context;
        context = getActivity();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }


    public static Account CreateSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);

        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {

        } else {

        }
        return newAccount;
    }

    public static Account CreateSyncAccount_R(Context context) {
        Account newAccount = new Account(ACCOUNT_R, ACCOUNT_TYPE);

        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {

        } else {

        }
        return newAccount;
    }

}
