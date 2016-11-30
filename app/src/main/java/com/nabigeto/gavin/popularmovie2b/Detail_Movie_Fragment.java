package com.nabigeto.gavin.popularmovie2b;

import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeIntents;
import com.google.api.services.youtube.YouTube;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Favourite_db_Helper;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Favourites_Contract;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class Detail_Movie_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {



    Context dContext;
    public static final String KEY_FILE = "Shared_Preference_KEY_FILE_Detail";
    public static final String KEY_FILE2 = "movie_id";
    public static final String KEY_FILE3 = "startmovie";
    public static final String dKEY_FILE2 = "movie_id";
    public static final String KEY_FAVOURITE = "favourite_state";
    public static final String KEY_FINDER = "favourite_finder";

    public int position;
    public String mSw;

    public String sduri;

    public Boolean mFavourite_Status = false;

    public String mfavourite_finder;



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


     public static final String AUTHORITY = ".UtilitiesDB.movieContentProvider";
     public static final String ACCOUNT_TYPE = "nabigeto.com";
     public static final String ACCOUNT = "dummyaccount";

     Account rAccount;
     **/
    public String dUri;

    public String dUri_favourite;

    public int dUri_length;

    private static final String Youtube_KEY = "AIzaSyD4abokmDnidF1bBI2c5nrSMIb7Q73Tnnk";

    private static final int DETAIL_LOADER = 1;
    private static final  int REVIEW_LOADER = 2;
    private static final int TRAILER_LOADER = 3;
    private static final int FAVOURITE_LOADER = 4;
    private static final int SWITCH_LOADER = 5;

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
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR1,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR2,
            Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR3,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2,
            Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER3,
            Movie_Contract.MovieInfo.COLUMN_FAVOURITE


    };

    public static final int _ID = 0;
    public static final int COL_MOVIE_ENTRY_ID = 1;
    public static final int COL_MOVIE_ID = 2;
    public static final int COL_MOVIE_TITLE_D = 3;
    public static final int COL_MOVIE_RELEASE_DATE_D = 4;
    public static final int COL_MOVIE_RATING = 5;
    public static final int COL_MOVIE_INFO = 6;
    public static final int COL_MOVIE_IMAGE_FILE = 7;
    public static final int COL_MOVIE_REVIEW1 = 8;
    public static final int COL_MOVIE_REVIEW2 = 9;
    public static final int COL_MOVIE_REVIEW3 = 10;
    public static final int COL_MOVIE_REVIEW_AUTHOR1 =11;
    public static final int COL_MOVIE_REVIEW_AUTHOR2 =12;
    public static final int COL_MOVIE_REVIEW_AUTHOR3 =13;
    public static final int COL_MOVIE_TRAILER1 = 14;
    public static final int COL_MOVIE_TRAILER2 = 15;
    public static final int COL_MOVIE_TRAILER3 = 16;
    public static final int COL_FAVOURITE = 17;



    /**    public static final int COL_REVIEW_ONE = 5;
     public static final int COL_REVIEW_TWO = 6;
     public static final int COL_MOVIE_TRAILER = 7;
     **/

    public static final String [] Favourite_Columns = {
            Movie_Favourites_Contract.FavouriteInfo.TABLE_NAME_F + "." +
                    Movie_Favourites_Contract.FavouriteInfo._ID,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_ENTRY_ID,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TITLE,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RATING,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_INFO,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_IMAGE_FILE,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW1,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW2,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW3,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR1,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR2,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR3,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER1,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER2,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER3,
            Movie_Favourites_Contract.FavouriteInfo.COLUMN_FAVOURITE

    };


    public ImageView dImage_File;
    public TextView dID;
    public TextView dTitle;
    public TextView dRelease_Date;
    public TextView dRating;
    public TextView dInfo;
    public TextView dFavourites;

    public TextView dReview1;
    public TextView dReview2;
    public TextView dReview3;

    public TextView dReviewauthor1;
    public TextView dReviewauthor2;
    public TextView dReviewauthor3;

    public ImageView dTrailer1;
    public ImageView dTrailer2;
    public ImageView dTrailer3;

    public TextView movie_details_offline;
    public TextView movie_reviews_offline;
    public TextView movie_trailer_offline;

    public String m_id;
    public String movie_EntryID;
    public String movie_id;
    public String movie_TitleF;
    public String movie_ReleaseF;
    public String movie_RatingF;
    public String movie_ImageF;
    public String movie_InfoF;
    public String movie_Review1F;
    public String movie_Review1_AuthorF;
    public String movie_Review2F;
    public String movie_Review2_AuthorF;
    public String movie_Review3F;
    public String movie_Review3_AuthorF;
    public String movie_Trailer1;
    public String movie_Trailer2;
    public String movie_Trailer3;

    public String movie_Favourite;

    public boolean movie_fav;

    public CheckBox favouritebutton;

    public String movie_id_holder;


    public Detail_Movie_Fragment() {
        // Required empty public constructor
        /**  dContext = getContext();  **/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.v("Gavin", "onActivityCreated");


        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null) {
            dUri = arguments.getString(KEY_FILE);
            mFavourite_Status = arguments.getBoolean(KEY_FAVOURITE);
            mfavourite_finder = arguments.getString(KEY_FINDER);

            if (mFavourite_Status == true){
                getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
                String boolean_Value = String.valueOf(mFavourite_Status);
                Log.v("Gavin", "Detail Activity Fragment " + boolean_Value);
            }
            else {
                getLoaderManager().initLoader(DETAIL_LOADER, null, this);
                getLoaderManager().initLoader(REVIEW_LOADER, null, this);
                getLoaderManager().initLoader(TRAILER_LOADER, null, this);


            }
            Log.v("Gavin", "Detail Movie Fragment" + dUri);



        }
        else {
            dUri = "3";
            mFavourite_Status = false;

            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
            getLoaderManager().initLoader(REVIEW_LOADER, null, this);
            getLoaderManager().initLoader(TRAILER_LOADER, null, this);


        }


        Log.v("Gavin", "uri " + dUri);







    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){

            dUri = savedInstanceState.getString(dKEY_FILE2);
        }
        else{

        }

        /**    setRetainInstance(true);  **/
        setHasOptionsMenu(false);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_details_layout, container, false);
        Log.v("Gavin", "Got to the bit in the detail fragment");

        movie_details_offline = (TextView) rootView.findViewById(R.id.movie_details_offline);
        movie_reviews_offline = (TextView) rootView.findViewById(R.id.movie_review_offline);
        movie_trailer_offline = (TextView) rootView.findViewById(R.id.movie_trailer_offline);

        dImage_File = (ImageView) rootView.findViewById(R.id.detail_movie_poster);
        dRating = (TextView) rootView.findViewById(R.id.movie_user_rating_d);
        dRelease_Date = (TextView) rootView.findViewById(R.id.movie_release_date_d);
        dInfo = (TextView) rootView.findViewById(R.id.movie_info_d);
        dTitle = (TextView) rootView.findViewById(R.id.movie_title_d);

        dReview1 = (TextView) rootView.findViewById(R.id.movie_review1);
        dReview2 = (TextView) rootView.findViewById(R.id.movie_review2);
        dReview3 = (TextView) rootView.findViewById(R.id.movie_review3);

        dReviewauthor1 = (TextView) rootView.findViewById(R.id.movie_review1_author);
        dReviewauthor2 = (TextView) rootView.findViewById(R.id.movie_review2_author);
        dReviewauthor3 = (TextView) rootView.findViewById(R.id.movie_review3_author);

        dTrailer1 = (ImageView) rootView.findViewById(R.id.movie_Trailer1);
        dTrailer2 = (ImageView) rootView.findViewById(R.id.movie_Trailer2);
        dTrailer3 = (ImageView) rootView.findViewById(R.id.movie_Trailer3);



        favouritebutton = (CheckBox) rootView.findViewById(R.id.checkbox_favourite);


        favouritebutton.setChecked(mFavourite_Status);


        favouritebutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();

                if (checked = true) {

                    Cursor favourite_check;


                    String position_f = " _ID = " + dUri;
                    favourite_check = getContext().getContentResolver().query(Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F, DETAIL_COLUMNS, position_f, null, null);

                    if (favourite_check.getCount() <= 0) {

                        Toast.makeText(getActivity(), movie_TitleF + "Already a favourite :) ", Toast.LENGTH_LONG).show();

                    } else {

                        Log.v("Gavin", "got to this bit loader 2");

                        Log.v("Gavin", "Checkbox checked");

                        movie_Favourite = "y";

                        ContentValues favouriteValues = new ContentValues();

                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo._ID, m_id);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_ENTRY_ID, movie_EntryID);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TITLE, movie_TitleF);
                        Log.v("Gavin", "Detail Movie Fragment" + movie_TitleF);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RATING, movie_RatingF);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE, movie_ReleaseF);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_INFO, movie_InfoF);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_IMAGE_FILE, movie_ImageF);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID, movie_id);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW1, movie_Review1F);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW2, movie_Review2F);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW3, movie_Review3F);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR1, movie_Review1_AuthorF);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR2, movie_Review2_AuthorF);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR3, movie_Review3_AuthorF);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER1, movie_Trailer1);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER2, movie_Trailer2);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TRAILER3, movie_Trailer3);
                        favouriteValues.put(Movie_Favourites_Contract.FavouriteInfo.COLUMN_FAVOURITE, movie_Favourite);


                        Uri movie_insert = getContext().getContentResolver().insert(
                                Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F,
                                favouriteValues);

                    }

                }

                else {

                        String rSelectionClause = Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TITLE + " LIKE ?";
                        String[] rSelectionArgs = {movie_TitleF};

                        Log.v("Gavin", "got to this bit loader 2");

                        int favourite_details_delete = getContext().getContentResolver().delete(
                                Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F,
                                rSelectionClause,
                                rSelectionArgs);


                }
            }

        });

        dTrailer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                watchYoutubeVideo(movie_Trailer1);
            }
        });

        dTrailer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                watchYoutubeVideo(movie_Trailer2);
            }
        });

        dTrailer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                watchYoutubeVideo(movie_Trailer3);
            }
        });


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
                            Movie_Contract.MovieInfo.CONTENT_URI_R,
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
                            Movie_Contract.MovieInfo.CONTENT_URI_R,
                            DETAIL_COLUMNS,
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
                    Log.v("Gavin", "Trailer Loader cursor created");
                    return new CursorLoader(
                            getActivity(),
                            Movie_Contract.MovieInfo.CONTENT_URI_R,
                            DETAIL_COLUMNS,
                            position,
                            null,
                            null

                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Trailer Loader");
                Log.v("Gavin", "Loader cursor finished" + dUri);
                return null;


            case FAVOURITE_LOADER:

                position = " _ID = " + dUri;
                /**      Uri prefs_uri2 =  + " _ID " prefs_uri.getLastPathSegment();
                 **/
                row_Pref = dUri;
                Log.v("Gavin", "dUuri" + row_Pref);
                if (null != dUri) {
                    Log.v("Gavin", "Favourite Loader cursor created");
                    return new CursorLoader(
                            getActivity(),
                            Movie_Favourites_Contract.FavouriteInfo.CONTENT_URI_F,
                            DETAIL_COLUMNS,
                            position,
                            null,
                            null

                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Favourite Loader");
                Log.v("Gavin", "Loader cursor finished" + dUri);
                return null;

        }

        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(dKEY_FILE2, dUri);
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

                int cursor_check_detail = data_d.getCount();
                String check_detail = Integer.toString(cursor_check_detail);
                Log.v("Gavin", "cursor_check_detail" + check_detail);

                if (cursor_check_detail == 0) {

                    String detail_check = "Sorry - no reviews available :)";
                    movie_details_offline.setText(detail_check);

                }

                else

                {
                    data_d.moveToFirst();
                    Log.v("Gavin", "Position passed to loader");
                    position = data_d.getPosition();
                    positions = Integer.toString(position);
                    Log.v("Gavin", "Cursor position in detail fragment" + positions);

                    movie_details_offline.setVisibility(View.GONE);

                    int movie_EntryIDd = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_ENTRY_ID);
                    movie_EntryID = data_d.getString(movie_EntryIDd);

                    int movie_Titled = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TITLE);
                    movie_TitleF = data_d.getString(movie_Titled);

                    int movie_IDd = data_d.getColumnIndex(Movie_Contract.MovieInfo._ID);
                    m_id = data_d.getString(movie_IDd);

                    movie_RatingF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RATING);
                    Log.v("Gavin", movie_RatingF);
                    dRating.setText(movie_RatingF);

                    movie_ReleaseF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RELEASE_DATE_D);
                    Log.v("Gavin", movie_ReleaseF);
                    dRelease_Date.setText(movie_ReleaseF);

                    movie_InfoF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_INFO);
                    dInfo.setText(movie_InfoF);

                    int image_file_position = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_IMAGE_FILE);
                    movie_ImageF = data_d.getString(image_file_position);
                    Log.v("Gavin", "DetailActivityFragment" + movie_ImageF);

                    int filmTitle = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TITLE);
                    movie_TitleF = data_d.getString(filmTitle);
                    Log.v("Gavin", "DetailActivityFragment" + movie_TitleF);

                    if (isOnline() != true) {
                        Picasso.with(dContext).load(movie_ImageF).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.worms_head).into(dImage_File);
                        Log.v("Gavin", "Loading picasso in detail fragment");
                    }
                    else
                    {
                        Picasso.with(dContext).load(movie_ImageF).placeholder(R.drawable.worms_head).into(dImage_File);
                        Log.v("Gavin", "Loading picasso in detail fragment - cache");
                    }

                    int filmID = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_MOVIE_ID);
                    movie_id = data_d.getString(filmID);
                    Log.v("Gavin", "DetailActivityFragment" + movie_id);

                    int filmreview1 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW1);
                    movie_Review1F = data_d.getString(filmreview1);

                    int filmreview2 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW2);
                    movie_Review2F = data_d.getString(filmreview2);

                    int filmreview3 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW3);
                    movie_Review3F = data_d.getString(filmreview3);

                    int filmreviewauthor1 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR1);
                    movie_Review1_AuthorF = data_d.getString(filmreviewauthor1);

                    int filmreviewauthor2 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR2);
                    movie_Review2_AuthorF = data_d.getString(filmreviewauthor2);

                    int filmreviewauthor3 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR3);
                    movie_Review3_AuthorF = data_d.getString(filmreviewauthor3);

                    int filmtrailer1 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1);
                    movie_Trailer1 = data_d.getString(filmtrailer1);

                    int filmtrailer2 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2);
                    movie_Trailer2 = data_d.getString(filmtrailer2);

                    int filmtrailer3 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER3);
                    movie_Trailer3 = data_d.getString(filmtrailer3);

                    dTitle.setText(movie_TitleF);

                    int favourite_position = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_FAVOURITE);
                    movie_Favourite = data_d.getString(favourite_position);
                    Log.v("Gavin", "Favourite Status" + movie_Favourite);

                    if(movie_Favourite.equals("y")) {
                        favouritebutton.setChecked(true);
                    }
                    else{
                        favouritebutton.setChecked(false);
                    }



                }

                break;

            case REVIEW_LOADER:

                int cursor_check_review = data_d.getCount();

                String check_review = Integer.toString(cursor_check_review);
                Log.v("Gavin", "cursor_check_detail" + check_review);

                if (cursor_check_review == 0) {

                    String review_status = "Sorry - no reviews available :)";
                    dReview1.setVisibility(View.VISIBLE);
                    dReview1.setText(review_status);

                }

                else{
                    data_d.moveToFirst();

                    dReview1.setVisibility(View.GONE);

                    int movie_R1 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW1);
                    movie_Review1F = data_d.getString(movie_R1);
                    Log.v("Gavin", "Review Loader " + movie_Review1F);
                    if (movie_Review1F.equals("b")){
                        dReview1.setVisibility(View.GONE);
                    }
                    else{
                        dReview1.setVisibility(View.VISIBLE);
                        dReview1.setText(movie_Review1F);
                    }

                    int movie_A1 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR1);
                    movie_Review1_AuthorF = data_d.getString(movie_A1);
                    if (movie_Review1_AuthorF.equals("b")){
                        dReviewauthor1.setVisibility(View.GONE);
                    }
                    else{
                        dReviewauthor1.setVisibility(View.VISIBLE);
                        dReviewauthor1.setText(movie_Review1_AuthorF);
                    }

                    int movie_R2 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW2);
                    movie_Review2F = data_d.getString(movie_R2);
                    if (movie_Review2F.equals("b")){
                        dReview2.setVisibility(View.GONE);
                    }
                    else {
                        dReview2.setVisibility(View.VISIBLE);
                        dReview2.setText(movie_Review2F);
                    }

                    int movie_A2 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR2);
                    movie_Review2_AuthorF = data_d.getString(movie_A2);
                    if (movie_Review2_AuthorF.equals("b")){
                        dReviewauthor2.setVisibility(View.GONE);
                    }
                    else{
                        dReviewauthor2.setVisibility(View.VISIBLE);
                        dReviewauthor2.setText(movie_Review2_AuthorF);
                    }

                    int movie_R3 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW3);
                    movie_Review3F = data_d.getString(movie_R3);
                    if (movie_Review3F.equals("b")){
                        dReview3.setVisibility(View.GONE);
                    }
                    else{
                        dReview3.setVisibility(View.VISIBLE);
                        dReview3.setText(movie_Review3F);
                    }

                    int movie_A3 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_REVIEW_AUTHOR3);
                    movie_Review3_AuthorF = data_d.getString(movie_A3);
                    if (movie_Review3_AuthorF.equals("b")){
                        dReviewauthor3.setVisibility(View.GONE);
                    }
                    else{
                        dReviewauthor3.setVisibility(View.VISIBLE);
                        dReviewauthor3.setText(movie_Review3_AuthorF);
                    }

                }

                break;

            case TRAILER_LOADER:

                String url_youtube;

                int cursor_check_trailer = data_d.getCount();
                String check_trailer = Integer.toString(cursor_check_trailer);
                Log.v("Gavin", "cursor_check_detail" + check_trailer);

                if (cursor_check_trailer == 0){

                    String trailer_status = "Sorry - no trailers available :)";
                    movie_trailer_offline.setVisibility(View.VISIBLE);
                    movie_trailer_offline.setText(trailer_status);

                }

                else{


                    data_d.moveToFirst();

                    movie_trailer_offline.setVisibility(View.GONE);

                    int trailer_A1 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER1);
                    movie_Trailer1 = data_d.getString(trailer_A1);
                    if (movie_Trailer1.equals("b")){
                        dTrailer1.setVisibility(View.GONE);
                    }
                    else{
                        dTrailer1.setVisibility(View.VISIBLE);
                        url_youtube = "http://img.youtube.com/vi/" + movie_Trailer1 + "/1.jpg";
                        Picasso.with(dContext).load(url_youtube).placeholder(R.drawable.worms_head).into(dTrailer1);
                    }

                    int trailer_A2 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER2);
                    movie_Trailer2 = data_d.getString(trailer_A2);
                    if (movie_Trailer2.equals("b")){
                        dTrailer2.setVisibility(View.GONE);
                    }
                    else{
                        dTrailer2.setVisibility(View.VISIBLE);
                        url_youtube = "http://img.youtube.com/vi/" + movie_Trailer2 + "/1.jpg";
                        Picasso.with(dContext).load(url_youtube).placeholder(R.drawable.worms_head).into(dTrailer2);
                    }

                    int trailer_A3 = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_NAME_TRAILER3);
                    movie_Trailer3 = data_d.getString(trailer_A3);
                    if (movie_Trailer3.equals("b")){
                        dTrailer3.setVisibility(View.GONE);
                    }
                    else{
                        dTrailer3.setVisibility(View.VISIBLE);
                        url_youtube = "http://img.youtube.com/vi/" + movie_Trailer3 + "/1.jpg";
                        Picasso.with(dContext).load(url_youtube).placeholder(R.drawable.worms_head).into(dTrailer3);
                    }
                }
                break;

            case FAVOURITE_LOADER:

                int cursor_check_detail_f = data_d.getCount();
                String check_detail_f = Integer.toString(cursor_check_detail_f);
                Log.v("Gavin", "cursor_check_detail" + check_detail_f);

                if (cursor_check_detail_f == 0) {

                    String detail_check = "Sorry - no reviews available :)";
                    movie_details_offline.setText(detail_check);

                }

                else

                {
                    data_d.moveToFirst();
                    Log.v("Gavin", "Position passed to loader");
                    position = data_d.getPosition();
                    positions = Integer.toString(position);
                    Log.v("Gavin", "Cursor position in detail fragment" + positions);

                    movie_details_offline.setVisibility(View.GONE);

                    int movie_EntryIDd = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_ENTRY_ID);
                    movie_EntryID = data_d.getString(movie_EntryIDd);

                    int movie_Titled = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_TITLE);
                    movie_TitleF = data_d.getString(movie_Titled);
                    dTitle.setText(movie_TitleF);

                    int movie_IDd = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo._ID);
                    m_id = data_d.getString(movie_IDd);
                    dID.setText(m_id);

                    int movie_Ratingd = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RATING);
                    movie_RatingF = data_d.getString(movie_Ratingd);
                    dRating.setText(movie_RatingF);
                    Log.v("Gavin", movie_RatingF);


                    int movie_Released = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE);
                    movie_ReleaseF = data_d.getString(movie_Released);
                    Log.v("Gavin", movie_ReleaseF);
                    dRelease_Date.setText(movie_ReleaseF);

                    int movie_Infod = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_INFO);
                    movie_InfoF = data_d.getString(movie_Infod);
                    dInfo.setText(movie_InfoF);

                    int image_file_position = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_IMAGE_FILE);
                    movie_ImageF = data_d.getString(image_file_position);
                    Log.v("Gavin", "DetailActivityFragment" + movie_ImageF);



                    if (isOnline() != true) {
                        Picasso.with(dContext).load(movie_ImageF).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.worms_head).into(dImage_File);
                        Log.v("Gavin", "Loading picasso in detail fragment");
                    }
                    else
                    {
                        Picasso.with(dContext).load(movie_ImageF).placeholder(R.drawable.worms_head).into(dImage_File);
                        Log.v("Gavin", "Loading picasso in detail fragment - cache");
                    }



                    int favourite_position = data_d.getColumnIndex(Movie_Contract.MovieInfo.COLUMN_FAVOURITE);
                    movie_Favourite = data_d.getString(favourite_position);
                    Log.v("Gavin", "Favourite Status" + movie_Favourite);

                    if(movie_Favourite.equals("y")) {
                        favouritebutton.setChecked(true);
                    }
                    else{
                        favouritebutton.setChecked(false);
                    }


                    dReview1.setVisibility(View.GONE);

                    int movie_R1 = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW1);
                    movie_Review1F = data_d.getString(movie_R1);
                    Log.v("Gavin", "Review Loader " + movie_Review1F);
                    if (movie_Review1F.equals("b")){
                        dReview1.setVisibility(View.GONE);
                    }
                    else{
                        dReview1.setVisibility(View.VISIBLE);
                        dReview1.setText(movie_Review1F);
                    }

                    int movie_A1 = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR1);
                    movie_Review1_AuthorF = data_d.getString(movie_A1);
                    if (movie_Review1_AuthorF.equals("b")){
                        dReviewauthor1.setVisibility(View.GONE);
                    }
                    else{
                        dReviewauthor1.setVisibility(View.VISIBLE);
                        dReviewauthor1.setText(movie_Review1_AuthorF);
                    }

                    int movie_R2 = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW2);
                    movie_Review2F = data_d.getString(movie_R2);
                    if (movie_Review2F.equals("b")){
                        dReview2.setVisibility(View.GONE);
                    }
                    else {
                        dReview2.setVisibility(View.VISIBLE);
                        dReview2.setText(movie_Review2F);
                    }

                    int movie_A2 = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR2);
                    movie_Review2_AuthorF = data_d.getString(movie_A2);
                    if (movie_Review2_AuthorF.equals("b")){
                        dReviewauthor2.setVisibility(View.GONE);
                    }
                    else{
                        dReviewauthor2.setVisibility(View.VISIBLE);
                        dReviewauthor2.setText(movie_Review2_AuthorF);
                    }

                    int movie_R3 = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW3);
                    movie_Review3F = data_d.getString(movie_R3);
                    if (movie_Review3F.equals("b")){
                        dReview3.setVisibility(View.GONE);
                    }
                    else{
                        dReview3.setVisibility(View.VISIBLE);
                        dReview3.setText(movie_Review3F);
                    }

                    int movie_A3 = data_d.getColumnIndex(Movie_Favourites_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR3);
                    movie_Review3_AuthorF = data_d.getString(movie_A3);
                    if (movie_Review3_AuthorF.equals("b")){
                        dReviewauthor3.setVisibility(View.GONE);
                    }
                    else{
                        dReviewauthor3.setVisibility(View.VISIBLE);
                        dReviewauthor3.setText(movie_Review3_AuthorF);
                    }


                }


        }

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public boolean isOnline() {
        Context context;
        context = getActivity();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }


    public void watchYoutubeVideo(String id){

        /**
         Intent appIntent = YouTubeStandalonePlayer.createVideoIntent(getContext(), Youtube_KEY, id);
         **/

        Intent appIntent = YouTubeIntents.createPlayVideoIntentWithOptions(getContext(), id, true, false);
        startActivity(appIntent);

        Log.v("Gavin", "Detail Movie Trailer " + id);

        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));

        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }


}