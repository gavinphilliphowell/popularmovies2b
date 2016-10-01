package com.nabigeto.gavin.popularmovie2b;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.nabigeto.gavin.popularmovie2b.Sync.ReviewSyncAdapter;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Favourite_Contract;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Favourite_db_Helper;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_Contract;
import com.nabigeto.gavin.popularmovie2b.UtilitiesDB.Movie_db_Helper;
import com.squareup.picasso.Picasso;

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

    private static final String Youtube_KEY = "AIzaSyD4abokmDnidF1bBI2c5nrSMIb7Q73Tnnk";

    private static final int DETAIL_LOADER = 1;
    private static final  int REVIEW_LOADER = 2;
    private static final int TRAILER_LOADER = 3;
    private static final int FAVOURITE_LOADER = 4;


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
    public static final int COL_MOVIE_REVIEW_AUTHOR1 =11;
    public static final int COL_MOVIE_REVIEW_AUTHOR2 =12;
    public static final int COL_MOVIE_REVIEW_AUTHOR3 =13;
    public static final int COL_MOVIE_TRAILER1 = 14;
    public static final int COL_MOVIE_TRAILER2 = 15;
    public static final int COL_MOVIE_TRAILER3 = 16;
    public static final int COL_FAVOURITE_D = 17;



    /**    public static final int COL_REVIEW_ONE = 5;
    public static final int COL_REVIEW_TWO = 6;
    public static final int COL_MOVIE_TRAILER = 7;
**/

    public static final String [] Favourite_Columns = {
            Favourite_Contract.FavouriteInfo.TABLE_NAME + "." +
            Favourite_Contract.FavouriteInfo._ID,
            Favourite_Contract.FavouriteInfo.COLUMN_NAME_TITLE,
            Favourite_Contract.FavouriteInfo.COLUMN_NAME_RATING,
            Favourite_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE,
            Favourite_Contract.FavouriteInfo.COLUMN_NAME_INFO,
            Favourite_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID

    };

    public static final int _ID = 20;
    public static final int COL_FAVOURITE_TITLE = 21;
    public static final int COL_FAVOURITE_RATING = 22;
    public static final int COL_FAVOURITE_RELEASE_DATE = 23;
    public static final int COL_FAVOURITE_INFO = 24;
    public static final int COL_FAVOURITE_MOVIE_ID = 25;


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

    public ImageButton favouriteButton;

    private String m_id;
    private String movie_id;
    private String movie_TitleF;
    private String movie_ReleaseF;
    private String movie_RatingF;
    private String movie_ImageF;
    private String movie_InfoF;
    private String movie_Review1F;
    private String movie_Review1_AuthorF;
    private String movie_Review2F;
    private String movie_Review2_AuthorF;
    private String movie_Review3F;
    private String movie_Review3_AuthorF;
    private String movie_Trailer1;
    private String movie_Trailer2;
    private String movie_Trailer3;


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


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);


