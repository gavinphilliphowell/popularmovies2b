package com.nabigeto.gavin.popularmovie2b;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

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
    public static final int COL_FAVOURITE_D = 8;



    /**    public static final int COL_REVIEW_ONE = 5;
    public static final int COL_REVIEW_TWO = 6;
    public static final int COL_MOVIE_TRAILER = 7;
**/


    public ImageView dImage_File;
    public TextView dID;
    private TextView dTitle;
    public TextView dRelease_Date;
    public TextView dRating;
    public TextView dInfo;
    public TextView dFavourites;

/**    private TextView mReview_One;
    private TextView mReview_Two;
    private ImageView mTrailer;
**/

    public Detail_Movie_Fragment() {
        // Required empty public constructor
      /**  dContext = getContext();  **/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.v("Gavin", "onActivityCreated");
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);

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

        Bundle arguments = getArguments();

        if (arguments != null) {
            dUri = arguments.getString(KEY_FILE);

            Log.v("Gavin", "uri " + dUri);
        }


        View rootView = inflater.inflate(R.layout.fragment_details_layout, container, false);
        Log.v("Gavin", "Got to the bit in the detail fragment");

        dImage_File = (ImageView) rootView.findViewById(R.id.detail_movie_poster);



        dRating = (TextView) rootView.findViewById(R.id.movie_user_rating_d);
        dRelease_Date = (TextView) rootView.findViewById(R.id.movie_release_date_d);
        dInfo = (TextView) rootView.findViewById(R.id.movie_info_d);

        dTitle = (TextView) rootView.findViewById(R.id.movie_title_d);

        Log.v("Gavin", "About to load rootview");
        return rootView;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String row_Pref = dUri;
        Log.v("Gavin", "dUuri" + row_Pref);
        if (null != dUri) {
            Log.v("Gavin", "Detail Loader cursor created");
            return new CursorLoader(
                    getActivity(),
                    Movie_Contract.MovieInfo.CONTENT_URI,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null

            );

        }
        Log.v("Gavin", "Loader cursor finished");
        Log.v("Gavin", "Loader cursor finished" + dUri);
        return null;
    }
/**
    void onMovieChanged(int newMovie){
        Uri uri = dUri;
        if(uri != null){
            Uri updateUri = Movie_Contract.MovieInfo.buildMovie_InfoUri(newMovie);
            dUri = updateUri;
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
        }
    }
**/
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data_d) {
         if (data_d != null) {

             getActivity();

        dContext = getContext();
        int position_d = Integer.valueOf(dUri);
        data_d.moveToPosition(position_d);
        Log.v("Gavin", "Position passed to loader" + position_d);
        int position = data_d.getPosition();
        String positions = Integer.toString(position);
        Log.v("Gavin", "Cursor position in detail fragment" + positions);

        int image_file_position = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE);


        String url = data_d.getString(image_file_position);
             Log.v("Gavin", "DetailActivityFragment" + url);

             Log.v("Gavin", "Loading picasso in detail fragment");

             Picasso.with(dContext).load(url).placeholder(R.drawable.worms_head).into(dImage_File);

        int filmTitle = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TITLE);
             String filmTitle_a = data_d.getString(filmTitle);
             Log.v("Gavin", "DetailActivityFragment" + filmTitle_a);

        dTitle.setText(filmTitle_a);

 /**
        String filmRelease = data.getString(Detail_Movie_Fragment.COL_MOVIE_RELEASE_DATE);
        Log.v("Gavin", filmRelease);
        mRelease_Date.setText(filmRelease);

        String filmRating = data.getString(Detail_Movie_Fragment.COL_MOVIE_RATING);
        Log.v("Gavin", filmRating);
        mRating.setText(filmRating);
  **/
        final String filmInfo = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_INFO_D);




        Log.v("Gavin", filmInfo);
  /**
        /**
         String filmReview1 = data.getString(COL_REVIEW_ONE);
         mReview_One.setText(filmReview1);

         String filmReview2 = data.getString(COL_REVIEW_TWO);
         mReview_Two.setText(filmReview2);





             **/
    }
    else {
        Log.v("Gavin", "Cursor has no data");
    };



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }





}
