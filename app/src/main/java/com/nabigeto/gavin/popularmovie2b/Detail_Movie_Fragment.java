package com.nabigeto.gavin.popularmovie2b;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.nabigeto.gavin.popularmovie2b.Adapter.Movie_Review_Adapter;
import com.nabigeto.gavin.popularmovie2b.Sync.ReviewSyncAdapter;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class Detail_Movie_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {



    Context dContext;
    public static final String KEY_FILE = "Shared_Preference_KEY_FILE_Detail";
    public static final String KEY_FILE2 = "movies";
    public static final String KEY_FILE3 = "startmovie";

    public int position;
    public String mSw;

    public String sduri;

    public Movie_Review_Adapter rAdapter;

    View view = null;
/**
    public String mTitle;
    public String mRelease;
    public String mRating;
    public String mInfo;
    public String mImage;
    public String mBackground;
**/
    static final String DETAIL_URI = "URI";

    private ShareActionProvider mShareActionProvider;

    /**
    String mMovie_ID_http = ("/movie/" + mMovie_ID + "/video");
    String mMovie_Trailer_http = ("/movie/" + mMovie_Trailer + "/reviews");
**/

    public String dUri;


    private static final int DETAIL_LOADER = 1;
    private static final  int REVIEW_LOADER = 2;
    private static final int TRAILER_LOADER = 3;


    private static final String [] DETAIL_COLUMNS = {
            Movie_Contract.MovieInfo.TABLE_NAME + "." +
                    Movie_Contract.MovieInfo._ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_ENTRY_ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_MOVIE_ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_TITLE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RELEASE_DATE,
            Movie_Contract.MovieInfo.COLUMN_NAME_RATING,
            Movie_Contract.MovieInfo.COLUMN_NAME_INFO,
            Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW1,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW2,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW3,
            Movie_Contract.MovieInfo.COLUMN_FAVOURITE


    };

    public static final int _ID_D = 0;
    public static final int COL_MOVIE_ENTRY_ID_D = 1;
    public static final int COL_MOVIE_ID_D = 2;
    public static final int COL_MOVIE_TITLE_D = 3;
    public static final int COL_MOVIE_RELEASE_DATE_D = 4;
    public static final int COL_MOVIE_RATING_D = 5;
    public static final int COL_MOVIE_INFO_D = 6;
    public static final int COL_MOVIE_IMAGE_FILE_D = 7;
    public static final int COL_MOVIE_REVIEW1 = 8;
    public static final int COL_MOVIE_REVIEW2 = 9;
    public static final int COL_MOVIE_REVIEW3 = 10;
    public static final int COL_FAVOURITE_D = 8;



    /**    public static final int COL_REVIEW_ONE = 5;
    public static final int COL_REVIEW_TWO = 6;
    public static final int COL_MOVIE_TRAILER = 7;
**/


    public ImageView dImage_File;
    public TextView dID;
    public TextView dTitle;
    public TextView dRelease_Date;
    public TextView dRating;
    public TextView dInfo;
    public TextView dFavourites;


    private static final String [] REVIEW_COLUMNS ={
        Movie_Contract.MovieInfo.TABLE_NAME + "." +
                Movie_Contract.MovieInfo._ID,
        Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW1,
        Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW2,
        Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW3
    };

    private static final String [] TRAILER_COLUMNS ={
            Movie_Contract.MovieInfo.TABLE_NAME + "." +
                    Movie_Contract.MovieInfo._ID,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER3
    };

    public Detail_Movie_Fragment() {
        // Required empty public constructor
      /**  dContext = getContext();  **/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.v("Gavin", "onActivityCreated");
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        getLoaderManager().initLoader(REVIEW_LOADER, null, this);
        getLoaderManager().initLoader(TRAILER_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();

        if (arguments != null) {
            dUri = arguments.getString(KEY_FILE);

            Log.v("Gavin", "uri " + "KEY_FILE" + dUri);
        }
        else{
            dUri = "3";
        }
        Log.v("Gavin", "uri " + dUri);

        ReviewSyncAdapter.initialiseSyncAdapter(getContext(), dUri);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_details_layout, container, false);
        Log.v("Gavin", "Got to the bit in the detail fragment");

        dImage_File = (ImageView) rootView.findViewById(R.id.detail_movie_poster);
        dRating = (TextView) rootView.findViewById(R.id.movie_user_rating_d);
        dRelease_Date = (TextView) rootView.findViewById(R.id.movie_release_date_d);
        dInfo = (TextView) rootView.findViewById(R.id.movie_info_d);
        dTitle = (TextView) rootView.findViewById(R.id.movie_title_d);

        final ListView listView_Review = (ListView) view.findViewById(R.id.movie_review_listview);
        rAdapter = new Movie_Review_Adapter(getActivity(), null, 0);
        listView_Review.setAdapter(rAdapter);

        Log.v("Gavin", "About to load rootview");
        return rootView;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String position;
        String row_Pref;

        switch (id) {


            case DETAIL_LOADER:

                position = " _ID = " + dUri;
                /**      Uri prefs_uri2 =  + " _ID " prefs_uri.getLastPathSegment();
                 **/
                row_Pref = dUri;
                Log.v("Gavin", "dUuri" + row_Pref);
                if (null != dUri) {
                    Log.v("Gavin", "Detail Loader cursor created");
                    return new CursorLoader(
                            getActivity(),
                            Movie_Contract.MovieInfo.CONTENT_URI,
                            DETAIL_COLUMNS,
                            position,
                            null,
                            null

                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Detail Loader");
                Log.v("Gavin", "Loader cursor finished" + dUri);
                return null;

            case REVIEW_LOADER:

                position = " _ID = " + dUri;
                /**      Uri prefs_uri2 =  + " _ID " prefs_uri.getLastPathSegment();
                 **/
                row_Pref = dUri;
                Log.v("Gavin", "dUuri" + row_Pref);
                if (null != dUri) {
                    Log.v("Gavin", "Detail Loader cursor created");
                    return new CursorLoader(
                            getActivity(),
                            Movie_Contract.MovieInfo.CONTENT_URI,
                            REVIEW_COLUMNS,
                            position,
                            null,
                            null

                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Review Loader");
                Log.v("Gavin", "Loader cursor finished" + dUri);
                return null;


            case TRAILER_LOADER:

                position = " _ID = " + dUri;
                /**      Uri prefs_uri2 =  + " _ID " prefs_uri.getLastPathSegment();
                 **/
                row_Pref = dUri;
                Log.v("Gavin", "dUuri" + row_Pref);
                if (null != dUri) {
                    Log.v("Gavin", "Detail Loader cursor created");
                    return new CursorLoader(
                            getActivity(),
                            Movie_Contract.MovieInfo.CONTENT_URI,
                            TRAILER_COLUMNS,
                            position,
                            null,
                            null

                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Trailer Loader");
                Log.v("Gavin", "Loader cursor finished" + dUri);
                return null;

        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data_d) {

        int loader_ID;
        int position;
        String positions;

        loader_ID = loader.getId();
        dContext = getContext();

        switch (loader_ID) {


            case DETAIL_LOADER:

                data_d.moveToFirst();
                Log.v("Gavin", "Position passed to loader");
                position = data_d.getPosition();
                positions = Integer.toString(position);
                Log.v("Gavin", "Cursor position in detail fragment" + positions);

                int movie_api_id = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_MOVIE_ID);
                String movie_api_id_s = data_d.getString(movie_api_id);

                ReviewSyncAdapter.initialiseSyncAdapter(getContext(), movie_api_id_s);

                int image_file_position = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE);


                String url = data_d.getString(image_file_position);
                Log.v("Gavin", "DetailActivityFragment" + url);

                Log.v("Gavin", "Loading picasso in detail fragment");

                Picasso.with(dContext).load(url).placeholder(R.drawable.worms_head).into(dImage_File);

                int filmTitle = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TITLE);
                String filmTitle_a = data_d.getString(filmTitle);
                Log.v("Gavin", "DetailActivityFragment" + filmTitle_a);

                dTitle.setText(filmTitle_a);

                String filmInfo = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_INFO_D);
                dInfo.setText(filmInfo);

                String filmRelease = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RELEASE_DATE_D);
                Log.v("Gavin", filmRelease);
                dRelease_Date.setText(filmRelease);

                String filmRating = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RATING_D);
                Log.v("Gavin", filmRating);
                dRating.setText(filmRating);


                Log.v("Gavin", filmInfo);

                break;

                case REVIEW_LOADER:

                    data_d.moveToFirst();



        }
        ;

    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }





}
