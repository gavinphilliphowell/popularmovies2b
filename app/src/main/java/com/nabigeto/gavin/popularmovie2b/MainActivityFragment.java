package com.nabigeto.gavin.popularmovie2b;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import java.net.URI;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public Custom_Movie_Adapter mAdapter;
    public static final String KEY_FILE = "Shared_Preference_KEY_FILE";
    public static final String KEY_FILE2 = "movies";
    public static final String KEY_FILE3 = "startmovie";

    public static final String FRAGMENT_NAME = "Frag1";

    public int position;

    private GridView gridView;
    private boolean ismUseCaseLayout;
    private int mPosition = GridView.INVALID_POSITION;

    private boolean mUseCaseLayout;

    private static final String SELECTED_KEY = "selected_position";

    private static final int MOVIE_LOADER = 0;

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

        public void onItemSelected(String location_ID);

    }

    public MainActivityFragment () {

    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);

    if (isOnline() != true) {
    Toast.makeText(getActivity(), "No network detected", Toast.LENGTH_LONG).show();

    } else {
    Toast.makeText(getActivity(), "Network connection available - we are getting your data", Toast.LENGTH_LONG).show();

    }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        Log.v("Gavin", "Got to this part - launching view");
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        final GridView gridView = (GridView) view.findViewById(R.id.grid_view_movie);

        mAdapter = new Custom_Movie_Adapter(getActivity(), null, 0);

        gridView.setAdapter(mAdapter);
        Log.e("Gavin", "Got to this bit in mainactivityfragment");

        getLoaderManager().initLoader(MOVIE_LOADER, null, this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                                                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                                                if (cursor != null) {
                                                    String title = cursor.getString(COL_MOVIE_ID);

                                                    Log.v("Gavin", "MainActivityFragment" + title);
                                                    Uri puri = Movie_Contract.MovieInfo.CONTENT_URI;
                                                    String ppuri = puri.toString();
                                                    Log.v("Gavin", "MainActivityFragment" + ppuri);
                                                    ((Callback) getActivity()).onItemSelected(title);
                                                }
                                                mPosition = position;

                                            }

                                        }
        );

        if (savedInstanceState !=null && savedInstanceState.containsKey(SELECTED_KEY)){
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
  /**      mAdapter.setuseCaseLayout(mUseCaseLayout);
     **/
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_options_menu, menu);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (mPosition != GridView.INVALID_POSITION)
            savedInstanceState.putInt(SELECTED_KEY,mPosition);
 /**       savedInstanceState.putParcelableArrayList(KEY_FILE, movieList);  **/
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);

        }

    private void updateMovieSelection() {
        MovieSyncAdapter.syncImmediately(getActivity());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
        String sortOrder = Movie_Contract.MovieInfo.COLUMN_NAME_RATING + " ASC";
        Log.v("Gavin", sortOrder);
        String view2 = Movie_Contract.MovieInfo.CONTENT_URI.toString();
        Log.v("Gavin", "Main Activity_1" + view2);

        Uri movie = Movie_Contract.MovieInfo.buildMovie_InfoUri(i);

                return new CursorLoader(getActivity(),
                        Movie_Contract.MovieInfo.CONTENT_URI,
                        MOVIE_COLUMNS,
                        null,
                        null,
                        sortOrder);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mAdapter.swapCursor(null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       /** mAdapter.swapCursor(data); **/
        mAdapter.changeCursor(data);
   /**     if (mPosition != GridView.INVALID_POSITION) {
            gridView.smoothScrollToPosition(mPosition);
        }  **/
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    public boolean isOnline() {
        Context context;
        context = getActivity();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public void setuseCaseLayout(boolean useCaseLayout){
        mUseCaseLayout = useCaseLayout;
  /**      if(mAdapter != null) {
            mAdapter.setuseCaseLayout(mUseCaseLayout);
        }
**/
        }



}