/**
        rAccount = CreateSyncAccount(getContext());
**/
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

        dReview1 = (TextView) rootView.findViewById(R.id.movie_review1);
        dReview2 = (TextView) rootView.findViewById(R.id.movie_review2);
        dReview3 = (TextView) rootView.findViewById(R.id.movie_review3);

        dReviewauthor1 = (TextView) rootView.findViewById(R.id.movie_review1_author);
        dReviewauthor2 = (TextView) rootView.findViewById(R.id.movie_review2_author);
        dReviewauthor3 = (TextView) rootView.findViewById(R.id.movie_review3_author);

        dTrailer1 = (ImageView) rootView.findViewById(R.id.movie_Trailer1);
        dTrailer2 = (ImageView) rootView.findViewById(R.id.movie_Trailer2);
        dTrailer3 = (ImageView) rootView.findViewById(R.id.movie_Trailer3);

        final CheckBox favouritebutton = (CheckBox) rootView.findViewById(R.id.checkbox_favourite);

        favouritebutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                    boolean checked = ((CheckBox) view).isChecked();

                    switch(view.getId()){
                        case R.id.checkbox_favourite:
                            if(checked){
                    /**            Favourite_db_Helper fdbmovie = new Favourite_db_Helper(getContext());

                                SQLiteDatabase db = fdbmovie.getWritableDatabase();

                                String rSelectionClause = Favourite_Contract.FavouriteInfo._ID + " LIKE ?";
                                String[] rSelectionArgs = {dUri};
                                Log.v("Gavin","got to this bit loader 2");

                                int num_rows = getContext().getContentResolver().query(
                                        Favourite_Contract.FavouriteInfo.CONTENT_URI_F,
                                        ?,
                                        rSelectionClause,
                                        rSelectionArgs
                                );
                                Log.v("Gavin", "got to this bit loader 3");
                     **/


                            Log.v("Gavin", "Checkbox checked");

                                ContentValues favouriteValues = new ContentValues();

                                favouriteValues.put(Favourite_Contract.FavouriteInfo._ID, m_id);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_TITLE, movie_TitleF);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_RATING, movie_RatingF);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE, movie_ReleaseF);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_INFO, movie_InfoF);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_IMAGE_FILE, movie_ImageF);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID, movie_id);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW1, movie_Review1F);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW2, movie_Review2F);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW3, movie_Review3F);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR1, movie_Review1_AuthorF);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR2, movie_Review2_AuthorF);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR3, movie_Review3_AuthorF);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_TRAILER1, movie_Trailer1);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_TRAILER2, movie_Trailer2);
                                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_TRAILER3, movie_Trailer3);


                                String rSelectionClause = Favourite_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID + " LIKE ?";
                                String[] rSelectionArgs = {movie_id};
                                Log.v("Gavin","got to this bit loader 2");

                                getContext().getContentResolver().insert(
                                        Favourite_Contract.FavouriteInfo.CONTENT_URI_F,
                                        favouriteValues);
                            }

                            else{
                                Log.v("Gavin", "Checkbox unchecked");

                                String rSelectionClause = Favourite_Contract.FavouriteInfo.COLUMN_NAME_TITLE + " LIKE ?";
                                String[] rSelectionArgs = {movie_TitleF};
                                Log.v("Gavin","got to this bit loader 2");

                                getContext().getContentResolver().delete(
                                        Favourite_Contract.FavouriteInfo.CONTENT_URI_F,
                                        rSelectionClause,
                                        rSelectionArgs);
                        /**
                                Favourite_db_Helper fdbmovie = new Favourite_db_Helper(getContext());

                                SQLiteDatabase db = fdbmovie.getWritableDatabase();

                                String rSelectionClause = Favourite_Contract.FavouriteInfo._ID + " LIKE ?";
                                String[] rSelectionArgs = {dUri};
                                Log.v("Gavin","got to this bit loader 2");

                                int num_rows = getContext().getContentResolver().query(
                                        Favourite_Contract.FavouriteInfo.CONTENT_URI_F,
                                        ?,
                                        rSelectionClause,
                                        rSelectionArgs
                                );
                                Log.v("Gavin", "got to this bit loader 3");
                        **/

                            }
                            break;
                    }
                }

        });



      /**  favouriteButton = (ImageButton) rootView.findViewById(R.id.favourite_button);

        favouriteButton.setOnClickListener(new View.OnClickListener() {

                                               @Override
                                               public void onClick(View v) {

                                                   boolean favourite_state = readState();

                                                   if (favourite_state = true) {

                                                       favouriteButton.setBackgroundResource(android.R.drawable.btn_star_big_off);
                                                       setState(false);

                                                   } else {

                                                       favouriteButton.setBackgroundResource(android.R.drawable.btn_star_big_on);
                                                       setState(true);
                                                   }
                                               }

                                           });
**/
        Log.v("Gavin", "About to load rootview");
        return rootView;
    }

    public boolean readState(){

        boolean favourite_State = false;


        return favourite_State;
    }

    private void setState(boolean s_state){

        boolean favourite_State = s_state;

        if (s_state = true){

            getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
        }




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
                            Favourite_Contract.FavouriteInfo.CONTENT_URI_F,
                            DETAIL_COLUMNS,
                            position,
                            null,
                            null

                    );

                }
                Log.v("Gavin", "Loader cursor finished" + "Favourite_Loader");
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

                    String movieReview1 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW1);
                    if (movieReview1 != "b"){
                        dReview1.setVisibility(View.VISIBLE);
                        dReview1.setText(movieReview1);
                    }

                    String movieReviewAuthor1 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR1);
                    if (movieReviewAuthor1 != "b"){
                        dReviewauthor1.setVisibility(View.VISIBLE);
                        dReviewauthor1.setText(movieReviewAuthor1);
                    }

                    String movieReview2 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW2);
                    if (movieReview2 != "b"){
                        dReview2.setVisibility(View.VISIBLE);
                        dReview2.setText(movieReview2);
                    }

                    String movieReviewAuthor2 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR2);
                    if (movieReviewAuthor2 != "b"){
                        dReviewauthor2.setVisibility(View.VISIBLE);
                        dReviewauthor2.setText(movieReviewAuthor2);
                    }

                    String movieReview3 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW3);
                    if (movieReview3 != "b"){
                        dReview3.setVisibility(View.VISIBLE);
                        dReview3.setText(movieReview3);
                    }

                    String movieReviewAuthor3 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR3);
                    if (movieReviewAuthor3 != "b"){
                        dReviewauthor3.setVisibility(View.VISIBLE);
                        dReviewauthor3.setText(movieReviewAuthor3);
                    }

                break;

                case TRAILER_LOADER:

                    String url_youtube;

                    data_d.moveToFirst();

                    String movieTrailer1 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER1);
                    if (movieTrailer1 != "b"){
                        dTrailer1.setVisibility(View.VISIBLE);
                        url_youtube = "http://img.youtube.com/vi/" + movieTrailer1 + "/1.jpg";
                        Log.v("Gavin", url_youtube);
                        Picasso.with(dContext).load(url_youtube).placeholder(R.drawable.worms_head).into(dTrailer1);
            /**            dTrailer1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.v("Gavin", "Youtube video launched");
                            }
                        });
             **/
                    }


                    String movieTrailer2 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER2);
                    if (movieTrailer2 != "b"){
                        dTrailer2.setVisibility(View.VISIBLE);
                        url_youtube = "http://img.youtube.com/vi/" + movieTrailer2 + "/1.jpg";
                        Log.v("Gavin", url_youtube);
                        Picasso.with(dContext).load(url_youtube).placeholder(R.drawable.worms_head).into(dTrailer2);

                    }

                    String movieTrailer3 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER3);
                    if (movieTrailer3 != "b"){
                        dTrailer3.setVisibility(View.VISIBLE);
                        url_youtube = "http://img.youtube.com/vi/" + movieTrailer3 + "/1.jpg";
                        Log.v("Gavin", url_youtube);
                        Picasso.with(dContext).load(url_youtube).placeholder(R.drawable.worms_head).into(dTrailer3);

                    }

            case FAVOURITE_LOADER:

                data_d.moveToFirst();

                movie_id = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_ID_D);
                movie_TitleF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TITLE_D);
                Log.v("Gavin", "Favourite_Loader" + movie_TitleF);
                movie_ReleaseF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RELEASE_DATE_D);
                movie_RatingF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_RATING_D);
                movie_ImageF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_IMAGE_FILE_D);
                movie_InfoF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_INFO_D);
                movie_Review1F = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW1);
                Log.v("Gavin", "Favourite_Loader" + movie_Review1F);
                movie_Review1_AuthorF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR1);
                movie_Review2F = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW2);
                movie_Review2_AuthorF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR2);
                movie_Review3F = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW3);
                movie_Review3_AuthorF = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_REVIEW_AUTHOR3);
                movie_Trailer1 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER1);
                movie_Trailer2 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER2);
                movie_Trailer3 = data_d.getString(Detail_Movie_Fragment.COL_MOVIE_TRAILER3);

                Favourite_db_Helper fdbHelper = new Favourite_db_Helper(getContext());

                SQLiteDatabase db = fdbHelper.getWritableDatabase();

                ContentValues favouriteValues = new ContentValues();

                Log.v("Gavin", "Favourite Loader upload ++" + movie_id);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_MOVIE_ID, movie_id);

                Log.v("Gavin", "Favourite Loader upload" + movie_TitleF);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_TITLE, movie_TitleF);

                Log.v("Gavin", "Favourite Loader upload" + movie_ReleaseF);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_RELEASE_DATE, movie_ReleaseF);

                Log.v("Gavin", "Favourite Loader upload" + movie_RatingF);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_RATING, movie_RatingF);

                Log.v("Gavin", "Favourite Loader upload" +  movie_ImageF);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_IMAGE_FILE, movie_ImageF);

                Log.v("Gavin", "Favourite Loader upload" + movie_InfoF);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_INFO, movie_InfoF);

                Log.v("Gavin", "Favourite Loader upload" + movie_Review1F);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW1, movie_Review1F);

                Log.v("Gavin", "Favourite Loader upload" + movie_Review1_AuthorF);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR1, movie_Review1_AuthorF);

                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW2, movie_Review2F);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR2, movie_Review2_AuthorF);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW3, movie_Review3F);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_REVIEW_AUTHOR3, movie_Review3_AuthorF);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_TRAILER1, movie_Trailer1);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_TRAILER2, movie_Trailer2);
                favouriteValues.put(Favourite_Contract.FavouriteInfo.COLUMN_NAME_TRAILER3, movie_Trailer3);


                long id_insert = db.insert(Favourite_Contract.FavouriteInfo.TABLE_NAME, null, favouriteValues);

                db.close();

        }






    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


/**
    public static Account CreateSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);

        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {

        } else {

        }
        return newAccount;
    }
**/

}
